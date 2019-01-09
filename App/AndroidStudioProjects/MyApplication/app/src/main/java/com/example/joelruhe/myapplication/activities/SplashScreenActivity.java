package com.example.joelruhe.myapplication.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.transition.Explode;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

import com.example.joelruhe.myapplication.R;

public class SplashScreenActivity extends AppCompatActivity {
    AnimationDrawable plantAnimation;
    public static int SPLASH_TIME_OUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new android.transition.Explode());

        setContentView(R.layout.activity_splashscreen);

        SPLASH_TIME_OUT = 3000;

        ImageView imageView= findViewById(R.id.plantImage);
        imageView.setBackgroundResource(R.drawable.splashscreen_animation);
        plantAnimation = (AnimationDrawable) imageView.getBackground();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreenActivity.this, EnterPinActivity.class);
                startActivity(homeIntent,
                        ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this).toBundle());
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
