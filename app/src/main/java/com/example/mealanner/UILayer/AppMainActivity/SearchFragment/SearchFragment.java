package com.example.mealanner.UILayer.AppMainActivity.SearchFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.mealanner.DataLayer.Model.DataModels.Categories;
import com.example.mealanner.DataLayer.Model.DataModels.Category;
import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Country;
import com.example.mealanner.DataLayer.Model.DataModels.Meal;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Local.LocalDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.NetworkCallBack;
import com.example.mealanner.DataLayer.Model.Services.Remote.RemoteDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.Repository;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.R;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.Presenter.HomePresenter;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.CategoriesRCAdapter;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.CountriesRCAdapter;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.HomeFragment;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.HomeView;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.Presenter.MealsPresenter;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View.MealsFragment;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View.MealsRCAdapter;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View.MealsView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class SearchFragment extends Fragment implements NetworkCallBack, HomeView, MealsView {

    ChipGroup chipGroup;
    RecyclerView searchRC;
    HomePresenter homePresenter;
    MealsPresenter mealsPresenter;
    Repository repository;
    CategoriesRCAdapter categoriesRCAdapter;
    CountriesRCAdapter countriesRCAdapter;
    MealsRCAdapter mealsRCAdapter;
    Categories localCategories;
    Countries localCountries;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chipGroup = view.findViewById(R.id.chipsGroupID);
        searchRC = view.findViewById(R.id.searchRC);
        repository = RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(Void.class), LocalDataSourceImpl.getInstance(getContext().getApplicationContext()));
        homePresenter = new HomePresenter(repository, SearchFragment.this);
        mealsPresenter = new MealsPresenter(repository , SearchFragment.this);
        setupChips();
        homePresenter.getCountries();
        homePresenter.getCategories();

    }
    void setupChips(){
        for (int i=0; i<chipGroup.getChildCount();i++)
        {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i("TAG", "onCheckedChanged: click");
                    if(isChecked)
                    {
                        Log.i("TAG", "onCheckedChanged: check");
                        if (chip.getText().toString().equals("country"))
                        {
                            Log.i("TAG", "onCheckedChanged: country");
                            showCountries(localCountries);
                            searchRC.setAdapter(countriesRCAdapter);
                            countriesRCAdapter.notifyDataSetChanged();
                        } else if (chip.getText().toString().equals("category")) {
                            Log.i("TAG", "onCheckedChanged: category");
                            showCategories(localCategories);
                            searchRC.setAdapter(categoriesRCAdapter);
                            categoriesRCAdapter.notifyDataSetChanged();


                        }else if (chip.getText().toString().equals("ingredient")) {
                            Log.i("TAG", "onCheckedChanged: ingredient");
                           // adapter.setList(getSony());
                           // adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onSuccess(Object result) {

    }

    @Override
    public void onFailure(String errorMsg) {

    }

    @Override
    public void showRandomMeal(Meals result) {

    }

    @Override
    public void showCountries(Countries result) {
        Log.i("yala", "showCountries: " +result.getMeals().get(0).getStrArea());
        countriesRCAdapter = new CountriesRCAdapter(getContext() ,result.getMeals());
        countriesRCAdapter.setOnItemClickListener(country -> onCountryClick(country));
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        searchRC.setLayoutManager(gridLayoutManager);
        localCountries = result;
    }

    @Override
    public void showCategories(Categories result) {
        categoriesRCAdapter = new CategoriesRCAdapter(getContext() ,result.getCategories());
        categoriesRCAdapter.setOnItemClickListener(category -> onCategoryClick(category));
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        searchRC.setLayoutManager(gridLayoutManager);
        localCategories = result;
    }
    public void onCategoryClick(Category category) {
        mealsPresenter.getMealsByCategories(category.getStrCategory());
    }
    public void onCountryClick(Country country) {
        mealsPresenter.getMealsByCountries(country.getStrArea());

    }
    private void updateRecyclerViewWithMealsAdapter(RecyclerView.Adapter adapter) {
        searchRC.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        searchRC.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void showMealsByCategory(Meals result) {
        mealsRCAdapter = new MealsRCAdapter(getContext(), result.getMeals());
        updateRecyclerViewWithMealsAdapter(mealsRCAdapter);


    }

    @Override
    public void showMealsByCountry(Meals result) {
        mealsRCAdapter = new MealsRCAdapter(getContext() ,result.getMeals());
        updateRecyclerViewWithMealsAdapter(mealsRCAdapter);

    }

    @Override
    public void showSavedMeals(LiveData<List<Meal>> meals) {

    }
}