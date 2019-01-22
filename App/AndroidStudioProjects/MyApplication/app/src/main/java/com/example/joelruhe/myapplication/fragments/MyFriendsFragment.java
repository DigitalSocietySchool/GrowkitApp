package com.example.joelruhe.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.activities.AddFriendsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFriendsFragment extends Fragment {

    @BindView(R.id.fab)
    FloatingActionButton btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_garden, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    }

    @OnClick(R.id.fab)
    public void setBtn(View view){
        Intent i = new Intent(getActivity(), AddFriendsActivity.class) {}
        
    }
}
