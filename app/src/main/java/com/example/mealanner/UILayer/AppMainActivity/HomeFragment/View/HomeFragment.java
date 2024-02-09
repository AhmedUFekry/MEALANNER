package com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealanner.DataLayer.Model.DataModels.Categories;
import com.example.mealanner.DataLayer.Model.DataModels.Category;
import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Country;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Local.LocalDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.NetworkCallBack;
import com.example.mealanner.DataLayer.Model.Services.Remote.RemoteDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.Repository;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.R;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.Presenter.HomePresenter;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View.MealsFragment;

public class HomeFragment extends Fragment implements NetworkCallBack,HomeView {

    ImageView randomMealImageView;
    TextView randomMealTextView;
    HomePresenter homePresenter;
    Repository repository;
    RecyclerView countriesRC;
    RecyclerView categoriesRC;
    ImageButton addToFavBtn;
    boolean isSaved;


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
        addToFavBtn = view.findViewById(R.id.addToFavimageButton);
        repository = RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(Void.class), LocalDataSourceImpl.getInstance(getContext().getApplicationContext()));
        homePresenter = new HomePresenter(repository , HomeFragment.this);
        homePresenter.getRandomMeal();
        homePresenter.getCountries();
        homePresenter.getCategories();

        if(homePresenter.user == null){
            addToFavBtn.setVisibility(View.INVISIBLE);
        }
        isSaved = false;


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
        addToFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSaved == false) {
                    homePresenter.saveToLocal(result.getMeals().get(0));
                    addToFavBtn.setImageResource(android.R.drawable.btn_star_big_on);
                    Log.i("TAG", "added to local: " + result.getMeals().get(0).getStrMeal());
                    isSaved = true;
                }else {
                    isSaved = false;
                    addToFavBtn.setImageResource(android.R.drawable.btn_star_big_off);
                    homePresenter.deleteFromLocal(result.getMeals().get(0));
                    Log.i("TAG", "removed from local: " + result.getMeals().get(0).getStrMeal());
                }
            }
        });
    }
    public void onCategoryClick(Category category) {
        // Handle item click here, e.g., show details, navigate to another screen, etc.
        MealsFragment mealsFragment = new MealsFragment();
        // Pass any necessary data to the MealsFragment using arguments
        Bundle bundle = new Bundle();
        bundle.putString("categoryName", category.getStrCategory());
        bundle.putString("filterType","cate");
        mealsFragment.setArguments(bundle);
        // Replace the current fragment with the MealsFragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.appFragmentContainer, mealsFragment);
        transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
        transaction.commit();

    }
    public void onCountryClick(Country country) {
        // Handle item click here, e.g., show details, navigate to another screen, etc.
        MealsFragment mealsFragment = new MealsFragment();
        // Pass any necessary data to the MealsFragment using arguments
        Bundle bundle = new Bundle();
        bundle.putString("categoryName", country.getStrArea());
        bundle.putString("filterType","cont");
        mealsFragment.setArguments(bundle);
        // Replace the current fragment with the MealsFragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.appFragmentContainer, mealsFragment);
        transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
        transaction.commit();

    }

    @Override
    public void showCountries(Countries result) {
        Log.i("yala", "showCountries: " +result.getMeals().get(0).getStrArea());
        CountriesRCAdapter countriesRCAdapter = new CountriesRCAdapter(getContext() ,result.getMeals());
        countriesRCAdapter.setOnItemClickListener(country -> onCountryClick(country));
        countriesRC.setAdapter(countriesRCAdapter);
        countriesRC.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    public void showCategories(Categories result) {
        CategoriesRCAdapter categoriesRCAdapter = new CategoriesRCAdapter(getContext() ,result.getCategories());
        categoriesRCAdapter.setOnItemClickListener(category -> onCategoryClick(category));
        categoriesRC.setAdapter(categoriesRCAdapter);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        categoriesRC.setLayoutManager(gridLayoutManager);
    }
}