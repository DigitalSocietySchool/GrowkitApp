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
import com.example.joelruhe.myapplication.PagerAdapter;
import com.example.joelruhe.myapplication.PagerAdapter3;
import com.example.joelruhe.myapplication.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFriendActivity extends AppCompatActivity {

    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.experience)
    TextView experience;
    @BindView(R.id.text_toolbar)
    TextView txtPlantDataToolbar;

    Toolbar plantToolbar;
    ImageButton cancelIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend);

        ButterKnife.bind(MyFriendActivity.this);

        TabLayout tabLayout = findViewById(R.id.id_tabs);
        final CustomViewPager viewPager = findViewById(R.id.viewPagerMyProfile);

        tabLayout.addTab(tabLayout.newTab().setText("Garden"));
        tabLayout.addTab(tabLayout.newTab().setText("Milestones"));

        final Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        final Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");

        username.setText("David Shzu");
        experience.setText("Experience: 9 months");
        username.setTypeface(myCustomFont);
        experience.setTypeface(myCustomFont2);

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

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab,null);
            tv.setTypeface(myCustomFont);
            Objects.requireNonNull(tabLayout.getTabAt(i)).setCustomView(tv);
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.setPagingEnabled(false);

        PagerAdapter3 pagerAdapter = new PagerAdapter3(getSupportFragmentManager(), tabLayout.getTabCount());
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
