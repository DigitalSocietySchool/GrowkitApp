package com.example.joelruhe.myapplication.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joelruhe.myapplication.R;

import butterknife.ButterKnife;

public class MyGardenFriendsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // add butter knife to this fragment
        View view = inflater.inflate(R.layout.fragment_my_garden_friends, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // get the custom fonts
        Typeface myCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans_bold.ttf");
        Typeface myCustomFont2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans_regular.ttf");
    }
}
