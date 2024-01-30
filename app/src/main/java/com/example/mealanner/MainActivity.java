package com.example.mealanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mealanner.DataLayer.Model.DataModels.Categories;
import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Ingrediants;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Local.LocalDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.NetworkCallBack;
import com.example.mealanner.DataLayer.Model.Services.Remote.RemoteDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.Repository;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;

public class MainActivity extends AppCompatActivity implements NetworkCallBack {
    Repository<Void> repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(Void.class), LocalDataSourceImpl.getInstance(MainActivity.this));
        repository.getDataFromAPI(MainActivity.this ,RepositoryImpl.MealID , "52903");


    }

    @Override
    public void onSuccess(Object result) {
        if (result.getClass() == Categories.class) {
            Categories response = (Categories) result;
            Log.i("TAG", "onSuccess: " + response.getCategories().get(2).getStrCategory());
        } else if (result.getClass() == Countries.class) {
            Countries response = (Countries) result;
            Log.i("TAG", "onSuccess: " + response.getMeals().get(2).getStrArea());
        } else if (result.getClass() == Ingrediants.class) {
            Ingrediants response = (Ingrediants) result;
            Log.i("TAG", "onSuccess: " + response.getMeals().get(2).getStrIngredient());
        } else if (result.getClass() == Meals.class) {
            Meals response = (Meals) result;
            Log.i("TAG", "onSuccess: " + response.getMeals().get(0).getStrMeal());
            repository.insertMeal(response.getMeals().get(0));
        }
    }

    @Override
    public void onFailure(String errorMsg) {
        Log.e("TAG", "onFailure: "+ errorMsg);

    }
}