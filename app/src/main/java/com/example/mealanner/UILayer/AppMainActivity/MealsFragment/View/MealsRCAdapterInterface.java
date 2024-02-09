package com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;

import java.util.List;

public interface MealsRCAdapterInterface {
    void getMealsFromLocal(List<Meal> localMeals);
}
