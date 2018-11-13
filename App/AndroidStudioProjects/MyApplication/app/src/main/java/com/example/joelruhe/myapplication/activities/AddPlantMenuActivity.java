package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.activities.SidebarActivity.NavigationMenu;

public class AddPlantMenuActivity extends AppCompatActivity {

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_menu);

        Button button = (Button)findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your code in here!
                i = 1;
                Intent intent = new Intent(AddPlantMenuActivity.this,MainActivity.class);
                intent.putExtra("number", i);
                startActivity(intent);
            }
        });    }
}
