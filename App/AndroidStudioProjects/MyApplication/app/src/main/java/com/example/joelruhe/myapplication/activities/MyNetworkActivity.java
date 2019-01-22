package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.fragments.MyFriendsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyNetworkActivity extends AppCompatActivity {

    @BindView(R.id.text_toolbar)
    TextView txtMyNetworkToolbar;
    @BindView(R.id.profile)
    TextView profile;
    @BindView(R.id.friends)
    TextView friends;
    @BindView(R.id.forum)
    TextView forum;
    @BindView(R.id.textViewProfile)
    TextView tvProfile;
    @BindView(R.id.textViewFriends)
    TextView tvFriends;
    @BindView(R.id.textViewForum)
    TextView tvForum;

    Toolbar myNetworkToolbar;
    ImageButton cancelIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_network);
        ButterKnife.bind(MyNetworkActivity.this);

        txtMyNetworkToolbar.setText("My Network");
        final Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        final Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
        txtMyNetworkToolbar.setTypeface(myCustomFont);

        tvProfile.setTypeface(myCustomFont2);
        tvFriends.setTypeface(myCustomFont2);
        tvForum.setTypeface(myCustomFont2);

        myNetworkToolbar = findViewById(R.id.myNetworkToolbar);
        cancelIcon = myNetworkToolbar.findViewById(R.id.btn_cancel);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(MyNetworkActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.profile)
    public void setProfile() {
        Intent i= new Intent(MyNetworkActivity.this, MyProfileActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.forum)
    public void setForum() {

    }

    @OnClick(R.id.friends)
    public void setFriends() {
        Intent i= new Intent(MyNetworkActivity.this, FriendsActivity.class);
        startActivity(i);
    }
}
