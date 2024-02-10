package com.example.mealanner.UILayer.AppMainActivity.MealsFragment.Presenter;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.mealanner.DataLayer.Model.DataModels.Categories;
import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Meal;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Remote.NetworkCallBack;
import com.example.mealanner.DataLayer.Model.Services.Remote.Repository;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.Presenter.HomePresenter;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.HomeView;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View.MealsRCAdapterInterface;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View.MealsView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsPresenter implements NetworkCallBack , MealsView {
    Repository repository;
    MealsView _view;
    MealsRCAdapterInterface mealsRCAdapterInterface;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public FirebaseUser user = auth.getCurrentUser();
    int filter;
    public static final int COUNTRIES = 1;
    public static final int CATEGORIES = 2;
    public static final int  INGREDIENTS = 3;



    public MealsPresenter(Repository repository, MealsView _view) {
        this.repository = repository;
        this._view = _view;
    }

    public void getMealsByCountries(String filterId){
        filter = COUNTRIES;
        repository.getDataFromAPI(MealsPresenter.this, RepositoryImpl.MEALSBYCountryID , filterId);
    }

    public void getMealsByIngredient(String filterId){
        filter = INGREDIENTS;
        Log.i("TAG", "getMealsByIngredient: " + filter);
        repository.getDataFromAPI(MealsPresenter.this, RepositoryImpl.MEALSBYINGREDIENTSID , filterId);
    }
    public void searchMeal(String filterId){
        filter = CATEGORIES;
        repository.getDataFromAPI(MealsPresenter.this, RepositoryImpl.SEARCHBYNAME , filterId);
    }

    public void getMealsByCategories(String filterId){
        filter = CATEGORIES;
        Log.i("TAG", "getmealllllllllls  getMealsByCategories=  " + filterId);
        repository.getDataFromAPI(MealsPresenter.this, RepositoryImpl.MEALSBYCategoryID, filterId);
    }

    public void saveToLocal(Meal meal){
        meal.userID = user.getUid();
        repository.insertMeal(meal);}
    public void deleteFromLocal(Meal meal){ repository.deleteMeal(meal);}
    public List<Meal> getFromLocal(){
            //mealsRCAdapterInterface.getMealsFromLocal((List<Meal>) repository.getStoredMeals());
        LiveData<List<Meal>> listLiveData = repository.getStoredMeals(user.getUid());
        List<Meal> list = new ArrayList<>();
        listLiveData.observe((LifecycleOwner) MealsPresenter.this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                list.addAll(meals);
                Log.i("TAG", "Local Meals Size " + meals.size());

            }
        });




        return list;
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
        }else if (filter == INGREDIENTS) {
            Meals response = (Meals) result;
            _view.showMealsByIngredients(response);
        }
    }

    @Override
    public void onFailure(String errorMsg) {
        Log.i("TAG", "eror =  " + errorMsg);

    }

    @Override
    public void showMealsByCategory(Meals result) {

    }

    @Override
    public void showMealsByCountry(Meals result) {

    }

    @Override
    public void showMealsByIngredients(Meals result) {

    }

    @Override
    public void showSavedMeals(LiveData<List<Meal>> meals) {

    }
}
