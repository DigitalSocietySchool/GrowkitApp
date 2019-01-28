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
import com.example.joelruhe.myapplication.PagerAdapter3;
import com.example.joelruhe.myapplication.PagerAdapter4;
import com.example.joelruhe.myapplication.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForumActivity extends AppCompatActivity {

    @BindView(R.id.text_toolbar)
    TextView txtPlantDataToolbar;

    Toolbar plantToolbar;
    ImageButton cancelIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        ButterKnife.bind(ForumActivity.this);

        TabLayout tabLayout = findViewById(R.id.id_tabs);
        final CustomViewPager viewPager = findViewById(R.id.viewPagerForum);

        tabLayout.addTab(tabLayout.newTab().setText("Ask"));
        tabLayout.addTab(tabLayout.newTab().setText("Answer"));

        final Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        final Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");

        txtPlantDataToolbar.setText("Forum");
        txtPlantDataToolbar.setTypeface(myCustomFont);

        plantToolbar = findViewById(R.id.forumToolbar);
        cancelIcon = plantToolbar.findViewById(R.id.btn_cancel);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // get the tabs to change the font on each tab
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab,null);
            tv.setTypeface(myCustomFont);
            Objects.requireNonNull(tabLayout.getTabAt(i)).setCustomView(tv);
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // make sliding tabs
        viewPager.setPagingEnabled(true);

        // get the pager adapter to call the fragments
        PagerAdapter4 pagerAdapter = new PagerAdapter4(getSupportFragmentManager(), tabLayout.getTabCount());
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
