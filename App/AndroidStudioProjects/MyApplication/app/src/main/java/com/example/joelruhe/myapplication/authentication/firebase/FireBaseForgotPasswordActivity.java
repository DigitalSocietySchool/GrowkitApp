package com.example.joelruhe.myapplication.authentication.firebase;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FireBaseForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_email)
    EditText inputEmailEditText;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btn_reset_password)
    Button resetPasswordBtn;
    @BindView(R.id.forgot_password_header)
    TextView forgotPaswordHeader;
    @BindView(R.id.forgot_password_subtext)
    TextView forgotPaswordSubtext;

    @BindString(R.string.enter_registered_email)
    String enterEmail;

    private FirebaseAuth auth;

    Toolbar authCommunityToolbar;
    ImageButton cancelIcon;
    TextView authCommunityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_forgot_password);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();

        authCommunityToolbar = findViewById(R.id.authCommunityToolbarPassword);
        cancelIcon = authCommunityToolbar.findViewById(R.id.btn_cancel3);
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

        forgotPaswordHeader.setTypeface(myCustomFont);
        forgotPaswordSubtext.setTypeface(myCustomFont2);

        inputEmailEditText.setTypeface(myCustomFont2);
        resetPasswordBtn.setTypeface(myCustomFont2);
    }

    @OnClick(R.id.btn_reset_password)
    public void resetPassword() {
        String email = inputEmailEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            inputEmailEditText.setError(enterEmail);
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FireBaseForgotPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FireBaseForgotPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}