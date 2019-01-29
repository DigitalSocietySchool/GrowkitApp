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

public class AskFragment extends Fragment {

    Dialog myDialog;
    FloatingActionButton btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask, container, false);
        ButterKnife.bind(this, view);

        // initialize the custom dialog
        myDialog = new Dialog(getActivity());

        btn  = view.findViewById(R.id.fab_ask_forum);
        // show the pop up window wgen clicking on the floatActionButton
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShowPopUp();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

    }

    // this method attaches our custom made layout to the view of a build in dialog from Android Studio
    // it sets the background to transparent so only our custom made layout is seen and not the background of
    // build in dialog
    public void ShowPopUp() {
        ImageView txtClose;
        myDialog.setContentView(R.layout.ask_question_pop_up);
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
