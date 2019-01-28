package com.example.joelruhe.myapplication.authentication.firebase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.activities.MyNetworkActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FireBaseAccLoginActivity extends Activity {

    @BindView(R.id.edit_text_email)
    EditText emailEditText;
    @BindView(R.id.edit_text_password)
    EditText passwordEditText;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.forgot_password)
    TextView forgotPassword;

    @BindString(R.string.click_msg)
    String clickMsg;
    @BindString(R.string.permission_fail)
    String permissionFail;
    @BindString(R.string.enter_email)
    String enterEmail;
    @BindString(R.string.enter_password)
    String enterPassword;
    @BindString(R.string.minimum_password)
    String minPassword;
    @BindString(R.string.auth_failed)
    String authFailed;
    @BindString(R.string.users)
    String users;

    private FirebaseAuth auth;

    private final int MIN_PASSWORD_LENGTH = 6;

    Toolbar authCommunityToolbarLogin;
    ImageButton cancelIcon;
    TextView authCommunityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_login);
        ButterKnife.bind(this);

        // get fire base auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(FireBaseAccLoginActivity.this, MyNetworkActivity.class));
            finish();
        }

        // set the cancel icon function
        authCommunityToolbarLogin = findViewById(R.id.authCommunityToolbarLogin);
        cancelIcon = authCommunityToolbarLogin.findViewById(R.id.btn_cancel2);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");

        authCommunityTitle = authCommunityToolbarLogin.findViewById(R.id.tv_my_network);
        authCommunityTitle.setTypeface(myCustomFont);

        btnLogin.setTypeface(myCustomFont2);
        emailEditText.setTypeface(myCustomFont2);
        passwordEditText.setTypeface(myCustomFont2);
        forgotPassword.setTypeface(myCustomFont);

    }

    // set the right button to the activity
    @OnClick({R.id.btn_login, R.id.forgot_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loginClick();
                break;
            case R.id.forgot_password:
                startActivity(new Intent(FireBaseAccLoginActivity.this, FireBaseForgotPasswordActivity.class));
                break;
            default:
                Toast.makeText(this, clickMsg, Toast.LENGTH_SHORT).show();
        }
    }

    // get the email and password per user
    private void loginClick() {
        String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        if (isLoginFormValid(email, password)) {
            authUser(email, password);
        }
    }

    // check if the login is valid
    private boolean isLoginFormValid(final String pEmail, final String pPassword) {
        if (TextUtils.isEmpty(pEmail)) {
            emailEditText.setError(enterEmail);
            return false;
        }

        if (TextUtils.isEmpty(pPassword)) {
            passwordEditText.setError(enterPassword);
            return false;
        }

        return true;
    }

    private void authUser(final String pEmail, final String pPassword) {
        // authenticate user
        auth.signInWithEmailAndPassword(pEmail, pPassword)
                .addOnCompleteListener(FireBaseAccLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (pPassword.length() < MIN_PASSWORD_LENGTH) {
                                passwordEditText.setError(minPassword);
                            } else {
                                Toast.makeText(FireBaseAccLoginActivity.this, authFailed, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(FireBaseAccLoginActivity.this, MyNetworkActivity.class);
                            intent.putExtra("Email", emailEditText.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    // replace the characters to let fire base save it
    private String getFirebaseEmail(String email) {
        email = email.replace("@", "-");
        email = email.replace(".", "_");
        return email;
    }
}
