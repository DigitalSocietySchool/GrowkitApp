package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.joelruhe.myapplication.R;

public class EnterPinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);

        Button nextButton = (Button)findViewById(R.id.buttonNext);

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(EnterPinActivity.this,MainActivity.class));
            }
        });
    }
}
