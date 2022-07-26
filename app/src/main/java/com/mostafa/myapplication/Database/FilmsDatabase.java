package com.mostafa.myapplication.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mostafa.myapplication.DataModels.Film;
import com.mostafa.myapplication.DataModels.RateConverter;

@Database(entities = {Film.class}, version = 1)
@TypeConverters({RateConverter.class})
public abstract class FilmsDatabase extends RoomDatabase {
    public abstract FilmDao getFilmDao();
}
