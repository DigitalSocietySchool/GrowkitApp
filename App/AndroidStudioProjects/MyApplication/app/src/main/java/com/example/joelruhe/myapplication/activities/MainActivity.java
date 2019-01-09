package com.example.joelruhe.myapplication.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.joelruhe.myapplication.CustomTypefaceSpan;
import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.SlidingTabsBasicFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActionBarDrawerToggle mToggle;

    private FirebaseAuth auth;
    DatabaseReference databaseReference;

    public static final String MY_PREFS_NAME = "MyPrefs";

    TextView navUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new android.transition.Explode());
        setContentView(R.layout.activity_main);

        //Menu icon for sidebar
        DrawerLayout mDrawerlayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable
                (new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            LayoutInflater inflater = LayoutInflater.from(this);
            @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.titleview, null);

            //if you need to customize anything else about the text, do it here.
            //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
            ((TextView) v.findViewById(R.id.title)).getText();
            Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
            ((TextView) v.findViewById(R.id.title)).setTypeface(myCustomFont);

            //assign the view to the actionbar
            this.getSupportActionBar().setCustomView(v);
        }

        Intent intent = this.getIntent();
        String plant = intent.getStringExtra("plant");
        String plant_array[] = {plant};

        databaseReference = FirebaseDatabase.getInstance().getReference("Pins");

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String pinString = preferences.getString("pin", null);

        assert pinString != null;
        DatabaseReference plantsPerPin = databaseReference.child(pinString).child("Plants");

        plantsPerPin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot plantsPerPin : dataSnapshot.getChildren());
                ///String plant = dataSnapshot.getValue(String.class);
                //Toast.makeText(MainActivity.this, "retrieved data", LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Get firebase auth instance
        auth = FirebaseAuth.getInstance();

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

        Menu menu = navigationView.getMenu();

        View headerView = navigationView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.username);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (auth.getCurrentUser() == null) {
            View header = navigationView.getHeaderView(0);
            header.setVisibility(View.GONE);
            MenuItem target = menu.findItem(R.id.nav_logout);
            target.setVisible(false);
        } else {
            View header = navigationView.getHeaderView(0);
            header.setVisibility(View.VISIBLE);
            MenuItem target = menu.findItem(R.id.nav_logout);
            target.setVisible(true);

            assert user != null;
            setDataToView(user);
            navUsername.setTypeface(myCustomFont);
        }

        for (int i=0;i < menu.size();i++) {
            MenuItem mi = menu.getItem(i);

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        MenuItem target = menu.findItem(R.id.add_plant_button);
        target.setVisible(false);

    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the actionbar if it's present
        getMenuInflater().inflate(R.menu.navigation_menu, menu);

        for (int i = 0; i < 4; i++) {
            menu.getItem(i).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        if (id == R.id.add_plant_button) {
            Intent i = new Intent(this, AddPlantMenuActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_my_plants){
            Intent i = new Intent(MainActivity.this, AddPlantMenuActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_my_network){
            Intent i = new Intent(MainActivity.this, JoinCommunityActivity.class);
            startActivity(i);
            finish();
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

    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {
        navUsername.setText(user.getEmail());
    }
}








