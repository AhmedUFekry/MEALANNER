package com.example.mealanner.UILayer.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.mealanner.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        NavController navController = Navigation.findNavController(Login.this,R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(Login.this, navController);
    }
}