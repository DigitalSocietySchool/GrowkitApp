package com.example.joelruhe.myapplication.authentication.firebase;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.activities.FirebaseClass;
import com.example.joelruhe.myapplication.activities.MyNetworkActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FireBaseSignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @BindView(R.id.edit_text_email)
    EditText inputEmailEditText;
    @BindView(R.id.edit_text_password)
    EditText inputPasswordEditText;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;

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
    @BindString(R.string.enter_email)
    String enterEmail;
    @BindString(R.string.enter_password)
    String enterPassword;

    Toolbar authCommunityToolbar;
    ImageButton cancelIcon;
    TextView authCommunityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_sign_up);

        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();

        authCommunityToolbar = findViewById(R.id.authCommunityToolbar);
        cancelIcon = authCommunityToolbar.findViewById(R.id.btn_cancel);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");

        authCommunityTitle = authCommunityToolbar.findViewById(R.id.tv_my_network);
        authCommunityTitle.setTypeface(myCustomFont);
        btnSignUp.setTypeface(myCustomFont2);
        inputEmailEditText.setTypeface(myCustomFont2);
        inputPasswordEditText.setTypeface(myCustomFont2);

    }

    @OnClick(R.id.btn_sign_up)
    public void btnSignUp(View v) {
        final String email = inputEmailEditText.getText().toString().trim();
        final String password = inputPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            inputEmailEditText.setError(enterEmail);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPasswordEditText.setError(enterPassword);
            return;
        }

        if (password.length() < 6) {
            inputPasswordEditText.setError(minPassword);
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(FireBaseSignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(FireBaseSignUpActivity.this, regiSuccessful + task.isSuccessful(),
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(FireBaseSignUpActivity.this, regiUnsuccessful + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                //Insert new user in real time database
                                FirebaseClass firebaseClass = new FirebaseClass();
                                firebaseClass.insertNewUser(email, password);

                                startActivity(new Intent(FireBaseSignUpActivity.this, MyNetworkActivity.class));
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
