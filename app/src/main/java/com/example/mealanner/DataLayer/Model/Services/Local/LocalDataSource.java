package com.example.mealanner.DataLayer.Model.Services.Local;

import androidx.lifecycle.LiveData;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;

import java.util.List;

public interface LocalDataSource {
    void insertMeal(Meal meal);
    void deleteMeal(Meal meal);
    LiveData<List<Meal>> getAllMeals();

}
