package com.example.mealanner.UILayer.AppMainActivity.FavouriteFragment;

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
import com.example.mealanner.DataLayer.Model.Services.Remote.RemoteDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.Repository;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.R;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View.MealsRCAdapter;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View.MealsView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class FavouritFragment extends Fragment implements MealsView {


    FavouritsPresenter favouritsPresenter;
    Repository repository;
    RecyclerView favRC;
    ImageButton addToFavBtn;
    TextView filterTVv;
    String filterId;
    boolean isSaved;
    FavRCAdapter mealsRCAdapter;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    List<Meal>  recievedMeals;

    public FavouritFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve arguments
        recievedMeals = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favRC = view.findViewById(R.id.favouritsRC);
        addToFavBtn = view.findViewById(R.id.addmealToFavimageBtn);
        filterTVv = view.findViewById(R.id.favTitle);
        repository = RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(Void.class), LocalDataSourceImpl.getInstance(getContext().getApplicationContext()));
        favouritsPresenter = new FavouritsPresenter(repository , FavouritFragment.this);
        repository.getStoredMeals(user.getUid());
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        LiveData<List<Meal>> listLiveData = repository.getStoredMeals(user.getUid());
        listLiveData.observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                if (mealsRCAdapter == null) {
                    // Initialize adapter and set layout manager only if it's not already initialized
                    mealsRCAdapter = new FavRCAdapter(getContext(), meals);
                    StaggeredGridLayoutManager gridLayoutManager =
                            new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    favRC.setLayoutManager(gridLayoutManager);
                    favRC.setAdapter(mealsRCAdapter);
                } else {
                    // Update data if adapter is already initialized
                    mealsRCAdapter.setList(meals);
                    mealsRCAdapter.notifyDataSetChanged();
                }
            }
        });

        isSaved = false;
    }


    @Override
    public void showMealsByCategory(Meals result) {
        Log.i("TAG", "showMealsByCategory: " + result.getMeals().size() );
        if(result.getMeals().size() != 0) {
            Log.i("TAG", "showMealsByCategory: " + result.getMeals().size() );
            MealsRCAdapter mealsRCAdapter = new MealsRCAdapter(getContext(), result.getMeals());
            //mealsRCAdapter.getMealsFromLocal(mealsPresenter.getFromLocal());
            favRC.setAdapter(mealsRCAdapter);
            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            favRC.setLayoutManager(gridLayoutManager);
        }

    }

    @Override
    public void showMealsByCountry(Meals result) {

    }

    @Override
    public void showSavedMeals(LiveData<List<Meal>> meals) {
      /*  Log.i("TAG", "showSavedMeals: " + meals.getValue().size() );
        if(meals.getValue().size() != 0) {

            Log.i("TAG", "showSavedMeals: " + meals.getValue().size() );
            meals.observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
                @Override
                public void onChanged(List<Meal> meals) {
                   mealsRCAdapter = new FavRCAdapter(getContext(), meals);
                }
            });

            //mealsRCAdapter.getMealsFromLocal(mealsPresenter.getFromLocal());
            favRC.setAdapter(mealsRCAdapter);
            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            favRC.setLayoutManager(gridLayoutManager);
        }*/

    }

}