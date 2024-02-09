package com.example.mealanner.UILayer.AppMainActivity.FavouriteFragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Remote.NetworkCallBack;
import com.example.mealanner.DataLayer.Model.Services.Remote.Repository;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.Presenter.MealsPresenter;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View.MealsRCAdapterInterface;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View.MealsView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class FavouritsPresenter implements NetworkCallBack, MealsView {
    Repository repository;
    MealsView _view;
    MealsRCAdapterInterface mealsRCAdapterInterface;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    int filter;
    public static final int COUNTRIES = 1;
    public static final int CATEGORIES = 2;




    public FavouritsPresenter(Repository repository, FavouritFragment favouritFragment) {
        this.repository = repository;
        this._view = _view;
    }

    public void getMealsByCountries(String filterId){
        filter = COUNTRIES;
        repository.getDataFromAPI(FavouritsPresenter.this, RepositoryImpl.MEALSBYCountryID , filterId);
    }
    public void getMealsByCategories(String filterId){
        filter = CATEGORIES;
        Log.i("TAG", "getmealllllllllls  getMealsByCategories=  " + filterId);
        repository.getDataFromAPI(FavouritsPresenter.this, RepositoryImpl.MEALSBYCategoryID, filterId);
    }
    public void saveToLocal(Meal meal){
        meal.userID = user.getUid();
        repository.insertMeal(meal);}
    public void deleteFromLocal(Meal meal){ repository.deleteMeal(meal);}

    public FavouritsPresenter(Repository repository, MealsView _view) {
        this.repository = repository;
        this._view = _view;
    }



    // ... rest of the code ...

    public void getFromLocal() {
        LiveData<List<Meal>> listLiveData = repository.getStoredMeals(user.getUid());

        //Log.i("TAG", "getFromLocal: " + listLiveData.getValue().size());
        //_view.showSavedMeals(listLiveData);
    }




    @Override
    public void onSuccess(Object result) {
        Log.i("TAG", "filter in presenter =  " + filter);
        if (filter == CATEGORIES) {
            Meals response = (Meals) result;
            Log.i("TAG", "size =  " + response.getMeals().size());
            _view.showMealsByCategory(response);
            Log.i("TAG", "size =  " + response.getMeals().size());
        }else if (filter == COUNTRIES) {
            Meals response = (Meals) result;
            _view.showMealsByCountry(response);
        }
    }

    @Override
    public void onFailure(String errorMsg) {
        Log.i("TAG", "error =  " + errorMsg);

    }

    @Override
    public void showMealsByCategory(Meals result) {

    }

    @Override
    public void showMealsByCountry(Meals result) {

    }

    @Override
    public void showSavedMeals(LiveData<List<Meal>> meals) {

    }
}
