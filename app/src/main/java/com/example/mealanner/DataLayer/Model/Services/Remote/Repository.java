package com.example.mealanner.DataLayer.Model.Services.Remote;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface Repository<T> {
    public Flowable<List<Meal>> getStoredMeals();
    public void insertMeal(Meal meal);
    public void deleteMeal(Meal meal);

    public void getDataFromAPI(NetworkCallBack<T> networkCallBack ,int requestNumber , String... filter);

    }
