package com.example.mealanner.UILayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mealanner.R;

public class splashscreen extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        // Use a Handler to delay the start of the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity after the splash timeout
                Intent intent = new Intent(splashscreen.this, MainActivity.class);
                startActivity(intent);
                finish();  // Close the splash activity to prevent going back to it
            }
        }, SPLASH_TIMEOUT);
    }
}