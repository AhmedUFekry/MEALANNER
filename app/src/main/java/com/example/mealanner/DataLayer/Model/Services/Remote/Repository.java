package com.example.mealanner.DataLayer.Model.Services.Remote;

import androidx.lifecycle.LiveData;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;

import java.util.List;

public interface Repository<T> {
    LiveData<List<Meal>> getStoredMeals();
    void insertMeal(Meal meal);
    void deleteMeal(Meal meal);

    public void getDataFromAPI(NetworkCallBack<T> networkCallBack ,int requestNumber , String... filter);

    }
