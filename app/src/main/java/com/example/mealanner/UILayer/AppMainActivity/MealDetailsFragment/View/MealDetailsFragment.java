package com.example.mealanner.UILayer.AppMainActivity.MealDetailsFragment.View;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealanner.DataLayer.Model.DataModels.Ingrediants;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Local.LocalDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.NetworkCallBack;
import com.example.mealanner.DataLayer.Model.Services.Remote.RemoteDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.R;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.Presenter.HomePresenter;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.HomeFragment;
import com.example.mealanner.UILayer.AppMainActivity.MealDetailsFragment.Presenter.MealDetailsPresenter;
import com.example.mealanner.UILayer.AppMainActivity.SearchFragment.IngredientsRCAdapter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.net.URI;
import java.net.URL;

public class MealDetailsFragment extends Fragment implements NetworkCallBack , MealDetails {
    ImageView mealImageView;
    TextView mealTextView;
    ImageButton addToFavBtn;
    ImageButton addToCalenderBtn;
    RecyclerView ingredientsRC;
    TextView DescriptionTextView;
    YouTubePlayerView mealVideoView;
    RepositoryImpl repository;
    IngredientsRCAdapter ingredientsRCAdapter;
    MealDetailsPresenter mealDetailsPresenter;



    boolean isSaved;



    public MealDetailsFragment() {
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
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealImageView = view.findViewById(R.id.MealDetailimageView);
        mealTextView = view.findViewById(R.id.MealDetailName);
        addToFavBtn = view.findViewById(R.id.addmealToFavimageBtn);
        addToCalenderBtn = view.findViewById(R.id.calenderimageButton);
        ingredientsRC = view.findViewById(R.id.mealIngredientsRecyclerView);
        DescriptionTextView = view.findViewById(R.id.descriptiontextView);
        mealVideoView = view.findViewById(R.id.mealVideoView);
        repository = RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(Void.class), LocalDataSourceImpl.getInstance(getContext().getApplicationContext()));
        mealDetailsPresenter = new MealDetailsPresenter(repository , MealDetailsFragment.this);
        mealDetailsPresenter.getSpecificMeal("52772");

    }

    @Override
    public void onSuccess(Object result) {

    }

    @Override
    public void onFailure(String errorMsg) {

    }

    @Override
    public void showIngredients(Ingrediants result) {
        ingredientsRCAdapter = new IngredientsRCAdapter(getContext() ,result.getMeals());
        Log.i("TAG", "showIngredients: "+result.getMeals().get(3).getStrIngredient().toString());
        ingredientsRC.setAdapter(ingredientsRCAdapter);
        ingredientsRC.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        //localIngredients = result;
    }

    @Override
    public void showMealDetails(Meals result) {
        Glide.with(this).load(result.getMeals().get(0).getStrMealThumb()).apply(new RequestOptions().override(200,200).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground)).into(mealImageView);
        mealTextView.setText(result.getMeals().get(0).getStrMeal());
        Log.i("TAG", "showMealDetails: " + result.getMeals().get(0).getStrMeal());
        DescriptionTextView.setText(result.getMeals().get(0).getStrInstructions());
        mealVideoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);

                if (isAdded()) {
                    //youTubePlayer.cueVideo(result.getMeals().get(0).getStrYoutube().toString(), 0);
                    youTubePlayer.loadVideo(result.getMeals().get(0).getStrYoutube(), 0);
                }
            }
        });


    }
}