package com.example.joelruhe.myapplication.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joelruhe.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FireBaseLoginActivity extends Activity {

    @BindView(R.id.email)
    EditText emailEditText;
    @BindView(R.id.password)
    EditText passwordEditText;
    @BindView(R.id.backBtn)
    Button backBtn;

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

    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_login);
        ButterKnife.bind(this);

        //Database reference pointing to root of database
        rootRef = FirebaseDatabase.getInstance().getReference();

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(FireBaseLoginActivity.this, MainActivity.class));
            finish();
        }

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
    }

    @OnClick(R.id.backBtn)
    public void goBack(View v) {
        finish();
    }

    @OnClick({R.id.logoImageView, R.id.loginBtn, R.id.resetPasswordBtn, R.id.signupBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                loginClick();
                break;
            case R.id.resetPasswordBtn:
                startActivity(new Intent(FireBaseLoginActivity.this, FireBaseResetPasswordActivity.class));
                break;
            case R.id.signupBtn:
                startActivity(new Intent(FireBaseLoginActivity.this, FireBaseRegisterActivity.class));
                break;
            default:
                Toast.makeText(this, clickMsg, Toast.LENGTH_SHORT).show();
        }
    }

    private void loginClick() {
        String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        if (isLoginFormValid(email, password)) {
            authUser(email, password);
        }
    }

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

    /**
     * Firebase email password login method
     *
     * @param pEmail
     * @param pPassword
     */
    private void authUser(final String pEmail, final String pPassword) {
        //authenticate user
        auth.signInWithEmailAndPassword(pEmail, pPassword)
                .addOnCompleteListener(FireBaseLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (pPassword.length() < MIN_PASSWORD_LENGTH) {
                                passwordEditText.setError(minPassword);
                            } else {
                                Toast.makeText(FireBaseLoginActivity.this, authFailed, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(FireBaseLoginActivity.this, MainActivity.class);
                            intent.putExtra("Email", emailEditText.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    private String getFirebaseEmail(String email) {
        email = email.replace("@", "-");
        email = email.replace(".", "_");
        return email;
    }
}
