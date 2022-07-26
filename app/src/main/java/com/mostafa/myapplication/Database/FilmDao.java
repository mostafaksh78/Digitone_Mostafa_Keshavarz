package com.mostafa.myapplication.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mostafa.myapplication.DataModels.Film;

@Dao
public abstract class FilmDao {
    @Insert
    public abstract void insert(Film... notes);
    @Update
    public abstract void update(Film... notes);
    @Delete
    public abstract void delete(Film notes);
    @Query("SELECT * FROM Films")
    public abstract Film[] get();
    @Query("SELECT * FROM Films Where imdbID = :imdbID")
    public abstract Film get(String imdbID);
    @Query("UPDATE Films Set title = :title , year =:year , posterUrl =:poster Where imdbID =:imdbID" )
    public abstract void halfUpdate(String title,String year,String poster,String imdbID);
}
