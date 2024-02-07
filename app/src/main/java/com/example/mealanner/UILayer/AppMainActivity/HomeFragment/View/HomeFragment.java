package com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealanner.DataLayer.Model.DataModels.Categories;
import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Local.LocalDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.NetworkCallBack;
import com.example.mealanner.DataLayer.Model.Services.Remote.RemoteDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.Repository;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.R;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.Presenter.HomePresenter;
import com.example.mealanner.UILayer.AppMainActivity.MainActivity;

public class HomeFragment extends Fragment implements NetworkCallBack,HomeView {

    ImageView randomMealImageView;
    TextView randomMealTextView;
    HomePresenter homePresenter;
    Repository repository;
    RecyclerView countriesRC;
    RecyclerView categoriesRC;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        randomMealImageView = view.findViewById(R.id.randomMealimageView);
        randomMealTextView = view.findViewById(R.id.randomMealName);
        countriesRC = view.findViewById(R.id.coutriesRecyclerView);
        categoriesRC = view.findViewById(R.id.categoriesRecyclerView);
        repository = RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(Void.class), LocalDataSourceImpl.getInstance(getContext().getApplicationContext()));
        homePresenter = new HomePresenter(repository , HomeFragment.this);
        homePresenter.getRandomMeal();
        homePresenter.getCountries();
        homePresenter.getCategories();

    }

    @Override
    public void onSuccess(Object result) {

    }

    @Override
    public void onFailure(String errorMsg) {

    }

    @Override
    public void showRandomMeal(Meals result) {
        Log.i("TAG", "showRandomMeal: " + result.getMeals().get(0).getStrMeal().toString());
        randomMealTextView.setText(result.getMeals().get(0).getStrMeal().toString());
        Glide.with(HomeFragment.this).load(result.getMeals().get(0).getStrMealThumb()).apply(new RequestOptions().override(400,200).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground)).into(randomMealImageView);

    }

    @Override
    public void showCountries(Countries result) {
        Log.i("yala", "showCountries: " +result.getMeals().get(0).getStrArea());
        CountriesRCAdapter countriesRCAdapter = new CountriesRCAdapter(getContext() ,result.getMeals());
        countriesRC.setAdapter(countriesRCAdapter);
        countriesRC.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    public void showCategories(Categories result) {
        CategoriesRCAdapter categoriesRCAdapter = new CategoriesRCAdapter(getContext() ,result.getCategories());
        categoriesRC.setAdapter(categoriesRCAdapter);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        categoriesRC.setLayoutManager(gridLayoutManager);
    }
}