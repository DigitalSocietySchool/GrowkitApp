package com.example.joelruhe.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.joelruhe.myapplication.activities.ForumActivity;
import com.example.joelruhe.myapplication.fragments.AnswerFragment;
import com.example.joelruhe.myapplication.fragments.AskFragment;
import com.example.joelruhe.myapplication.fragments.MyGardenFriendsFragment;
import com.example.joelruhe.myapplication.fragments.MyMilestonesFriendsFragment;

public class PagerAdapter4 extends FragmentPagerAdapter {

    public int mTabs;

    // get the the tabs and the fragments to use in the adapter
    public PagerAdapter4(FragmentManager fm, int Tabs) {
        super(fm);
        this.mTabs = Tabs;
    }

    // navigate to the right fragments based on the selected position in the tabLayout
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AskFragment askFragment = new AskFragment();
                return askFragment;
            case 1:
                 AnswerFragment answerFragment = new AnswerFragment();
                return answerFragment;
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
