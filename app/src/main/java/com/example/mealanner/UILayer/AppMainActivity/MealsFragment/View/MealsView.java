package com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View;

import androidx.lifecycle.LiveData;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;

import java.util.List;

public interface MealsView {
    void showMealsByCategory(Meals result);
    void showMealsByCountry(Meals result);

    void showSavedMeals(LiveData<List<Meal>> meals);
}
