package com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View;

import com.example.mealanner.DataLayer.Model.DataModels.Categories;
import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Meal;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;

public interface HomeView {
    void showRandomMeal(Meals result);
    void showCountries(Countries result);
    void showCategories(Categories result);

}
