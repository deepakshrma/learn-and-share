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

package com.dbs.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dbs.components.R;


/**
 * The DBSPageHeaderView component is to create page header view
 * with multi-colored text, icon on the right bottom and page indicator
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSPageHeaderView extends LinearLayout {
    public static final int IMAGE_POSITION_TOP = 0;
    public static final int IMAGE_POSITION_BOTTOM = 1;
    public static final int IMAGE_POSITION_RIGHT = 2;
    private AppCompatTextView title;
    private AppCompatTextView subTitle;
    private ImageView headerImage;
    private View progressBar;
    private int progress;
    private AppCompatButton cta;

    public DBSPageHeaderView(Context context) {
        this(context, null);
    }

    public DBSPageHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DBSPageHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateUI(context, attrs);
        linkAllUIElementsToFields();
        setValues(context, attrs);
    }

    public AppCompatTextView getTitle() {
        return title;
    }

    public AppCompatTextView getSubTitle() {
        return subTitle;
    }

    public View getProgressBar() {
        return progressBar;
    }

    public int getProgress() {
        return progress;
    }

    public ImageView getHeaderImage() {
        return headerImage;
    }

    public AppCompatButton getCta() {
        return cta;
    }

    public void setOnCtaClicked(OnClickListener onClickListener){
        if(cta == null)
            return;
        cta.setOnClickListener(onClickListener);
    }

    public void setProgress(int progress) {
        ((LayoutParams) progressBar.getLayoutParams()).weight = (float)progress / 100;
    }

    private void setValues(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DBSPageHeaderView);
        setTextOrHide(title, typedArray, R.styleable.DBSPageHeaderView_dbs_title);
        setTextOrHide(subTitle, typedArray, R.styleable.DBSPageHeaderView_dbs_subTitle);
        setImageDrawableOrHide(headerImage, typedArray, R.styleable.DBSPageHeaderView_dbs_imageSrc);
        setTextOrHide(cta, typedArray, R.styleable.DBSPageHeaderView_dbs_cta);
        setProgressOrHide(typedArray);
        typedArray.recycle();
    }

    private void setProgressOrHide(TypedArray typedArray) {
        if(typedArray.hasValue(R.styleable.DBSPageHeaderView_dbs_progress)){
            progress = typedArray.getInt(R.styleable.DBSPageHeaderView_dbs_progress, 0);
            setProgress(progress);
        }
        else
            hide(progressBar == null ? null : ((View) progressBar.getParent()));
    }

    private void setImageDrawableOrHide(ImageView headerImage, TypedArray typedArray, int attributeIndex) {
        if(typedArray.hasValue(attributeIndex))
            headerImage.setImageDrawable(typedArray.getDrawable(attributeIndex));
        else
            hide(headerImage);
    }

    private void setTextOrHide(TextView textView, TypedArray typedArray, int attributeIndex) {
        if(typedArray.hasValue(attributeIndex))
            textView.setText(typedArray.getString(attributeIndex));
        else
            hide(textView);
    }

    private void hide(View view) {
        if(view == null)
            return;
        view.setVisibility(GONE);
    }

    private void inflateUI(Context context, AttributeSet attrs) {
        int layout = R.layout.dbs_page_header_image_right;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DBSPageHeaderView);
        if(typedArray.hasValue(R.styleable.DBSPageHeaderView_dbs_cta))
            layout = R.layout.dbs_page_header_cta;
        else if(typedArray.hasValue(R.styleable.DBSPageHeaderView_dbs_imagePosition)){
            int imagePosition = typedArray.getInt(R.styleable.DBSPageHeaderView_dbs_imagePosition, IMAGE_POSITION_RIGHT);
            switch (imagePosition){
                case IMAGE_POSITION_BOTTOM:
                    layout = R.layout.dbs_page_header_image_bottom;
                    break;
                case IMAGE_POSITION_TOP:
                    layout = R.layout.dbs_page_header_image_top;
                    break;
                case IMAGE_POSITION_RIGHT:
                    layout = R.layout.dbs_page_header_image_right;
                    break;
            }
        }
        typedArray.recycle();
        View.inflate(context, layout, this);
    }

    private void linkAllUIElementsToFields() {
        title = findViewById(R.id.title);
        subTitle = findViewById(R.id.subtitle);
        headerImage = findViewById(R.id.image);
        progressBar = findViewById(R.id.progressbar);
        cta = findViewById(R.id.cta);
    }

}