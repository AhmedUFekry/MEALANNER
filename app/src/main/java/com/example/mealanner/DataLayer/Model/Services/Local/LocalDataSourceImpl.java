package com.example.mealanner.DataLayer.Model.Services.Local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;

import java.util.List;

public class LocalDataSourceImpl implements LocalDataSource{
    //List<ProductDTo> dataBaseProducts;
    AppDataBase db;
    MealsDAO mealsDAO;
    public LiveData<List<Meal>> meals;
    private static LocalDataSourceImpl DS = null;
    private LocalDataSourceImpl(Context context) {
        db = AppDataBase.getInstance(context.getApplicationContext());
        mealsDAO = db.getMealDAO();
        meals = mealsDAO.getAllMealss();
    }
    public static LocalDataSourceImpl getInstance(Context context){
        if (DS == null){
            DS = new LocalDataSourceImpl(context);
        }
        return DS;
    }
    public void insertMeal(Meal meal){
        new Thread(() -> {
            mealsDAO.insertMeal(meal);
        }).start();
    }
    public void deleteMeal(Meal meal) {
        new Thread(() -> {
            mealsDAO.deletMeal(meal);
        }).start();
    }
    public LiveData<List<Meal>> getAllMeals(){
        return meals;
    }

}
