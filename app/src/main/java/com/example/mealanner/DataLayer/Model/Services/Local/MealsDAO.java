package com.example.mealanner.DataLayer.Model.Services.Local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealsDAO {
    @Query("SELECT * FROM MEALS WHERE userID = :userIDD")
    LiveData<List<Meal>> getAllMeals(String userIDD);
    @Query("SELECT * FROM MEALS WHERE userID = :userIDD AND days IS NOT NULL")
    LiveData<List<Meal>> getAllCalenderMeals(String userIDD);

    @Insert
    void insertMeal(Meal meal);

    @Delete
    void deletMeal(Meal meal);
}
