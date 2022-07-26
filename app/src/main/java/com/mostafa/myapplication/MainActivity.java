package com.mostafa.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mostafa.myapplication.DataModels.Film;
import com.mostafa.myapplication.DataModels.FilmAdapter;
import com.mostafa.myapplication.Database.FilmsDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class MainActivity extends AppCompatActivity {
    private static final String URL = "https://www.omdbapi.com/?apikey=3e974fca&s=batman";
    private RecyclerView list;
    private FilmAdapter adapter;
    private FilmsDatabase database;
    private SwipeRefreshLayout refreshLayout;
    private boolean validResponse = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        refreshLayout = findViewById(R.id.refresh);
        list.setLayoutManager(new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false));
        Observable<Film[]> observable  = Observable.fromCallable(new Callable<Film[]>() {
            @Override
            public Film[] call() throws Exception {
                database = Room.databaseBuilder(getBaseContext(),FilmsDatabase.class, "db-Films")
                        .allowMainThreadQueries()   //Allows room to do operation on main thread
                        .build();
                return database.getFilmDao().get();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
            observable.subscribe(new Observer<Film[]>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Film[] films) {
                LinkedList<Film> films1 = new LinkedList<>(Arrays.asList(films));
                Log.d("Database","Database is ready : " + films1);
                if (!validResponse) {
                    adapter.handle(films1);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
//        Film[] films = database.getFilmDao().get();
//        Log.d("Database",Arrays.asList(films) + "");
//        adapter = new FilmAdapter(new LinkedList<Film>(Arrays.asList(films)), new ItemTouchedListener<Film>() {
//            @Override
//            public void onItemClick(Film film) {
//                Log.d("FilmDebug",film + "");
//                startActivity(new Intent(MainActivity.this,FilmActivity.class).putExtra("ID",film.getImdbID()));
//            }
//        });
        adapter = new FilmAdapter(new ArrayList<>(), new ItemTouchedListener<Film>() {
            @Override
            public void onItemClick(Film film) {
                Log.d("FilmDebug",film + "");
                startActivity(new Intent(MainActivity.this,FilmActivity.class).putExtra("ID",film.getImdbID()));
            }
        });
        list.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest();
            }
        });
        refreshLayout.setRefreshing(true);
        sendRequest();
    }
    private void sendRequest(){
        Log.d("Database","Send Request");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error(error);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void error(VolleyError volleyError) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    private void listener(JSONObject jsonObject) {
        Log.d("Respose",jsonObject.toString());
        try {
            List<Film> films = new ArrayList<>();
            JSONArray search = jsonObject.getJSONArray("Search");
            for (int i = 0; i < search.length(); i++) {
                JSONObject filmObject = search.getJSONObject(i);
                Log.d("FilmDebug",filmObject.toString());
                Film film = new Film(i,
                        filmObject.getString(Film.TITLE_KEY),
                        filmObject.getString(Film.YEAR_KEY),
                        filmObject.getString(Film.POSTER_KEY),
                        filmObject.getString(Film.IMDBID_KEY));
                films.add(film);
                boolean b = database.getFilmDao().get(film.getImdbID()) == null;

                Log.d("Database",b +" : "  + film.getTitle() + " : " +film.getImdbID() + " : " + database.getFilmDao().get(film.getImdbID()));
                if (database.getFilmDao().get(film.getImdbID()) == null) {
                    database.getFilmDao().insert(film);
                }else {
                    database.getFilmDao().halfUpdate(film.getTitle(),film.getYear(),film.getPosterUrl(),film.getImdbID());
                }
            }
            adapter.handle(films);
            validResponse = true;
            refreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}