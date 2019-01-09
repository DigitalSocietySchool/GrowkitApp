package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.joelruhe.myapplication.R;

public class MyNetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_network);
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(MyNetworkActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
