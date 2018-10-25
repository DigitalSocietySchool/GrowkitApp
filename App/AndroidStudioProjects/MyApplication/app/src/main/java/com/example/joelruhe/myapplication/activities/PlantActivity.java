package com.example.joelruhe.myapplication.activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.example.joelruhe.myapplication.R;
import butterknife.BindView;

public class PlantActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.plant_screen_toolbar)
    Toolbar plantScreenToolbar;
    @Nullable
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cactus);
    }
}
