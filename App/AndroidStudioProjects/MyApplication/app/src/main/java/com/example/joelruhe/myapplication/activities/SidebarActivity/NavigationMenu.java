package com.example.joelruhe.myapplication.activities.SidebarActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.authentication.firebase.FireBaseLoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class NavigationMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new android.transition.Explode());
        setContentView(R.layout.navigation_menu);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_myplants){
        }
        else if (id == R.id.nav_mynetwork){
            Intent i = new Intent(NavigationMenu.this, FireBaseLoginActivity.class);
            startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation(NavigationMenu.this).toBundle());
        }
        else if (id == R.id.nav_settings){

        }
        else if (id == R.id.nav_logout){
            auth.signOut();
        }
        return true;
    }
}
