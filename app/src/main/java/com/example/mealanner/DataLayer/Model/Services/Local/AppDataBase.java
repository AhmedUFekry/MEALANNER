package com.example.mealanner.DataLayer.Model.Services.Local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;
@Database(entities = {Meal.class}, version = 1 , exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;

    public abstract MealsDAO getMealDAO();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "MEALS").build();
        return instance;
    }
}
