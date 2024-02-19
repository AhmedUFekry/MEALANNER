package com.example.mealanner.UILayer.AppMainActivity.MealDetailsFragment.Presenter;

import android.util.Log;

import com.example.mealanner.DataLayer.Model.DataModels.Ingrediant;
import com.example.mealanner.DataLayer.Model.DataModels.Ingrediants;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Remote.NetworkCallBack;
import com.example.mealanner.DataLayer.Model.Services.Remote.Repository;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.UILayer.AppMainActivity.MealDetailsFragment.View.MealDetails;

import java.util.ArrayList;
import java.util.List;

public class MealDetailsPresenter implements NetworkCallBack {
    Repository repository;
    MealDetails _view;
    public MealDetailsPresenter(Repository repository, MealDetails _view) {
        this.repository = repository;
        this._view = _view;
    }
    public void getSpecificMeal(String mealID){
        repository.getDataFromAPI(MealDetailsPresenter.this, RepositoryImpl.MealID,mealID);
        Log.i("TAG", "showMealDetails from presenter");

    }


    @Override
    public void onSuccess(Object result) {
        Ingrediants mealIngredients = new Ingrediants();
        List<Ingrediant> ingredientsListObj = new ArrayList<>();
        // ingredients manipulation
        if (result.getClass() == Meals.class) {
            Meals response = (Meals) result;

            String str1 = response.getMeals().get(0).getStrIngredient1();
            if(str1.length() > 2 || str1 != null) {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str1);
                ingredientsListObj.add(ingrediant);
            }
            String str2 = response.getMeals().get(0).getStrIngredient2();
            if(str2.length() > 2 || str2 != null)
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str2);
                ingredientsListObj.add(ingrediant);
            }
            String str3 = response.getMeals().get(0).getStrIngredient3();
            if(str3.length() != 0)
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str3);
                ingredientsListObj.add(ingrediant);
            }
            String str4 = response.getMeals().get(0).getStrIngredient4();
            if(str4.length() != 0 )
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str4);
                ingredientsListObj.add(ingrediant);
            }
            String str5 = response.getMeals().get(0).getStrIngredient5();
            if(str5.length() != 0 )
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str5);
                ingredientsListObj.add(ingrediant);
            }
            String str6 = response.getMeals().get(0).getStrIngredient6();
            if(str6.length() != 0 )
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str6);
                ingredientsListObj.add(ingrediant);
            }
            String str7 = response.getMeals().get(0).getStrIngredient7();
            if(str7.length() != 0 )
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str7);
                ingredientsListObj.add(ingrediant);
            }
            String str8 = response.getMeals().get(0).getStrIngredient8();
            if(str8.length() != 0 )
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str8);
                ingredientsListObj.add(ingrediant);
            }
            String str9 = response.getMeals().get(0).getStrIngredient9();
            if(str9.length() != 0 )
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str9);
                ingredientsListObj.add(ingrediant);
            }
            String str10 = response.getMeals().get(0).getStrIngredient10();
            if(str10.length() != 0 )
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str10);
                ingredientsListObj.add(ingrediant);
            }
            String str11 = response.getMeals().get(0).getStrIngredient11();
            if(str11.length() != 0 )
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str11);
                ingredientsListObj.add(ingrediant);
            }
            String str12 = response.getMeals().get(0).getStrIngredient12();
            if(str12.length() != 0 )
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str12);
                ingredientsListObj.add(ingrediant);
            }
            String str13 = response.getMeals().get(0).getStrIngredient13();
            if(str13.length() != 0)
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str13);
                ingredientsListObj.add(ingrediant);
            }
            String str14 = response.getMeals().get(0).getStrIngredient14();
            if(str14.length() != 0 )
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str14);
                ingredientsListObj.add(ingrediant);
            }
            String str15 = response.getMeals().get(0).getStrIngredient15();
            if(str15.length() != 0 )
            {
                Ingrediant ingrediant = new Ingrediant();
                ingrediant.setStrIngredient(str15);
                ingredientsListObj.add(ingrediant);
            }


            mealIngredients.setMeals(ingredientsListObj);
            _view.showIngredients(mealIngredients);
            Log.i("TAG", "showMealDetails from presenter : " + response.getMeals().get(0).getStrMeal());
            _view.showMealDetails(response);

        }


    }

    @Override
    public void onFailure(String errorMsg) {
        Log.i("TAG", "onFailure: "+ errorMsg);

    }
}
