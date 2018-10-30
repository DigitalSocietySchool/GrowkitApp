package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.SlidingTabsBasicFragment;

public class MainActivity extends AppCompatActivity {

    int empty_array[][] = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(empty_array.length == 0){
            Intent i = new Intent(MainActivity.this, AddPlantActivity.class);
            startActivity(i);
            finish();
        }

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }
}

