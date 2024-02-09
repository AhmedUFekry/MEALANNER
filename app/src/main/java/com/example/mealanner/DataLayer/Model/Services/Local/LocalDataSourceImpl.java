package com.example.mealanner.DataLayer.Model.Services.Local;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.mealanner.DataLayer.Model.DataModels.Meal;
import java.util.List;

public class LocalDataSourceImpl implements LocalDataSource {
    private AppDataBase db;
    private MealsDAO mealsDAO;
    public LiveData<List<Meal>> meals;
    private static LocalDataSourceImpl instance = null;

    private LocalDataSourceImpl(Context context) {
        db = AppDataBase.getInstance(context.getApplicationContext());
        mealsDAO = db.getMealDAO();
        //meals = mealsDAO.getAllMeals();
    }

    public static LocalDataSourceImpl getInstance(Context context) {
        if (instance == null) {
            instance = new LocalDataSourceImpl(context);
        }
        return instance;
    }

    public void insertMeal(Meal meal) {
        new Thread(() -> {
            mealsDAO.insertMeal(meal);
        }).start();
    }

    public void deleteMeal(Meal meal) {
        new Thread(() -> {
            mealsDAO.deletMeal(meal);
        }).start();
    }

    public LiveData<List<Meal>> getAllMeals(String userID) {
        return mealsDAO.getAllMeals(userID);
    }

    @Override
    public LiveData<List<Meal>> getAllCalenderMeals(String userIDD) {return mealsDAO.getAllCalenderMeals(userIDD);}
}
