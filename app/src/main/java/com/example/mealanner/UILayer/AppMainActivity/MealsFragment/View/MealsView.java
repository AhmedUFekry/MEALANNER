package com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View;

import com.example.mealanner.DataLayer.Model.DataModels.Meals;

public interface MealsView {
    void showMealsByCategory(Meals result);
    void showMealsByCountry(Meals result);
}
