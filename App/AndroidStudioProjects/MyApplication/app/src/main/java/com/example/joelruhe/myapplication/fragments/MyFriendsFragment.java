package com.example.joelruhe.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.activities.AddFriendsActivity;

import butterknife.ButterKnife;

public class MyFriendsFragment extends Fragment {

    FloatingActionButton btn;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_friends, container, false);
        ButterKnife.bind(this, view);

        intent = new Intent(getActivity(), AddFriendsActivity.class);
        btn  = (FloatingActionButton) view.findViewById(R.id.fab);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    }


}
