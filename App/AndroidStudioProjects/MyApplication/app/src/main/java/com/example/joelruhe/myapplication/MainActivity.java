package com.example.joelruhe.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int i = 0;
    int y = 0;
    ImageView image;
    ImageView icondrop;
    TextView waterlevel;

    int water = 20;
    int soil = 70;
    int temperature = 21;

    //int plant1 = R.drawable.plant1;

    int [][] imageValue =  {
            {water, soil, temperature},
            {75, soil, temperature},
            {30, soil, temperature},
            {45, soil, temperature},
            {67, soil, temperature},
            {12, soil, temperature},
    };


    int [] images = new int[] {R.drawable.plant1, R.drawable.plant2, R.drawable.plants,
            R.drawable.cactus, R.drawable.potplant, R.drawable.longgrass};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //plant1 = 1;

        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);

        image = (ImageView)findViewById(R.id.plantSelect);
        waterlevel = (TextView)findViewById(R.id.waterlevel);
        icondrop = (ImageView)findViewById(R.id.iconDrop);

        waterlevel.setText(imageValue[y][0] + "%");
        //icondrop.getLayoutParams().height = imageValue[y][0];

        int height = icondrop.getHeight();


    }

    void next_plant(View v){
        y++;
        if(y > 5){
            y=0;
        }
        image.setImageResource(images[y]);
        waterlevel.setText(imageValue[y][0] +"%");
        //icondrop.getLayoutParams().height = imageValue[y][0];


    }

    void previous_plant(View v){
        y--;
        if(y < 0){
            y=5;
        }
        image.setImageResource(images[y]);
        waterlevel.setText(imageValue[y][0] +"%");
        //icondrop.getLayoutParams().height = imageValue[y][0];


    }
}


