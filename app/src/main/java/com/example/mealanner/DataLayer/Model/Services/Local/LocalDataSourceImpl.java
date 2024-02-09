package com.example.mealanner.DataLayer.Model.Services.Local;

import android.content.Context;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class LocalDataSourceImpl implements LocalDataSource {
    private AppDataBase db;
    private MealsDAO mealsDAO;
    private Flowable<List<Meal>> meals;
    private static LocalDataSourceImpl instance = null;

    private LocalDataSourceImpl(Context context) {
        db = AppDataBase.getInstance(context.getApplicationContext());
        mealsDAO = db.getMealDAO();
        meals = mealsDAO.getAllMeals();
    }

    public static LocalDataSourceImpl getInstance(Context context) {
        if (instance == null) {
            instance = new LocalDataSourceImpl(context);
        }
        return instance;
    }

    public Completable insertMeal(Meal meal) {
        return Completable.fromAction(() -> mealsDAO.insertMeal(meal));
    }

    public Completable deleteMeal(Meal meal) {
        return Completable.fromAction(() -> mealsDAO.deletMeal(meal));
    }

    public Flowable<List<Meal>> getAllMeals() {
        return meals;
    }
}
