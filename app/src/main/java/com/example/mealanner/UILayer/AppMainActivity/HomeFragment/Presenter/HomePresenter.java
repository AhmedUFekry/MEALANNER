package com.example.mealanner.UILayer.AppMainActivity.HomeFragment.Presenter;

import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Remote.NetworkCallBack;
import com.example.mealanner.DataLayer.Model.Services.Remote.Repository;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.HomeView;

public class HomePresenter implements NetworkCallBack {

    Repository repository;
    HomeView _view;


    public HomePresenter(Repository repository, HomeView _view) {
        this.repository = repository;
        this._view = _view;
    }

    public void getRandomMeal(){ repository.getDataFromAPI(HomePresenter.this, RepositoryImpl.RANDOMMEAL);}
    public void getCountries(){ repository.getDataFromAPI(HomePresenter.this, RepositoryImpl.COUNTRIES);}
    @Override
    public void onSuccess(Object result) {
        if (result.getClass() == Meals.class) {
            Meals response = (Meals) result;
            _view.showRandomMeal(response);
        }else if (result.getClass() == Countries.class) {
            Countries response = (Countries) result;
            _view.showCountries(response);
        }

    }

    @Override
    public void onFailure(String errorMsg) {

    }
}
