package com.example.joelruhe.myapplication.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joelruhe.myapplication.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION;

public class MyGardenFragment extends Fragment {

    @BindView(R.id.plant_name)
    TextView plantName;
    @BindView(R.id.plant_age)
    TextView plantAge;
    @BindView(R.id.plant_name2)
    TextView plantName2;
    @BindView(R.id.plant_age2)
    TextView plantAge2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_garden, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Typeface myCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans_bold.ttf");
        Typeface myCustomFont2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans_regular.ttf");

        plantName.setTypeface(myCustomFont);
        plantAge.setTypeface(myCustomFont2);
        plantName2.setTypeface(myCustomFont);
        plantAge2.setTypeface(myCustomFont2);
    }
}
