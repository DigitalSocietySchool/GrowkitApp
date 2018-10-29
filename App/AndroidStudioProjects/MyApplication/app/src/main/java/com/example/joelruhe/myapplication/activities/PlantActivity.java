package com.example.joelruhe.myapplication.activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.joelruhe.myapplication.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PlantActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.plant_screen_toolbar)
    Toolbar plantScreenToolbar;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.textView3)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        ButterKnife.bind(PlantActivity.this);

        // get access to the custom title view
        assert plantScreenToolbar != null;
        btnBack = plantScreenToolbar.findViewById(R.id.btn_back);
        assert btnBack != null;
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv.setText(getIntent().getStringExtra("DESCRIPTION"));
    }
}
