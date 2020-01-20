package com.example.backendonly;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

//@Database(entities = {Term.class, Course.class}, exportSchema = false, version = 1)
@Database(entities = {Term.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class BasicDatabase extends RoomDatabase {
    private static final String DB_NAME = "basic_db";
    private static BasicDatabase instance;

    public static synchronized BasicDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), BasicDatabase.class, DB_NAME).allowMainThreadQueries().build();

        }
        return instance;
    }

    public abstract DatabaseDao databaseDao();
}
