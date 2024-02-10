package com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Local.LocalDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.NetworkCallBack;
import com.example.mealanner.DataLayer.Model.Services.Remote.RemoteDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.Repository;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.R;
import com.example.mealanner.UILayer.AppMainActivity.FavouriteFragment.FavRCAdapter;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.Presenter.HomePresenter;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.CategoriesRCAdapter;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.HomeFragment;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.Presenter.MealsPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class MealsFragment extends Fragment implements MealsView{
    MealsPresenter mealsPresenter;
    Repository repository;
    RecyclerView mealsRC;
    ImageButton addToFavBtn;
    TextView filterTV;
    String filterId;
    boolean isSaved;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public FirebaseUser user = auth.getCurrentUser();


    public MealsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve arguments
        if (getArguments() != null) {
            filterId = getArguments().getString("categoryName");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealsRC = view.findViewById(R.id.mealsRecyclerView);
        addToFavBtn = view.findViewById(R.id.addmealToFavimageBtn);
        filterTV = view.findViewById(R.id.filterTV);
        repository = RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(Void.class), LocalDataSourceImpl.getInstance(getContext().getApplicationContext()));
        mealsPresenter = new MealsPresenter(repository , MealsFragment.this);
        filterTV.setText(filterId);
        //mealsPresenter.getMealsByCategories(filterId);
        if(getArguments().getString("filterType") == "cate") {
            mealsPresenter.getMealsByCategories(filterId);
        } else if(getArguments().getString("filterType") == "cont") {
            mealsPresenter.getMealsByCountries(filterId);
        }
        Log.i("TAG", "filter key : " + filterId);

        //filterTV.setText(filterId);
        isSaved = false;
    }


    @Override
    public void showMealsByCategory(Meals result) {
        Log.i("TAG", "showMealsByCategory: " + result.getMeals().size() );
        if(result.getMeals().size() != 0) {
            Log.i("TAG", "showMealsByCategory: " + result.getMeals().size() );
            MealsRCAdapter mealsRCAdapter = new MealsRCAdapter(getContext(), result.getMeals());
            //mealsRCAdapter.getMealsFromLocal(mealsPresenter.getFromLocal());
            mealsRC.setAdapter(mealsRCAdapter);
            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mealsRC.setLayoutManager(gridLayoutManager);
        }

    }

    @Override
    public void showMealsByCountry(Meals result) {
        MealsRCAdapter mealsRCAdapter = new MealsRCAdapter(getContext() ,result.getMeals());
       // mealsRCAdapter.getMealsFromLocal(mealsPresenter.getFromLocal());

        mealsRC.setAdapter(mealsRCAdapter);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mealsRC.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void showMealsByIngredients(Meals result) {
        MealsRCAdapter mealsRCAdapter = new MealsRCAdapter(getContext() ,result.getMeals());
        // mealsRCAdapter.getMealsFromLocal(mealsPresenter.getFromLocal());

        mealsRC.setAdapter(mealsRCAdapter);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mealsRC.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void showSavedMeals(LiveData<List<Meal>> meals) {

    }
}