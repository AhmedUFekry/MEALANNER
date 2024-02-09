package com.example.mealanner.DataLayer.Model.Services.Local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MealsDAO {
    @Query("SELECT * FROM meals")
    Flowable<List<Meal>> getAllMeals();

    @Insert
    void insertMeal(Meal meal);

    @Delete
    void deletMeal(Meal meal);
}
