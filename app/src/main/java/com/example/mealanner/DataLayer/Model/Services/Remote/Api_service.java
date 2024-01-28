package com.example.mealanner.DataLayer.Model.Services.Remote;

import com.example.mealanner.DataLayer.Model.DataModels.Categories;
import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Ingrediants;

import retrofit2.Call;
import retrofit2.http.GET;


public interface Api_service {
    @GET("list.php?c=list")
    Call<Categories> getAllCategories();
    @GET("list.php?a=list")
    Call<Countries> getAllCountries();

    @GET("list.php?i=list")
    Call<Ingrediants> getAllIngrediants();

}
