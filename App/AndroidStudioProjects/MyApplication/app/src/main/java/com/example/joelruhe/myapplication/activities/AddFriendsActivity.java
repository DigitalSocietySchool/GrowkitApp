package com.example.joelruhe.myapplication.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.joelruhe.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddFriendsActivity extends AppCompatActivity {

    TextView txtPlant;
    Toolbar plantToolbar;
    ImageButton cancelIcon;

    @BindView(R.id.id1)
    ConstraintLayout f1;
    @BindView(R.id.id2)
    ConstraintLayout f2;
    @BindView(R.id.id3)
    ConstraintLayout f3;
    @BindView(R.id.id4)
    ConstraintLayout f4;
    @BindView(R.id.id5)
    ConstraintLayout f5;

    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;

    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.textView12)
    TextView textView12;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        ButterKnife.bind(AddFriendsActivity.this);

        plantToolbar = findViewById(R.id.addFriendsToolbar);

        final Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        final Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");

        txtPlant = plantToolbar.findViewById(R.id.text_toolbar);
        txtPlant.setText("Add New Friend");
        txtPlant.setTypeface(myCustomFont);

        tv1.setTypeface(myCustomFont2);
        tv2.setTypeface(myCustomFont2);
        tv3.setTypeface(myCustomFont2);
        tv4.setTypeface(myCustomFont2);
        tv5.setTypeface(myCustomFont2);

        textView8.setTypeface(myCustomFont);
        textView9.setTypeface(myCustomFont);
        textView10.setTypeface(myCustomFont);
        textView11.setTypeface(myCustomFont);
        textView12.setTypeface(myCustomFont);

        cancelIcon = plantToolbar.findViewById(R.id.btn_cancel);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // onClickListener that changes the activity started based on the id
    @OnClick({R.id.id1, R.id.id2, R.id.id3, R.id.id4, R.id.id5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id1:
                startActivity(new Intent(AddFriendsActivity.this, AddFriendActivity.class));
                break;
            case R.id.id2:
                startActivity(new Intent(AddFriendsActivity.this, AddFriendActivity.class));
                break;
            case R.id.id3:
                startActivity(new Intent(AddFriendsActivity.this, AddFriendActivity.class));
                break;
            case R.id.id4:
                startActivity(new Intent(AddFriendsActivity.this, AddFriendActivity.class));
                break;
            case R.id.id5:
                startActivity(new Intent(AddFriendsActivity.this, AddFriendActivity.class));
                break;
            default:
        }
    }
}
