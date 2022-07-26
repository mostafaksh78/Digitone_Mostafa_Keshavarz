package com.mostafa.myapplication.DataModels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mostafa.myapplication.ItemTouchedListener;
import com.mostafa.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private List<Film> films;
    private ItemTouchedListener<Film> filmClickListener;

    public FilmAdapter(List<Film> films, ItemTouchedListener<Film>  filmClickListener) {
        this.films = films;
        this.filmClickListener = filmClickListener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(films.get(position),filmClickListener);
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public void handle(List<Film> films) {
        for (int i = 0; i <films.size(); i++) {
            Film film = films.get(i);
            if (!this.films.contains(film)){
                insert(film);
            }else{
                update(film);
            }
        }
        for (int i = 0; i <this.films.size(); i++) {
            Film film = this.films.get(i);
            if (!films.contains(film)){
                delete(film);
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView poster;
        private TextView title;
        private TextView year;
        private TextView imdb;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textView);
            imdb = itemView.findViewById(R.id.imdb);
            year = itemView.findViewById(R.id.textView3);
        }

        public void bind(Film film, ItemTouchedListener<Film> filmClickListener) {
            imdb.setText(String.format("%s : %s", itemView.getResources().getString(R.string.imdb), film.getImdbID()));
            title.setText(film.getTitle());
            year.setText(film.getYear());
            Picasso.with(itemView.getContext())
                    .load(film.getPosterUrl())
                    .into(poster);
//            filmClickListener.setT(film);
            itemView.setOnClickListener(filmClickListener.getListener(film));
        }
    }
    public void insert(Film film){
        films.add(film);
        notifyItemInserted(films.size() -1);
    }
    public void delete(Film film){
        int index = films.indexOf(film);
        if (index >= 0){
            delete(index);
        }
    }

    public void delete(int index) {
        films.remove(index);
        notifyItemRemoved(index);
    }

    public void update(Film film){
        int index = films.indexOf(film);
        if (index >= 0){
            update(film,index);
        }
    }
    public void update(Film film, int index){
        films.set(index,film);
        notifyItemChanged(index);
    }

}
