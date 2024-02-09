package com.example.mealanner.UILayer.AppMainActivity.HomeFragment.Presenter;

import com.example.mealanner.DataLayer.Model.DataModels.Categories;
import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Meal;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Remote.NetworkCallBack;
import com.example.mealanner.DataLayer.Model.Services.Remote.Repository;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.HomeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePresenter implements NetworkCallBack {

    Repository repository;
    HomeView _view;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public FirebaseUser user = auth.getCurrentUser();


    public HomePresenter(Repository repository, HomeView _view) {
        this.repository = repository;
        this._view = _view;
    }

    public void getRandomMeal(){ repository.getDataFromAPI(HomePresenter.this, RepositoryImpl.RANDOMMEAL);}
    public void getCountries(){ repository.getDataFromAPI(HomePresenter.this, RepositoryImpl.COUNTRIES);}
    public void getCategories(){ repository.getDataFromAPI(HomePresenter.this, RepositoryImpl.CATEGORIES);}
    public void saveToLocal(Meal meal){
        meal.userID = user.getUid();
        repository.insertMeal(meal);}
    public void deleteFromLocal(Meal meal){ repository.deleteMeal(meal);}




    @Override
    public void onSuccess(Object result) {
        if (result.getClass() == Meals.class) {
            Meals response = (Meals) result;
            _view.showRandomMeal(response);
        }else if (result.getClass() == Countries.class) {
            Countries response = (Countries) result;
            _view.showCountries(response);
        }else if (result.getClass() == Categories.class) {
            Categories response = (Categories) result;
            _view.showCategories(response);
        }

    }

    @Override
    public void onFailure(String errorMsg) {

    }
}
