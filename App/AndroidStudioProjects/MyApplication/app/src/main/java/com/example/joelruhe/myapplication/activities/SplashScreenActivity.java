package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.joelruhe.myapplication.R;

public class SplashScreenActivity extends AppCompatActivity {
    AnimationDrawable plantAnimation;
    public static int SPLASH_TIME_OUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        SPLASH_TIME_OUT = 4000; 

        ImageView imageView= findViewById(R.id.plantImage);
        imageView.setBackgroundResource(R.drawable.splashscreen_animation);
        plantAnimation = (AnimationDrawable) imageView.getBackground();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        plantAnimation.start();
    }
}
