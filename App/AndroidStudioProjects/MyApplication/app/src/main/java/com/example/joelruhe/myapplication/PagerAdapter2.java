package com.example.joelruhe.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.joelruhe.myapplication.fragments.FeedFragment;
import com.example.joelruhe.myapplication.fragments.MyFriendsFragment;

public class PagerAdapter2 extends FragmentPagerAdapter {

    public int mTabs;

    // get the the tabs and the fragments to use in the adapter
    public PagerAdapter2(FragmentManager fm, int Tabs) {
        super(fm);
        this.mTabs = Tabs;
    }

    // navigate to the right fragments based on the selected position in the tabLayout
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FeedFragment feedFragment = new FeedFragment();
                return feedFragment;
            case 1:
                 MyFriendsFragment myFriendsFragment = new MyFriendsFragment();
                return myFriendsFragment;
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
