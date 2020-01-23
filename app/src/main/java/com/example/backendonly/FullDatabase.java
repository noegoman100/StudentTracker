package com.example.backendonly;

import android.content.Context;
import android.content.res.Resources;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = {Term.class, Course.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class FullDatabase extends RoomDatabase {

//    private static final String DB_NAME = Resources.getSystem().getString(R.string.database_name);
    private static final String DB_NAME = "full_db15";
    private static FullDatabase instance;

    public static synchronized FullDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FullDatabase.class, DB_NAME).allowMainThreadQueries().build();

        }
        return instance;
    }

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
}
