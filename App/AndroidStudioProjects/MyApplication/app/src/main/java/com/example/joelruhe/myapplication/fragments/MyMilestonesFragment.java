package com.example.joelruhe.myapplication.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joelruhe.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMilestonesFragment extends Fragment {

    @BindView(R.id.post_title)
    TextView title;
    @BindView(R.id.post_date)
    TextView date;
    Dialog myDialog;
    FloatingActionButton btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_milestones, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Typeface myCustomFont2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans_regular.ttf");

        // initialize the custom dialog
        myDialog = new Dialog(getActivity());

        title.setTypeface(myCustomFont2);
        date.setTypeface(myCustomFont2);

        btn  = view.findViewById(R.id.fab2);
        // show the pop up window wgen clicking on the floatActionButton
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShowPopUp();
            }
        });
    }

    // this method attaches our custom made layout to the view of a build in dialog from Android Studio
    // it sets the background to transparent so only our custom made layout is seen and not the background of
    // build in dialog
    public void ShowPopUp() {
        ImageView txtClose;
        myDialog.setContentView(R.layout.add_milestone_pop_up);
        txtClose = myDialog.findViewById(R.id.imageView4);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
