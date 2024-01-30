package com.example.mealanner.DataLayer.Model.Services.Local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;

import java.util.List;
@Dao
public interface MealsDAO {
    @Query("SELECT * FROM MEALS")
    LiveData<List<Meal>> getAllMealss();

    @Insert
    void insertMeal(Meal meal);

    @Delete
    void deletMeal(Meal meal);
}
