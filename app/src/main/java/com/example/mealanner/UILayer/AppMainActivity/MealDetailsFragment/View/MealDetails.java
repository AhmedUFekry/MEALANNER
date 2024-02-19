package com.example.mealanner.UILayer.AppMainActivity.MealDetailsFragment.View;

import com.example.mealanner.DataLayer.Model.DataModels.Ingrediants;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;

public interface MealDetails {
    void showIngredients(Ingrediants result);
    void showMealDetails(Meals result);



}
