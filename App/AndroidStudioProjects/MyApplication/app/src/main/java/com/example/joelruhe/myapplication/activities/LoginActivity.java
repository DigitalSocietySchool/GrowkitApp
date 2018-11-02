package com.example.joelruhe.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.joelruhe.myapplication.R;

public class LoginActivity extends AppCompatActivity {
    EditText UsernameEt, PasswordEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameEt = (EditText)findViewById(R.id.textUsername);
        PasswordEt = (EditText)findViewById(R.id.textPassword);
    }

    public void OnLogin(View view){
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";
        //DatabaseActivity databaseActivity = new DatabaseActivity(this);
       // databaseActivity.execute(type, username, password);
    }
}
