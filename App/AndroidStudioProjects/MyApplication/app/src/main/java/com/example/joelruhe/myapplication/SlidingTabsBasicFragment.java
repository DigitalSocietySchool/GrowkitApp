/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.joelruhe.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.joelruhe.myapplication.activities.PlantActivity;
import com.example.joelruhe.myapplication.common.view.SlidingTabLayout;

import java.util.Objects;

public class SlidingTabsBasicFragment extends Fragment {

    protected SlidingTabLayout mSlidingTabLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ViewPager mViewPager;
        mViewPager = view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());

        mSlidingTabLayout = view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    class SamplePagerAdapter extends PagerAdapter {

        //Arrays
        private int[] slide_images = {

                R.drawable.cactus,
                R.drawable.plant1,
                R.drawable.plant2,
                R.drawable.plants,
                R.drawable.potplant
        };

        private String[] slide_headings = {

                "CACTUS",
                "TOMATOES",
                "SPINACH",
                "BASIL",
                "SPIDER PLANT"
        };

        @Override
        public int getCount() {
            return slide_headings.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Plant " + (position + 1);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @NonNull
        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
            // Inflate a new layout from our resources
            View view = Objects.requireNonNull(getActivity()).getLayoutInflater().inflate(R.layout.pager_item,
                    container, false);
            // Add the newly created View to the ViewPager
            container.addView(view);

            ImageButton btn = view.findViewById(R.id.slide_image);
            TextView slideHeading = view.findViewById(R.id.slide_heading);

            btn.setImageResource(slide_images[position]);
            slideHeading.setText(slide_headings[position]);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position == 0) {
                        Intent intent = new Intent(getActivity(), PlantActivity.class);
                        startActivity(intent);
                    }
                    if (position == 1) {
                        Intent intent = new Intent(getActivity(), PlantActivity.class);
                        startActivity(intent);
                    }
                    if (position == 2) {
                        Intent intent = new Intent(getActivity(), PlantActivity.class);
                        startActivity(intent);
                    }
                    if (position == 3) {
                        Intent intent = new Intent(getActivity(), PlantActivity.class);
                        startActivity(intent);
                    }
                    if (position == 4) {
                        Intent intent = new Intent(getActivity(), PlantActivity.class);
                        startActivity(intent);
                    }
                }
            });

            // Return the View
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
