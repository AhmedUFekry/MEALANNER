package com.example.mealanner.UILayer.SplashScreenActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mealanner.R;
import com.example.mealanner.UILayer.AppMainActivity.MainActivity;
import com.example.mealanner.UILayer.LoginActivity.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splashscreen extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 3000;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        // Use a Handler to delay the start of the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user != null) {
                    // Start the main activity after the splash timeout
                    Intent intent = new Intent(splashscreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(splashscreen.this, Login.class);
                    startActivity(intent);
                    finish();

                }
                // Close the splash activity to prevent going back to it
            }
        }, SPLASH_TIMEOUT);
    }
}