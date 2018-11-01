/*
 * - Licensed Materials - Property of DBS Bank SG
 * - "Restricted Materials of DBS Bank"
 *
 * APP Studio SDK, Copyright (c) 2018. DBS Bank SG
 *
 * Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 */

package com.dbs.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dbs.components.R;
import com.dbs.ui.widgets.MultiStepsPager.OnMultiPageChangeListener;

/**
 * SimplePageFragment is a fragment for demo of MultiPager
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */

public class SimplePageFragment extends Fragment {
    private static OnMultiPageChangeListener multiPageChangeListener;
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static SimplePageFragment newInstance(int page, String title, OnMultiPageChangeListener listener) {
        SimplePageFragment fragmentFirst = new SimplePageFragment();
        multiPageChangeListener = listener;
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_page_fragment, container, false);
        TextView tvLabel = view.findViewById(R.id.simple_page_title);
        Button btnNext = view.findViewById(R.id.btnNext);
        Button btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> multiPageChangeListener.onBack());
        btnNext.setOnClickListener(v -> multiPageChangeListener.onNext());
        tvLabel.setText(page + " -- " + title);
        return view;
    }
}
