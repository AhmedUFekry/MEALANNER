package com.example.mealanner.UILayer.AppMainActivity.MealsFragment.Presenter;

import com.example.mealanner.DataLayer.Model.DataModels.Categories;
import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Meal;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Remote.NetworkCallBack;
import com.example.mealanner.DataLayer.Model.Services.Remote.Repository;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.Presenter.HomePresenter;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.HomeView;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View.MealsView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MealsPresenter implements NetworkCallBack , MealsView {
    Repository repository;
    MealsView _view;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    int filter;
    public static final int COUNTRIES = 1;
    public static final int CATEGORIES = 2;



    public MealsPresenter(Repository repository, MealsView _view) {
        this.repository = repository;
        this._view = _view;
    }

    public void getMealsByCountries(String filterId){
        filter = COUNTRIES;
        repository.getDataFromAPI(MealsPresenter.this, RepositoryImpl.MEALSBYCountryID , filterId);
    }
    public void getMealsByCategories(String filterId){
        filter = CATEGORIES;
        repository.getDataFromAPI(MealsPresenter.this, RepositoryImpl.MEALSBYCategoryID, filterId);
    }
    public void saveToLocal(Meal meal){
        meal.userID = user.getUid();
        repository.insertMeal(meal);}
    public void deleteFromLocal(Meal meal){ repository.deleteMeal(meal);}




    @Override
    public void onSuccess(Object result) {
        if (filter == CATEGORIES) {
            Meals response = (Meals) result;
            _view.showMealsByCategory(response);
        }else if (filter == COUNTRIES) {
            Meals response = (Meals) result;
            _view.showMealsByCountry(response);
        }
    }

    @Override
    public void onFailure(String errorMsg) {

    }

    @Override
    public void showMealsByCategory(Meals result) {

    }

    @Override
    public void showMealsByCountry(Meals result) {

    }
}
