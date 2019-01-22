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

        HorizontalScrollView scrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollView);

        LinearLayout topLinearLayout = new LinearLayout(getActivity());
        // topLinearLayout.setLayoutParams(android.widget.LinearLayout.LayoutParams.FILL_PARENT,android.widget.LinearLayout.LayoutParams.FILL_PARENT);
        topLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        int[] slide_images = {
                R.drawable.flowerpot,
                R.drawable.flowerpot,
                R.drawable.flowerpot,
                R.drawable.flowerpot,
                R.drawable.flowerpot,
        };

        String[] slide_headings = {
                "Coriander",
                "Strawberry",
                "Spinach",
                "Basil",
                "Spider plant"
        };

        String[] age = {
                "4 months old",
                "2 months old",
                "1 months old",
                "3 months old",
                "5 months old"
        };


        for (int i = 0; i < slide_images.length; i++){

            final ImageView imageView = new ImageView (getActivity());
            TextView myText = new TextView(getActivity());
            myText.setX(470);
            myText.setY(290);
            myText.setText(slide_headings[i]);
            myText.setTypeface(myCustomFont);
            myText.setTextSize(20);
            myText.setTextColor(getResources().getColor(R.color.colorPrimary));
            topLinearLayout.addView(myText);

            TextView myText2 = new TextView(getActivity());
            myText2.setX(300);
            myText2.setY(320);
            myText2.setText(age[i]);
            myText2.setTextColor(getResources().getColor(R.color.colorPrimary));
            myText2.setTypeface(myCustomFont2);

            topLinearLayout.addView(myText2);

            imageView.setTag(i);

            imageView.setImageResource(slide_images[i]);
            imageView.setX(-300);

            topLinearLayout.addView(imageView);
        }
        scrollView.addView(topLinearLayout);
    }
}
