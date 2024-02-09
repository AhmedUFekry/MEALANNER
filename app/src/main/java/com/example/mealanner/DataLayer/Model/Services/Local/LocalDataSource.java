package com.example.mealanner.DataLayer.Model.Services.Local;

import androidx.lifecycle.LiveData;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public interface LocalDataSource {
    Completable insertMeal(Meal meal);
    Completable deleteMeal(Meal meal);
    Flowable<List<Meal>> getAllMeals();

}
