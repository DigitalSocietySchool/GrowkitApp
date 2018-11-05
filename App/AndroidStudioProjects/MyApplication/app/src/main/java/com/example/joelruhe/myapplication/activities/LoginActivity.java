package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joelruhe.myapplication.R;

import static java.util.Calendar.getInstance;

public class LoginActivity extends AppCompatActivity {
    EditText UsernameEt, PasswordEt;
    TextView Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameEt = (EditText)findViewById(R.id.textUsername);
        PasswordEt = (EditText)findViewById(R.id.textPassword);
        Register = (TextView)findViewById((R.id.register));

        String linkText = "Don't have an account yet?\n Register here now!";

        SpannableString ss = new SpannableString(linkText);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        };

        ss.setSpan(clickableSpan, 36, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Register.setText(ss);
        Register.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void OnLogin(View view){
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";
        DatabaseActivity databaseActivity = new DatabaseActivity(this);
        databaseActivity.execute(type, username, password);
    }
}
