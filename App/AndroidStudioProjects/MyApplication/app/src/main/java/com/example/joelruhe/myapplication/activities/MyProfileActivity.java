package com.example.joelruhe.myapplication.activities;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.joelruhe.myapplication.CustomViewPager;
import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.PagerAdapter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyProfileActivity extends AppCompatActivity {

    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.user_description)
    TextView userDescription;
    @BindView(R.id.experience)
    TextView experience;
    @BindView(R.id.text_toolbar)
    TextView txtPlantDataToolbar;

    Toolbar plantToolbar;
    ImageButton cancelIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(MyProfileActivity.this);

        TabLayout tabLayout = findViewById(R.id.id_tabs);
        final CustomViewPager viewPager = findViewById(R.id.viewPagerMyProfile);

        // add the right tabs to the tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("My Garden"));
        tabLayout.addTab(tabLayout.newTab().setText("My Milestones"));

        final Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        final Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");

        // ste the user and user description
        username.setText("Jane Doodle");
        userDescription.setText("Balcony gardener, volunteer at community garden Anna's Tuin");
        experience.setText("Experience: 4 months");
        username.setTypeface(myCustomFont);
        userDescription.setTypeface(myCustomFont2);
        experience.setTypeface(myCustomFont2);

        // set text to toolbar and the right font
        txtPlantDataToolbar.setText("My Garden");
        txtPlantDataToolbar.setTypeface(myCustomFont);

        plantToolbar = findViewById(R.id.profileToolbar);
        cancelIcon = plantToolbar.findViewById(R.id.btn_cancel);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // get the tabcount
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab,null);
            tv.setTypeface(myCustomFont);
            Objects.requireNonNull(tabLayout.getTabAt(i)).setCustomView(tv);
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // disable sliding tabs
        viewPager.setPagingEnabled(false);

        // get the pager adapter to set the right fragments under the tabLayout
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
