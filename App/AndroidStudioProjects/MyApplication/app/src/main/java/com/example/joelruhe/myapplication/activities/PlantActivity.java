package com.example.joelruhe.myapplication.activities;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.joelruhe.myapplication.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PlantActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.plant_screen_toolbar)
    Toolbar plantScreenToolbar;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.imageDrop)
    ImageView drop;

    @BindView(R.id.text_plant_data)
    TextView txtPlantData;
    @BindView(R.id.view_id)
    TextView plantId;
    @BindView(R.id.water_value)
    TextView waterValue;
    @BindView(R.id.textViewHealth)
    TextView plantHealth;
    @BindView(R.id.temperature_value)
    TextView temperatureValue;
    @BindView(R.id.light_value)
    TextView lightValue;
    @BindString(R.string.percent)
    String percent;

    ProgressBar progressBar;
    TextView textCounter;
    MyCountDownTimer myCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        ButterKnife.bind(PlantActivity.this);

        progressBar = findViewById(R.id.progressBar);
        textCounter = findViewById(R.id.counter);

        progressBar.setProgress(100);
        myCountDownTimer = new MyCountDownTimer(10000, 500);
        myCountDownTimer.start();

        String idString;
        int id;

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
        txtPlantData.setText(getIntent().getStringExtra("DESCRIPTION"));
        id = getIntent().getIntExtra("ID", 0);
        idString = Integer.toString(getIntent().getIntExtra("ID", 0));
        plantId.setText(idString);

        showValues(id, getPlantData());
    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textCounter.setText(String.valueOf(millisUntilFinished));
            int progress = (int) (millisUntilFinished / 100);
            progressBar.setProgress(progress);
        }

        @Override
        public void onFinish() {
            textCounter.setText(R.string.finish_harvest_time);
            progressBar.setProgress(0);
        }
    }

    void showValues(int id, int plantarray[][]) {
        int water = plantarray[id][0];
        int temperature = plantarray[id][1];
        int light = plantarray[id][2];

        showIcons(id, plantarray);
        showHealth(id, water, temperature, light);
        showIconValue(id, water, temperature, light);
    }

    int calculateHealth(int id, int water, int temperature, int soil) {
        int health = 0;

        if (water < soil && water < temperature) {
            health = water;
        }

        if (temperature < water && temperature < soil) {
            health = temperature;
        }

        if (soil < water && soil < temperature) {
            health = soil;
        }

        return health;
    }

    void showHealth(int id, int water, int temperature, int soil) {
        plantHealth.setText("health:" + calculateHealth(id, water, temperature, soil));
    }

    void showIcons(int id, int plantarray[][]) {
        int[] dropIcons = new int[]{R.drawable.drop, R.drawable.drop1, R.drawable.drop2};

        int water = plantarray[id][0];
        if (water >= 66) {
            drop.setImageResource(dropIcons[0]);
        }
        if (water < 66 && water > 33) {
            drop.setImageResource(dropIcons[1]);
        }
        if (water <= 33) {
            drop.setImageResource(dropIcons[2]);
        }

        int light = plantarray[id][0];
        if (light >= 66) {
        }
        if (light < 66 && light > 33) {
        }
        if (light <= 33) {
        }

        int temperature = plantarray[id][0];
        if (temperature >= 66) {
        }
        if (temperature < 66 && temperature > 33) {
        }
        if (temperature <= 33) {
        }
    }

    void showIconValue(int id, int water, int temperature, int light) {
        waterValue.setText(water + getString(R.string.percent));
        lightValue.setText(light + getString(R.string.percent));
        temperatureValue.setText(temperature + getString(R.string.percent));
    }


    int[][] getPlantData() {
        //These values will be pulled from the database!
        int plantarray[][] = {
                {44, 60, 78},
                {98, 88, 92},
                {5, 22, 11},
                {66, 28, 55},
                {44, 7, 29}
        };

        return plantarray;
    }
}
