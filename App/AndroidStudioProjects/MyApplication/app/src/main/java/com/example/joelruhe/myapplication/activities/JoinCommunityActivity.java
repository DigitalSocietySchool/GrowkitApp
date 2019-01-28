package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.authentication.firebase.FireBaseAccLoginActivity;
import com.example.joelruhe.myapplication.authentication.firebase.FireBaseSignUpActivity;
import com.google.firebase.auth.FirebaseAuth;


public class JoinCommunityActivity extends AppCompatActivity{

    ImageButton cancelIcon;
    Toolbar joinCommunityToolbar;
    Button btnSignUp;
    Button btnLogin;

    TextView joinCommunityTitle;
    TextView joinCommunityHeader;
    TextView joinCommunitySubtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_join);

        joinCommunityToolbar = findViewById(R.id.joinCommunityToolbar);
        cancelIcon = joinCommunityToolbar.findViewById(R.id.btn_cancel);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Get Firebase auth instance
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(JoinCommunityActivity.this, MyNetworkActivity.class));
            finish();
        }

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");

        joinCommunityTitle = joinCommunityToolbar.findViewById(R.id.tv_my_network);
        joinCommunityTitle.setTypeface(myCustomFont);

        joinCommunityHeader = findViewById(R.id.tv_join_community_header);
        joinCommunitySubtext = findViewById(R.id.tv_join_community_subtext);

        joinCommunityHeader.setTypeface(myCustomFont);
        joinCommunitySubtext.setTypeface(myCustomFont2);

        btnSignUp = findViewById(R.id.btn_sign_up);
        btnLogin = findViewById(R.id.btn_login);

        // set the custom font to the right text
        btnLogin.setTypeface(myCustomFont2);
        btnSignUp.setTypeface(myCustomFont2);

        // set button for signing up
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JoinCommunityActivity.this, FireBaseSignUpActivity.class));
            }
        });

        // set button fo logging in
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JoinCommunityActivity.this, FireBaseAccLoginActivity.class));
            }
        });
    }
}
