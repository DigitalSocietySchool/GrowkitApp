package com.example.joelruhe.myapplication.activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.joelruhe.myapplication.R;

import butterknife.BindView;

public class AddFriendsActivity extends AppCompatActivity {

    TextView txtPlant;

    Toolbar plantToolbar;
    ImageButton cancelIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);


        final Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        final Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");

        plantToolbar = findViewById(R.id.addFriendsToolbar);

        txtPlant = findViewById(R.id.text_toolbar_add_friends);
        txtPlant.setText("Add Friends");
        txtPlant.setTypeface(myCustomFont);

        cancelIcon = plantToolbar.findViewById(R.id.btn_cancel);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
