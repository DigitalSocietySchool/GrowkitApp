package com.example.joelruhe.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.joelruhe.myapplication.fragments.FeedFragment;
import com.example.joelruhe.myapplication.fragments.MyFriendsFragment;

public class PagerAdapter2 extends FragmentPagerAdapter {

    public int mTabs;

    public PagerAdapter2(FragmentManager fm, int Tabs) {
        super(fm);
        this.mTabs = Tabs;
    }

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

    @Override
    public int getCount() {
        return mTabs;
    }
}
