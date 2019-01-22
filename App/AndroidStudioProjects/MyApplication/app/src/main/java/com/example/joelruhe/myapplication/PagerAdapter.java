package com.example.joelruhe.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.joelruhe.myapplication.fragments.MyGardenFragment;
import com.example.joelruhe.myapplication.fragments.MyMilestonesFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public int mTabs;

    public PagerAdapter(FragmentManager fm, int Tabs) {
        super(fm);
        this.mTabs = Tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MyGardenFragment myGarden = new MyGardenFragment();
                return myGarden;
            case 1:
                MyMilestonesFragment myMilestones = new MyMilestonesFragment();
                return myMilestones;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabs;
    }
}
