package com.example.joelruhe.myapplication.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.SlidingTabsBasicFragment;
import com.example.joelruhe.myapplication.authentication.firebase.FireBaseLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    private FirebaseAuth auth;
    DatabaseReference databaseReference;

    public static final String MY_PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new android.transition.Explode());
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        String plant = intent.getStringExtra("plant");
        String plant_array[] = {plant};

        databaseReference = FirebaseDatabase.getInstance().getReference("Pins");

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String pinString = preferences.getString("pin", null);

       DatabaseReference plantsPerPin = databaseReference.child(pinString).child("Plants");

        plantsPerPin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot plantsPerPin : dataSnapshot.getChildren());
                ///String plant = dataSnapshot.getValue(String.class);
                Toast.makeText(MainActivity.this, "retrieved data", LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Menu icon for sidebar
        mDrawerlayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //Get firebase auth instance
        auth = FirebaseAuth.getInstance();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable
                (new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        int number = getIntent().getIntExtra("number", 0);

        if (plant_array.length == 0 && number == 0) {
           Intent startMain = new Intent(MainActivity.this, AddPlantActivity.class);
           startActivity(startMain);
           finish();
       }

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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








