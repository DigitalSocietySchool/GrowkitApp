package com.example.joelruhe.myapplication.activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    @BindView(R.id.imageDrop)
    ImageView drop;

    @BindView(R.id.textView3)
    TextView tv;
    @BindView(R.id.textViewId)
    TextView plantid;
    @BindView(R.id.textWaterValue)
    TextView waterValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        ButterKnife.bind(PlantActivity.this);


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
        tv.setText(getIntent().getStringExtra("DESCRIPTION"));
        id = getIntent().getIntExtra("ID", 0);
        idString = Integer.toString(getIntent().getIntExtra("ID", 0));
        plantid.setText(idString);

        showIcons(id, getPlantData());
        showValues(id, getPlantData());
    }

    void showIcons(int id, int plantarray[][]) {
        int [] dropIcons = new int[] {R.drawable.drop, R.drawable.drop1, R.drawable.drop2};

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
    }

    void showValues(int id, int plantarray[][]){
        int water = plantarray[id][0];
        waterValue.setText(water + "%");

    }

    int [][] getPlantData(){
        //These values will be pulled from the database!
        int plantarray[][] = {
                {44,60,78},
                {98,88,92},
                {5,22,11},
                {66,28,55},
                {44,7,29}
        };

        return plantarray;
    }
}