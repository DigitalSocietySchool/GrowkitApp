package com.example.joelruhe.myapplication.activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.joelruhe.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFriendActivity extends AppCompatActivity {

    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.experience)
    TextView userDescription;
    @BindView(R.id.text_friend_add)
    TextView textFriendAdd;

    Toolbar addFriendToolbar;
    ImageButton cancelIcon;
    TextView textToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(AddFriendActivity.this);

        username.setText("Yasmin Gail");
        userDescription.setText("Experience: 1 year");
        textFriendAdd.setText("You both live in the same area, and grow 4 common plants.");

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");

        username.setTypeface(myCustomFont);
        userDescription.setTypeface(myCustomFont2);
        textFriendAdd.setTypeface(myCustomFont2);

        addFriendToolbar = findViewById(R.id.addFriendToolbar);
        textToolbar = addFriendToolbar.findViewById(R.id.text_toolbar);
        textToolbar.setText("Add New Friend");
        textToolbar.setTypeface(myCustomFont);
        cancelIcon = addFriendToolbar.findViewById(R.id.btn_cancel);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
