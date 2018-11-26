package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.joelruhe.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FireBaseRegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference rootRef, demoRef;

    @BindView(R.id.inputEmailEditText)
    EditText inputEmailEditText;
    @BindView(R.id.inputPasswordEditText)
    EditText inputPasswordEditText;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.signInBtn)
    Button signInBtn;
    @BindView(R.id.signUpBtn)
    Button signUpBtn;
    @BindView(R.id.resetPasswordBtn)
    Button resetPasswordBtn;

    @BindString(R.string.error_empty_email_field)
    String emptyEmail;
    @BindString(R.string.error_empty_password_field)
    String emptyPassword;
    @BindString(R.string.minimum_password)
    String minPassword;
    @BindString(R.string.registration_successful)
    String regiSuccessful;
    @BindString(R.string.registration_unsuccessful)
    String regiUnsuccessful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base_register);

        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.resetPasswordBtn)
    public void resetPassword(View v) {
        startActivity(new Intent(FireBaseRegisterActivity.this, FireBaseResetPasswordActivity.class));
    }

    @OnClick(R.id.signInBtn)
    public void signIn(View v) {
        finish();
    }

    @OnClick(R.id.signUpBtn)
    public void signUp(View v) {
        final String email = inputEmailEditText.getText().toString().trim();
        final String password = inputPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), emptyEmail, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), emptyPassword, Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), minPassword, Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(FireBaseRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(FireBaseRegisterActivity.this, regiSuccessful + task.isSuccessful(),
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(FireBaseRegisterActivity.this, regiUnsuccessful + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                //Insert new user in realtime database
                                FirebaseClass firebaseClass = new FirebaseClass();
                                firebaseClass.insertNewUser(email, password);

                                startActivity(new Intent(FireBaseRegisterActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    });
        }

        @Override
        protected void onResume() {
            super.onResume();
            progressBar.setVisibility(View.GONE);
        }
}
