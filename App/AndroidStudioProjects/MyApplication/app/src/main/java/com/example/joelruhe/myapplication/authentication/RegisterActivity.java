package com.example.joelruhe.myapplication.authentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.activities.DatabaseActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText firstnameET, middlenameET, lastnameET, emailET, passwordET, confpasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstnameET = (EditText)findViewById(R.id.firstname);
        middlenameET = (EditText)findViewById(R.id.middlename);
        lastnameET = (EditText)findViewById(R.id.lastname);
        emailET = (EditText)findViewById(R.id.email);
        passwordET = (EditText)findViewById(R.id.password);
        confpasswordET = (EditText)findViewById(R.id.confpassword);
    }

    public void onclickRegister(View view){

        String firstname = firstnameET.getText().toString();
        String middlename = middlenameET.getText().toString();
        String lastname = lastnameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String confpassword = confpasswordET.getText().toString();
        String type = "register";

        if(validate()) {
            DatabaseActivity databaseActivity = new DatabaseActivity(this);
            databaseActivity.execute(type, firstname, middlename, lastname, email, password, confpassword);
        }
    }


    private boolean validate() {
        boolean temp = true;
        String firstname = firstnameET.getText().toString();
        String lastname = lastnameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String confpassword = confpasswordET.getText().toString();

        if(firstname.matches("")){
            Toast.makeText(RegisterActivity.this,"First name is required",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(lastname.matches("")){
            Toast.makeText(RegisterActivity.this,"Last name is required",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(email.matches("")){
            Toast.makeText(RegisterActivity.this,"Email is required",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(password.matches("")){
            Toast.makeText(RegisterActivity.this,"Password is required",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(confpassword.matches("")){
            Toast.makeText(RegisterActivity.this,"Password confirmation is required",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(!password.equals(confpassword)){
            Toast.makeText(RegisterActivity.this,"Passwords are not matching",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(!isEmailValid(email)){
            Toast.makeText(RegisterActivity.this,"Email address is not valid",Toast.LENGTH_SHORT).show();
            temp=false;
        }

        return temp;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
