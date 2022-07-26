package com.mostafa.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mostafa.myapplication.DataModels.Film;
import com.mostafa.myapplication.DataModels.Property;
import com.mostafa.myapplication.DataModels.PropertyAdapter;
import com.mostafa.myapplication.Database.FilmsDatabase;
import com.mostafa.myapplication.ui.main.FilmFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FilmActivity extends AppCompatActivity {
    private ImageView poster;
    private RecyclerView list;
    private PropertyAdapter adapter;
    private Film film;
    private FrameLayout frameLayout;
    private boolean validResponse = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_activity);
        poster = findViewById(R.id.container2);
        list = findViewById(R.id.list);
        frameLayout = findViewById(R.id.standard_bottom_sheet);
        BottomSheetBehavior<FrameLayout> from = BottomSheetBehavior.from(frameLayout);
        from.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d("adada", slideOffset + "");
                findViewById(R.id.slide).setRotation(slideOffset * 180);
            }
        });
        adapter = new PropertyAdapter(new ArrayList<>());
        list.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        list.setAdapter(adapter);
        String imdbID;
        imdbID = getIntent().getStringExtra("ID");
        Observable<List<Property>> observable = Observable.fromCallable(new Callable<List<Property>>() {
            @Override
            public List<Property> call() throws Exception {
                FilmsDatabase database = Room.databaseBuilder(FilmActivity.this, FilmsDatabase.class, "db-Films")
                        .allowMainThreadQueries()   //Allows room to do operation on main thread
                        .build();
                film = database.getFilmDao().get(imdbID);
                return film.getProperties(FilmActivity.this);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<List<Property>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Property> properties) {
                Log.d("Database", "Database is ready : " + properties);
                Picasso.with(FilmActivity.this)
                        .load(film.getPosterUrl())
                        .into(poster);
                if (!validResponse) {
                    adapter.refresh(properties);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        sendRequest(imdbID);
    }

    private void sendRequest(String imdbId) {
        String url = MessageFormat.format("https://www.omdbapi.com/?apikey=3e974fca&i={0}", imdbId);
        Log.d("Url", url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), response -> {
            Log.d("Url", "" + response);
            Observable<List<Property>> observable = Observable.fromCallable(new Callable<List<Property>>() {
                @Override
                public List<Property> call() throws Exception {
                    FilmsDatabase database = Room.databaseBuilder(FilmActivity.this, FilmsDatabase.class, "db-Films")
                            .allowMainThreadQueries()   //Allows room to do operation on main thread
                            .build();
                    film = new Film(film.getIndex(), response);
                    database.getFilmDao().update(film);
                    return film.getProperties(FilmActivity.this);
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            observable.subscribe(new Observer<List<Property>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(List<Property> properties) {
                    Log.d("Database", "Database is ready : " + properties);
                    Picasso.with(FilmActivity.this)
                            .load(film.getPosterUrl())
                            .into(poster);
                    adapter.refresh(properties);
                    validResponse = true;
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }, error -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}