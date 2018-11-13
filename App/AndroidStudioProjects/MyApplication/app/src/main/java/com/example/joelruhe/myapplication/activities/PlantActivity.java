package com.example.joelruhe.myapplication.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.Toast;
import com.example.joelruhe.myapplication.R;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlantActivity extends AppCompatActivity {

    @BindView(R.id.image_drop)
    ImageView drop;
    @BindView(R.id.water_alert)
    ImageView alertWater;
    @BindView(R.id.water_check)
    ImageView checkWater;
    @BindView(R.id.water_warning)
    ImageView warningWater;

    @BindView(R.id.text_plant_data)
    TextView txtPlantData;
    @BindView(R.id.view_id)
    TextView plantId;
    @BindView(R.id.counter)
    TextView textCounter;
    @BindView(R.id.textViewHealth)
    TextView plantHealth;
    @BindString(R.string.percent)
    String percent;

    @BindView(R.id.img_btn_start_harvest)
    ImageButton imgBtnStartHarvest;
    @BindView(R.id.img_btn_reset_harvest)
    ImageButton imgBtnResetHarvest;

    ProgressBar progressBar;
    int counter;
    int progress;
    CountDownTimer mCounterTimer;

    String idString;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        ButterKnife.bind(PlantActivity.this);

        alertWater.setVisibility(View.GONE);
        warningWater.setVisibility(View.GONE);

        imgBtnResetHarvest.setVisibility(View.GONE);

        txtPlantData.setText(getIntent().getStringExtra("DESCRIPTION"));
        id = getIntent().getIntExtra("ID", 0);
        idString = Integer.toString(getIntent().getIntExtra("ID", 0));
        plantId.setText(idString);

        imgBtnStartHarvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startTimer(id, getPlantData());
                    imgBtnResetHarvest.setVisibility(View.VISIBLE);
                    imgBtnStartHarvest.setVisibility(View.GONE);
            }
        });

        imgBtnResetHarvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mCounterTimer.cancel();
                    imgBtnResetHarvest.setVisibility(View.GONE);
                    imgBtnStartHarvest.setVisibility(View.VISIBLE);
            }
        });

        showValues(id, getPlantData());
    }

    void startTimer(int id, int plantArray[][]) {
        final int totalSeconds = plantArray[id][0];
        counter = totalSeconds;
        /*Intent intent = new Intent(PlantActivity.this, BroadcastTimerService.class);
        intent.putExtra("HARVEST_TIME", totalSeconds);*/

            progressBar = findViewById(R.id.progressBar);

            mCounterTimer = new CountDownTimer(totalSeconds * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    counter--;
                    progress++;
                    textCounter.setText(String.valueOf(counter));
                    progressBar.setProgress(progress * 10000 / (totalSeconds * 100));
                }

                public void onFinish() {
                    counter--;
                    textCounter.setText(R.string.finish_harvest_time);
                    progressBar.setProgress(100);
                }
            };
            mCounterTimer.start();
    }

    void showValues(int id, int plantArray[][]) {
        int water = plantArray[id][1];
        int temperature = plantArray[id][2];
        int light = plantArray[id][3];

        showIcons(id, plantArray);
        showHealth(id, water, temperature, light);
        showIconValue(water, temperature, light);
    }

    int calculateHealth(int water, int temperature, int soil) {
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

    void showHealth(int id, int water, int temperature, int light) {
        plantHealth.setText("health:" + calculateHealth(water, temperature, light));
    }

    void showIcons(int id, int plantArray[][]) {
        int water = plantArray[id][1];

        if (water <= 50 && water > 33) {
            warningWater.setVisibility(View.VISIBLE);
            checkWater.setVisibility(View.GONE);
            Toast.makeText(this, "I am a little thirsty", Toast.LENGTH_LONG).show();
        }

        if (water <= 33) {
            alertWater.setVisibility(View.VISIBLE);
            checkWater.setVisibility(View.GONE);
            Toast.makeText(this, "I am dying of thirst", Toast.LENGTH_LONG).show();
        }

        int light = plantArray[id][2];
        if (light >= 66) {
        }
        if (light < 66 && light > 33) {
        }
        if (light <= 33) {
        }

        int temperature = plantArray[id][3];
        if (temperature >= 66) {
        }
        if (temperature < 66 && temperature > 33) {
        }
        if (temperature <= 33) {
        }
    }

    @SuppressLint("SetTextI18n")
    void showIconValue(int water, int temperature, int light) {
    }

    int[][] getPlantData() {
        //These values will be pulled from the database!
        int plantArray[][] = {
                {10, 44, 60, 78},
                {20, 98, 88, 92},
                {30, 5, 22, 11},
                {15, 66, 28, 55},
                {10, 44, 7, 29}
        };
        return plantArray;
    }
}
