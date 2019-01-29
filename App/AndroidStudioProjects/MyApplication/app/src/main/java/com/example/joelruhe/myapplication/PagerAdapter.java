package com.example.joelruhe.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.joelruhe.myapplication.fragments.MyGardenFragment;
import com.example.joelruhe.myapplication.fragments.MyMilestonesFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public int mTabs;

    // get the the tabs and the fragments to use in the adapter
    public PagerAdapter(FragmentManager fm, int Tabs) {
        super(fm);
        this.mTabs = Tabs;
    }

    // navigate to the right fragments based on the selected position in the tabLayout
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

    // count all the tabs
    @Override
    public int getCount() {
        return mTabs;
    }
}
