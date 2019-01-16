package com.example.joelruhe.myapplication.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.joelruhe.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tooltip.OnDismissListener;
import com.tooltip.Tooltip;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlantActivity extends AppCompatActivity {

    @BindView(R.id.water_alert)
    ImageView alertWater;
    @BindView(R.id.light_alert)
    ImageView alertLight;
    @BindView(R.id.temp_alert)
    ImageView alertTemp;
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
    @BindView(R.id.imageViewPlant)
    ImageView imgPlant;

    ProgressBar progressBar;
    Toolbar plantToolbar;
    ImageButton cancelIcon;

    int id;
    int countClicked;

    int waterValue;
    int lightValue;
    int temperatureValue;
    int durationValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        ButterKnife.bind(PlantActivity.this);

        imgBtnResetHarvest.setVisibility(View.GONE);

        progressBar = findViewById(R.id.progressBar);

        txtPlantData.setText(getIntent().getStringExtra("DESCRIPTION"));
        txtPlantDataToolbar.setText(getIntent().getStringExtra("DESCRIPTION"));
        final Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        txtPlantData.setTypeface(myCustomFont);
        txtPlantDataToolbar.setTypeface(myCustomFont);

        plantToolbar = findViewById(R.id.plantHealthToolbar);
        cancelIcon = plantToolbar.findViewById(R.id.btn_cancel);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int resId = bundle.getInt("plantImage");
            imgPlant.setImageResource(resId);
        }

        id = getIntent().getIntExtra("ID", 0);
        getPlantData(id);

        imgBtnStartHarvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(PlantActivity.this, BroadcastTimerService.class));
                imgBtnResetHarvest.setVisibility(View.VISIBLE);
                imgBtnStartHarvest.setVisibility(View.GONE);
            }
        });

        imgBtnResetHarvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(PlantActivity.this, BroadcastTimerService.class));
                imgBtnResetHarvest.setVisibility(View.GONE);
                imgBtnStartHarvest.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick({R.id.water_icon, R.id.light_icon, R.id.temp_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.water_icon:
                countClicked++;
                if (countClicked < 2)
                    showTooltipWater(view, Gravity.BOTTOM, id, getPlantData(id));
                break;
            case R.id.light_icon:
                countClicked++;
                if (countClicked < 2)
                    showTooltipLight(view, Gravity.BOTTOM, id, getPlantData(id));
                break;
            case R.id.temp_icon:
                countClicked++;
                if (countClicked < 2)
                    showTooltipTemp(view, Gravity.BOTTOM, id, getPlantData(id));
                break;
            default:
        }
    }

    private void showTooltipWater(View v, int gravity, int id, int plantArray[]) {
        TextView tv = (TextView) v;
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");

        int water = plantArray[1];

        if (water > 50) {
            final Tooltip tooltip = new Tooltip.Builder(tv)
                    .setText("The water level of "+ getIntent().getStringExtra("plantNameNoCapital") +" is" +
                            " " + getPlantData(id)[1] + "%. This is a healthy percentage for your plant!")
                    .setTypeface(myCustomFont2)
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

        if (water > 33 && water < 50) {
            final Tooltip tooltip = new Tooltip.Builder(tv)
                    .setText("The water level is " + getPlantData(id)[1] + "% which is still high enough" +
                            "You will need to water your" +
                            getIntent().getStringExtra("plantNameNoCapital") + " again in about 1 day.")
                    .setTypeface(myCustomFont2)
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

        if (water <= 33) {
            final Tooltip tooltip = new Tooltip.Builder(tv)
                    .setText("The water level of your "+ getIntent().getStringExtra("plantNameNoCapital") +" is" +
                            " " + getPlantData(id)[1] +  "%  at the moment, which is not healthy for your plant. " +
                            "You will need to water your "+ getIntent().getStringExtra("plantNameNoCapital") +" again as soon as possible!")
                    .setTypeface(myCustomFont2)
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
    }

    private void showTooltipLight(View v, int gravity, int id, int plantArray[]) {
        TextView tv = (TextView) v;
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");

        int light = plantArray[2];

        if (light >= 60 ) {
            final Tooltip tooltip = new Tooltip.Builder(tv)
                    .setText("Your " + getIntent().getStringExtra("plantNameNoCapital") +  " plant it's " +
                            "light level is " + getPlantData(id)[2] +  "%. This is healthy for the plant, keep it up!")
                    .setTypeface(myCustomFont2)
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

        if (light > 33 && light < 60 ) {
            final Tooltip tooltip = new Tooltip.Builder(tv)
                    .setText("Your " + getIntent().getStringExtra("plantNameNoCapital") +  " plant it's " +
                            "light level is " + getPlantData(id)[2] +  "%. The plant will survive, but it is not the optimal condition.")
                    .setTypeface(myCustomFont2)
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

        if (light <= 33) {
            final Tooltip tooltip = new Tooltip.Builder(tv)
                    .setText("Your " + getIntent().getStringExtra("plantNameNoCapital") +  " is " +
                            "receiving " + getPlantData(id)[2] +  "% light right now. This not enough!" +
                            " Try to put it in a sunny place, or if not possible under a lamp.")
                    .setTypeface(myCustomFont2)
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
    }

    private void showTooltipTemp(View v, int gravity, int id, int plantArray[]) {
        TextView tv = (TextView) v;
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
        int temp = plantArray[3];

        if (temp > 18) {
            final Tooltip tooltip = new Tooltip.Builder(tv)
                    .setText("The current temperature" +
                            " around your " + getIntent().getStringExtra("plantNameNoCapital") + " is " + getPlantData(id)[3] + ". " +
                            "Perfect!")
                    .setTypeface(myCustomFont2)
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

        if (temp <= 18 ) {
            final Tooltip tooltip = new Tooltip.Builder(tv)
                    .setText("The current temperature" +
                            " around your " + getIntent().getStringExtra("plantNameNoCapital") + " is " + getPlantData(id)[3] + " degrees Celsius, which is not good. " +
                            "Try to get the efficient temperature for your plant.")
                    .setTypeface(myCustomFont2)
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
    }

    int[] getPlantData(int id) {

        int plantID = id + 1;

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference plantValues = mDatabase.child("Pins").child("4444").child("Plants").child("Stick"+plantID);

        plantValues.child("Water").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int water = Integer.valueOf(snapshot.getValue().toString());
                waterValue = water;
                if (water <= 33) {
                    alertWater.setVisibility(View.VISIBLE);
                } else {
                    alertWater.setVisibility(View.GONE);
                }
                if (alertWater.getVisibility() == View.GONE &&
                        alertLight.getVisibility() == View.GONE &&
                        alertTemp.getVisibility() == View.GONE ) {
                    txtPlantHealth.setText(R.string.health_high);
                    Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
                    txtPlantHealth.setTypeface(myCustomFont);
                }

                if ((alertWater.getVisibility() == View.VISIBLE) ||
                        (alertLight.getVisibility() == View.VISIBLE) ||
                        (alertTemp.getVisibility() == View.VISIBLE)) {
                    txtPlantHealth.setText(R.string.health_medium);
                    Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
                    txtPlantHealth.setTypeface(myCustomFont);
                }

                if ((alertWater.getVisibility() == View.VISIBLE &&
                        alertLight.getVisibility() == View.VISIBLE) ||
                        (alertTemp.getVisibility() == View.VISIBLE &&
                                alertLight.getVisibility() == View.VISIBLE)
                        || (alertWater.getVisibility() == View.VISIBLE && alertTemp.getVisibility() == View.VISIBLE)) {
                    txtPlantHealth.setText(R.string.health_low);
                    Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
                    txtPlantHealth.setTypeface(myCustomFont);
                }


                if (alertWater.getVisibility() == View.VISIBLE &&
                        alertLight.getVisibility() == View.VISIBLE &&
                        alertTemp.getVisibility() == View.VISIBLE) {
                    txtPlantHealth.setText(R.string.health_low);
                    Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
                    txtPlantHealth.setTypeface(myCustomFont);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        plantValues.child("Light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int light = Integer.valueOf(snapshot.getValue().toString());
                lightValue = light;
                if (light <= 33 ) {
                    alertLight.setVisibility(View.VISIBLE);
                }
                else{
                    alertLight.setVisibility(View.GONE);
                }
                if (alertWater.getVisibility() == View.GONE &&
                        alertLight.getVisibility() == View.GONE &&
                        alertTemp.getVisibility() == View.GONE ) {
                    txtPlantHealth.setText(R.string.health_high);
                    Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
                    txtPlantHealth.setTypeface(myCustomFont);
                }

                if ((alertWater.getVisibility() == View.VISIBLE) ||
                        (alertLight.getVisibility() == View.VISIBLE) ||
                        (alertTemp.getVisibility() == View.VISIBLE)) {
                    txtPlantHealth.setText(R.string.health_medium);
                    Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
                    txtPlantHealth.setTypeface(myCustomFont);
                }

                if ((alertWater.getVisibility() == View.VISIBLE &&
                        alertLight.getVisibility() == View.VISIBLE) ||
                        (alertTemp.getVisibility() == View.VISIBLE &&
                                alertLight.getVisibility() == View.VISIBLE)
                        || (alertWater.getVisibility() == View.VISIBLE && alertTemp.getVisibility() == View.VISIBLE)) {
                    txtPlantHealth.setText(R.string.health_low);
                    Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
                    txtPlantHealth.setTypeface(myCustomFont);
                }


                if (alertWater.getVisibility() == View.VISIBLE &&
                        alertLight.getVisibility() == View.VISIBLE &&
                        alertTemp.getVisibility() == View.VISIBLE) {
                    txtPlantHealth.setText(R.string.health_low);
                    Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
                    txtPlantHealth.setTypeface(myCustomFont);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        plantValues.child("Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int temperature = Integer.valueOf(snapshot.getValue().toString());
                temperatureValue = temperature;
                if (temperature <= 18 ) {
                    alertTemp.setVisibility(View.VISIBLE);
                }
                else{
                    alertTemp.setVisibility(View.GONE);
                }
                if (alertWater.getVisibility() == View.GONE &&
                        alertLight.getVisibility() == View.GONE &&
                        alertTemp.getVisibility() == View.GONE ) {
                    txtPlantHealth.setText(R.string.health_high);
                    Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
                    txtPlantHealth.setTypeface(myCustomFont);
                }

                if ((alertWater.getVisibility() == View.VISIBLE) ||
                        (alertLight.getVisibility() == View.VISIBLE) ||
                        (alertTemp.getVisibility() == View.VISIBLE)) {
                    txtPlantHealth.setText(R.string.health_medium);
                    Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
                    txtPlantHealth.setTypeface(myCustomFont);
                }

                if ((alertWater.getVisibility() == View.VISIBLE &&
                        alertLight.getVisibility() == View.VISIBLE) ||
                        (alertTemp.getVisibility() == View.VISIBLE &&
                                alertLight.getVisibility() == View.VISIBLE)
                        || (alertWater.getVisibility() == View.VISIBLE && alertTemp.getVisibility() == View.VISIBLE)) {
                    txtPlantHealth.setText(R.string.health_low);
                    Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
                    txtPlantHealth.setTypeface(myCustomFont);
                }


                if (alertWater.getVisibility() == View.VISIBLE &&
                        alertLight.getVisibility() == View.VISIBLE &&
                        alertTemp.getVisibility() == View.VISIBLE) {
                    txtPlantHealth.setText(R.string.health_low);
                    Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
                    txtPlantHealth.setTypeface(myCustomFont);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        plantValues.child("Duration").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int duration = Integer.valueOf(snapshot.getValue().toString());
                durationValue = duration;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        int plantArray[][] = {
                {durationValue, waterValue, lightValue, temperatureValue}
        };

        return plantArray[0];
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
        super.onDestroy();
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            int counter = intent.getIntExtra("countdown", 0);
            Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
            txtPlantHarvestTimeLeft.setText(counter + " days until harvest!");
            txtPlantHarvestTimeLeft.setTypeface(myCustomFont);
        }
    }
}
