package com.example.mealanner.DataLayer.Model.Services.Remote;

import com.example.mealanner.DataLayer.Model.DataModels.Categories;
import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Ingrediants;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Api_service {
    @GET("categories.php")
    Call<Categories> getAllCategories();
    @GET("list.php?a=list")
    Call<Countries> getAllCountries();
    @GET("list.php?i=list")
    Call<Ingrediants> getAllIngrediants();
    @GET("filter.php?")
    Call<Meals> filterMealsByIngredientID(@Query("i") String ingredientId);
    @GET("filter.php?")
    Call<Meals> filterMealsByCountryID(@Query("a") String countrytId);
    @GET("filter.php?")
    Call<Meals> filterMealsByCategoryID(@Query("c") String categorytId);
    @GET("lookup.php?")
    Call<Meals> getMealByID(@Query("i") String mealId);
    @GET("random.php")
    Call<Meals> getRandomMeal();

}
