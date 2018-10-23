package com.example.joelruhe.myapplication;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    @Nullable
    //@BindView(R.id.mainScreenToolbar)
    Toolbar mainToolbar;

    /*int i = 0;
    ImageView image;

    int [] images = new int[] {R.drawable.plant1, R.drawable.plant2, R.drawable.plants,
            R.drawable.cactus, R.drawable.potplant, R.drawable.longgrass};*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);*/



        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    /*void next_plant(View v){
        i++;
        if(i > 5){
            i=0;
        }
        image.setImageResource(images[i]);

    }

    void previous_plant(View v){
        i--;
        if(i < 0){
            i=5;
        }
        image.setImageResource(images[i]);
    }*/



}

