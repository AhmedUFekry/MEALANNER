package com.example.mealanner.UILayer.AppMainActivity.SearchFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.mealanner.DataLayer.Model.DataModels.Categories;
import com.example.mealanner.DataLayer.Model.DataModels.Category;
import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Country;
import com.example.mealanner.DataLayer.Model.DataModels.Ingrediant;
import com.example.mealanner.DataLayer.Model.DataModels.Ingrediants;
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
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.HomeView;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.Presenter.MealsPresenter;
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
    IngredientsRCAdapter ingredientsRCAdapter;
    Categories localCategories;
    Countries localCountries;
    Ingrediants localIngredients;
    EditText searchEditText;





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
        searchEditText = view.findViewById(R.id.searchEditText);
        repository = RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(Void.class), LocalDataSourceImpl.getInstance(getContext().getApplicationContext()));
        homePresenter = new HomePresenter(repository, SearchFragment.this);
        mealsPresenter = new MealsPresenter(repository , SearchFragment.this);
        setupChips();
        homePresenter.getCountries();
        homePresenter.getCategories();
        homePresenter.getIngredients();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable text) {
                String searchText = text.toString().toLowerCase();
              mealsPresenter.searchMeal(searchText);
            }
        });

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
                            showIngredients(localIngredients);
                            searchRC.setAdapter(ingredientsRCAdapter);
                            ingredientsRCAdapter.notifyDataSetChanged();

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

    @Override
    public void showIngredients(Ingrediants result) {
        ingredientsRCAdapter = new IngredientsRCAdapter(getContext() ,result.getMeals());
        Log.i("TAG", "showIngredients: "+result.getMeals().get(0).getStrIngredient().toString());
        ingredientsRCAdapter.setOnItemClickListener(ingrediant -> onIngredientClick(ingrediant));
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        searchRC.setLayoutManager(gridLayoutManager);
        localIngredients = result;
    }

    public void onCategoryClick(Category category) {
        mealsPresenter.getMealsByCategories(category.getStrCategory());
    }
    public void onCountryClick(Country country) {
        Log.i("TAG", "o44444444444nCountryClick: ");
        mealsPresenter.getMealsByCountries(country.getStrArea());

    }
    public void onIngredientClick(Ingrediant ingrediant) {
        Log.i("TAG", "onIngredientClick: " + ingrediant.getStrIngredient().toString());
        mealsPresenter.getMealsByIngredient(ingrediant.getStrIngredient());

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
    public void showMealsByIngredients(Meals result) {
        mealsRCAdapter = new MealsRCAdapter(getContext() ,result.getMeals());
        updateRecyclerViewWithMealsAdapter(mealsRCAdapter);
    }

    @Override
    public void showSavedMeals(LiveData<List<Meal>> meals) {

    }
}