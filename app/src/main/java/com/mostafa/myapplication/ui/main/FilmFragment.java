package com.mostafa.myapplication.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mostafa.myapplication.DataModels.Film;
import com.mostafa.myapplication.DataModels.Property;
import com.mostafa.myapplication.DataModels.PropertyAdapter;
import com.mostafa.myapplication.Database.FilmsDatabase;
import com.mostafa.myapplication.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class FilmFragment extends Fragment {

    private Film film;
    private RecyclerView list;
    private ImageView poster;
    private PropertyAdapter adapter;

    public static FilmFragment newInstance(String imdbID) {

        Bundle args = new Bundle();

        FilmFragment fragment = new FilmFragment();
        args.putString("ID", imdbID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String imdbID = getArguments().getString("ID");
        FilmsDatabase database = Room.databaseBuilder(getContext(), FilmsDatabase.class, "db-Films")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        film = database.getFilmDao().get(imdbID);
        adapter = new PropertyAdapter(new ArrayList<>());
        bindData();
        String url = MessageFormat.format("https://www.omdbapi.com/?apikey=3e974fca&i={0}", film.getImdbID());
        Log.d("Url",url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), response -> {
            Log.d("Url","" + response);
            try {
                film = new Film(film.getIndex(),response);
                database.getFilmDao().update(film);
                bindData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void bindData() {
        List<Property> properties = film.getProperties(getContext());
        adapter.refresh((properties));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        list = view.findViewById(R.id.list);
        poster = view.findViewById(R.id.imageView2);
        list.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        list.setAdapter(adapter);
        Picasso.with(getContext())
                .load(film.getPosterUrl())
                .into(poster);
        return view;
    }

}