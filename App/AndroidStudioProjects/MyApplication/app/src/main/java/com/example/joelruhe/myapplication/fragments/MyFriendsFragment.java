package com.example.joelruhe.myapplication.fragments;

import android.content.Intent;
import android.graphics.Typeface;
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
import com.example.joelruhe.myapplication.activities.MyFriendActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFriendsFragment extends Fragment {

    @BindView(R.id.fab)
    FloatingActionButton btn;
    Intent intent;

    @BindView(R.id.textView13)
    TextView textView13;
    @BindView(R.id.textView14)
    TextView textView14;
    @BindView(R.id.textView15)
    TextView textView15;
    @BindView(R.id.textView16)
    TextView textView16;
    @BindView(R.id.textView17)
    TextView textView17;

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
        btn  = view.findViewById(R.id.fab);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        Typeface myCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans_bold.ttf");
        Typeface myCustomFont2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans_regular.ttf");

        textView13.setTypeface(myCustomFont);
        textView14.setTypeface(myCustomFont);
        textView15.setTypeface(myCustomFont);
        textView16.setTypeface(myCustomFont);
        textView17.setTypeface(myCustomFont);

        return view;
}

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    }

    @OnClick({R.id.textView13, R.id.textView14, R.id.textView15, R.id.textView16, R.id.textView17})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView13:
                startActivity(new Intent(getActivity(), MyFriendActivity.class));
                break;
            case R.id.textView14:
                startActivity(new Intent(getActivity(), MyFriendActivity.class));
                break;
            case R.id.textView15:
                startActivity(new Intent(getActivity(), MyFriendActivity.class));
                break;
            case R.id.textView16:
                startActivity(new Intent(getActivity(), MyFriendActivity.class));
                break;
            case R.id.textView17:
                startActivity(new Intent(getActivity(), MyFriendActivity.class));
                break;
            default:
        }
    }
}
