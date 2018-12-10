package com.example.joelruhe.myapplication.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.SlidingTabsBasicFragment;
import com.example.joelruhe.myapplication.authentication.firebase.FireBaseLoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    Intent i= getIntent();
    String plant = i.getExtras().getString("plant");



    String plant_array[] = {plant};

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new android.transition.Explode());
        setContentView(R.layout.activity_main);

        //Menu icon for sidebar
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable
                (new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        int number = getIntent().getIntExtra("number", 0);

        if (plant_array.length == 0 && number == 0) {
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_myplants){

        }
        else if (id == R.id.nav_mynetwork){
            Intent i = new Intent(MainActivity.this, FireBaseLoginActivity.class);
            startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
        }
        else if (id == R.id.nav_settings){

        }
        else if (id == R.id.nav_logout){
            auth.signOut();
            finish();
            startActivity(getIntent());
        }
        return true;
    }
}








