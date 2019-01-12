package com.example.joelruhe.myapplication.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joelruhe.myapplication.R;
import com.tooltip.OnDismissListener;
import com.tooltip.Tooltip;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlantActivity extends AppCompatActivity {

    @BindView(R.id.water_alert)
    ImageView alertWater;
    @BindView(R.id.text_plant_data)
    TextView txtPlantData;
    @BindView(R.id.text_plant_data_toolbar)
    TextView txtPlantDataToolbar;
    @BindView(R.id.text_plant_health)
    TextView txtPlantHealth;
    @BindView(R.id.text_plant_harvest_time_start)
    TextView txtPlantHarvestTimeStart;
    @BindView(R.id.text_plant_harvest_time_left)
    TextView txtPlantHarvestTimeLeft;
    /*@BindView(R.id.view_id)
    TextView plantId;
    @BindView(R.id.counter)
    TextView textCounter;
    @BindView(R.id.textViewHealth)
    TextView plantHealth;*/
    @BindString(R.string.percent)
    String percent;

    @BindView(R.id.img_btn_start_harvest)
    ImageButton imgBtnStartHarvest;
    @BindView(R.id.img_btn_reset_harvest)
    ImageButton imgBtnResetHarvest;

    @BindView(R.id.water_icon)
    TextView iconWater;
    @BindView(R.id.light_icon)
    TextView iconLight;
    @BindView(R.id.temp_icon)
    TextView iconTemp;

    ProgressBar progressBar;
    int counter;
    int progress;
    CountDownTimer mCounterTimer;

    String idString;
    int id;
    ImageView imgPlant;
    int countClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        ButterKnife.bind(PlantActivity.this);

        alertWater.setVisibility(View.GONE);
        imgBtnResetHarvest.setVisibility(View.GONE);

        txtPlantData.setText(getIntent().getStringExtra("DESCRIPTION"));
        txtPlantDataToolbar.setText(getIntent().getStringExtra("DESCRIPTION"));
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        txtPlantData.setTypeface(myCustomFont);
        txtPlantDataToolbar.setTypeface(myCustomFont);

        imgPlant = findViewById(R.id.imageViewPlant);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int resId = bundle.getInt("plantImage");
            imgPlant.setImageResource(resId);
        }

        id = getIntent().getIntExtra("ID", 0);
        idString = Integer.toString(getIntent().getIntExtra("ID", 0));
        //plantId.setText(idString);

        //textviewWater.setShadowLayer(30, 0, 0, Color.BLACK);

        imgBtnStartHarvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startTimer(id, getPlantData());
                imgBtnResetHarvest.setVisibility(View.VISIBLE);
                imgBtnStartHarvest.setVisibility(View.GONE);
            }
        });

        imgBtnResetHarvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounterTimer.cancel();
                progressBar.setProgress(0);
                //textCounter.setText("");
                mCounterTimer = null;
                imgBtnResetHarvest.setVisibility(View.GONE);
                imgBtnStartHarvest.setVisibility(View.VISIBLE);
            }
        });

        showValues(id, getPlantData());

        startService(new Intent(this, BroadcastTimerService.class));

    }

    @OnClick({R.id.water_icon, R.id.light_icon, R.id.temp_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.water_icon:
                countClicked++;
                if (countClicked < 2)
                showTooltipWater(view, Gravity.BOTTOM);
                break;
            case R.id.light_icon:
                countClicked++;
                if (countClicked < 2)
                    showTooltipLight(view, Gravity.BOTTOM);
                break;
            case R.id.temp_icon:
                countClicked++;
                if (countClicked < 2)
                    showTooltipTemp(view, Gravity.BOTTOM);
                break;
            default:
        }
    }

    private void showTooltipWater(View v, int gravity) {
        TextView tv = (TextView) v;
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
        final Tooltip tooltip = new Tooltip.Builder(tv)
                .setText("Water level " + getPlantData()[id][1] +  " is fine at the moment. " +
                        "You will need to water your" +
                        getIntent().getStringExtra("plantNameNoCapital") + " again in 1 day.")
                .setTypeface(myCustomFont2)
                .setTextSize(getResources().getDimension(R.dimen.plant_factors_text_size))
                .setTextColor(getResources().getColor(R.color.colorBlue))
                .setGravity(gravity)
                .setBackgroundColor(getResources().getColor(R.color.colorBlueOpacity))
                .setCornerRadius(10f)
                .setDismissOnClick(true)
                .show();

        tooltip.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                countClicked = 0;
            }
        });
    }

    private void showTooltipLight(View v, int gravity) {
        TextView tv = (TextView) v;
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
        final Tooltip tooltip = new Tooltip.Builder(tv)
                .setText("Your " + getIntent().getStringExtra("plantNameNoCapital") +  " plant is not receiving enough " +
                        "light right now." +
                        " Try to put it in a sunny place, or if not possible under a lamp.")
                .setTypeface(myCustomFont2)
                .setTextSize(getResources().getDimension(R.dimen.plant_factors_text_size))
                .setTextColor(getResources().getColor(R.color.colorYellow))
                .setGravity(gravity)
                .setBackgroundColor(getResources().getColor(R.color.colorYellowOpacity))
                .setCornerRadius(10f)
                .setDismissOnClick(true)
                .show();

        tooltip.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                countClicked = 0;
            }
        });
    }

    private void showTooltipTemp(View v, int gravity) {
        TextView tv = (TextView) v;
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
        final Tooltip tooltip = new Tooltip.Builder(tv)
                .setText("The current temperature" +
                        " around your " + getIntent().getStringExtra("plantNameNoCapital") + " is " + getPlantData()[id][3] +  ". " +
                        "Perfect!")
                .setTypeface(myCustomFont2)
                .setTextSize(getResources().getDimension(R.dimen.plant_factors_text_size))
                .setTextColor(getResources().getColor(R.color.colorRed))
                .setGravity(gravity)
                .setBackgroundColor(getResources().getColor(R.color.colorRedOpacity))
                .setCornerRadius(10f)
                .setDismissOnClick(true)
                .show();

        tooltip.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                countClicked = 0;
            }
        });
    }

    /*
    void startTimer(int id, int plantArray[][]) {
        final int totalSeconds = plantArray[id][0];
        counter = totalSeconds;

        progress = 0;

        /*Intent intent = new Intent(PlantActivity.this, BroadcastTimerService.class);
        intent.putExtra("HARVEST_TIME", totalSeconds);

            progressBar = findViewById(R.id.progressBar);

            mCounterTimer = new CountDownTimer(totalSeconds * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    counter--;
                    progress++;
                    textCounter.setText(String.valueOf("Days remaining: " + counter));
                    progressBar.setProgress(progress * 10000 / (totalSeconds * 100));
                }

                public void onFinish() {
                    counter--;
                    textCounter.setText(R.string.finish_harvest_time);
                    progressBar.setProgress(100);

                }
            };
            mCounterTimer.start();
    }*/

    void showValues(int id, int plantArray[][]) {
        int water = plantArray[id][1];
        int temperature = plantArray[id][2];
        int light = plantArray[id][3];

        showIcons(id, plantArray);
        showHealth(id, water, temperature, light);
        showIconValue(water, temperature, light);
    }

    String calculateHealth(int water, int temperature, int light) {

        int health = 0;
        String healthString;

        if (water < light && water < temperature) {
            health = water;
        }

        if (temperature < water && temperature < light) {
            health = temperature;
        }

        if (light < water && light < temperature) {
            health = light;
        }

        if (health < 20) {
            healthString = "Low";
        }
        if (health <= 50 && health >= 20) {
            healthString = "Medium";
        } else {
            healthString = "High";
        }

        return healthString;
    }

    void showHealth(int id, int water, int temperature, int light) {
        txtPlantHealth.setText(String.format("Overall Health: %s", calculateHealth(water, temperature, light)));
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
        txtPlantHealth.setTypeface(myCustomFont);
    }

    void showIcons(int id, int plantArray[][]) {
        int water = plantArray[id][1];

        if (water <= 50 && water > 33) {
            Toast.makeText(this, "I am a little thirsty", Toast.LENGTH_LONG).show();
        }

        if (water <= 33) {
            alertWater.setVisibility(View.VISIBLE);
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

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent); // or whatever method used to update your GUI fields
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(BroadcastTimerService.COUNTDOWN_BR));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
    }
    @Override
    public void onDestroy() {
        stopService(new Intent(this, BroadcastTimerService.class));
        super.onDestroy();
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);
            Toast.makeText(this,  "Countdown seconds remaining: " +  millisUntilFinished / 1000, Toast.LENGTH_LONG).show();
        }
    }
}
