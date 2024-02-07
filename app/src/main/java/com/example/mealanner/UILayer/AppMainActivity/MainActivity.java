package com.example.mealanner.UILayer.AppMainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.mealanner.DataLayer.Model.DataModels.Categories;
import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Ingrediants;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Local.LocalDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.NetworkCallBack;
import com.example.mealanner.DataLayer.Model.Services.Remote.RemoteDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.Repository;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.R;
import com.example.mealanner.UILayer.AppMainActivity.CalenderFragment.CalenderFragment;
import com.example.mealanner.UILayer.AppMainActivity.FavouriteFragment.FavouritFragment;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.HomeFragment;
import com.example.mealanner.UILayer.AppMainActivity.SearchFragment.SearchFragment;
import com.example.mealanner.UILayer.LoginActivity.Login;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity implements NetworkCallBack {
    private MeowBottomNavigation bottomNavigation;
    private final static int HOME = 1;
    private final static int SEARCHE = 2;
    private final static int CALENDER = 3;
    private final static int FAVOURITS = 4;
    private ImageButton logoutBtn;
    FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;




    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.buttom_nav_bar);
        logoutBtn = findViewById(R.id.logoutImageBTN);
        bottomNavigation.add(new MeowBottomNavigation.Model(HOME , R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(SEARCHE , R.drawable.search));
        bottomNavigation.add(new MeowBottomNavigation.Model(CALENDER , R.drawable.calender));
        bottomNavigation.add(new MeowBottomNavigation.Model(FAVOURITS , R.drawable.favourits));
        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        //set current selected fragment
        bottomNavigation.show(HOME , true);

        // bottomNav Functions
        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                Fragment fragment = new HomeFragment();
                if(model.getId() == 1)
                    fragment = new HomeFragment();
                else if (model.getId() == 2)
                    fragment = new SearchFragment();
                else if (model.getId() == 3)
                    fragment = new CalenderFragment();
                else if (model.getId() == 4)
                    fragment = new FavouritFragment();
                //methode for load and replace fragments
                loadAndReplaceFragment(fragment);


                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                return null;
            }
        });


        repository = RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(Void.class), LocalDataSourceImpl.getInstance(MainActivity.this));
        repository.getDataFromAPI(MainActivity.this ,RepositoryImpl.CATEGORIES );

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out from Firebase
                auth.signOut();

                // Sign out from Google
                googleSignInClient.signOut().addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Redirect to login activity
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });



    }

    private void loadAndReplaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.appFragmentContainer,fragment,null)
                .commit();
    }

    @Override
    public void onSuccess(Object result) {
        if (result.getClass() == Categories.class) {
            Categories response = (Categories) result;
//            Log.i("TAG", "onSuccess: " + response.getCategories().get(2).getStrCategory());
        } else if (result.getClass() == Countries.class) {
            Countries response = (Countries) result;
            Log.i("TAG", "onSuccess: " + response.getMeals().get(2).getStrArea());
        } else if (result.getClass() == Ingrediants.class) {
            Ingrediants response = (Ingrediants) result;
            Log.i("TAG", "onSuccess: " + response.getMeals().get(2).getStrIngredient());
        } else if (result.getClass() == Meals.class) {
            Meals response = (Meals) result;
            Log.i("TAG", "onSuccess: " + response.getMeals().get(0).getStrMeal());
            repository.insertMeal(response.getMeals().get(0));
        }
    }

    @Override
    public void onFailure(String errorMsg) {
        Log.e("TAG", "onFailure: "+ errorMsg);

    }
}