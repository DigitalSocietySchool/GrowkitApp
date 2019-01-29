package com.example.joelruhe.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.joelruhe.myapplication.fragments.FeedFragment;
import com.example.joelruhe.myapplication.fragments.MyFriendsFragment;
import com.example.joelruhe.myapplication.fragments.MyGardenFriendsFragment;
import com.example.joelruhe.myapplication.fragments.MyMilestonesFriendsFragment;

public class PagerAdapter3 extends FragmentPagerAdapter {

    public int mTabs;

    // get the the tabs and the fragments to use in the adapter
    public PagerAdapter3(FragmentManager fm, int Tabs) {
        super(fm);
        this.mTabs = Tabs;
    }

    // navigate to the right fragments based on the selected position in the tabLayout
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MyGardenFriendsFragment myGardenFriendsFragment = new MyGardenFriendsFragment();
                return myGardenFriendsFragment;
            case 1:
                 MyMilestonesFriendsFragment myMilestonesFriendsFragment = new MyMilestonesFriendsFragment();
                return myMilestonesFriendsFragment;
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
