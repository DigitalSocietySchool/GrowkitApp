package com.example.joelruhe.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.joelruhe.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class FirebaseActivity extends AppCompatActivity {

    private Button mFirebaseBtn;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        mFirebaseBtn = findViewById(R.id.firebase_btn);
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }
}
