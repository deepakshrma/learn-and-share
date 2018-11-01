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

package com.dbs.ui.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.dbs.components.R;
import com.dbs.ui.BaseView;

/**
 * The DBSTransferView is a component which displays view which have 2 account list item views.
 * One for each source and destination account info view.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @see DBSAccountInfoListItemView
 * @since 2018-05-01
 */
public class DBSTransferView extends BaseView {
    private ImageView downArrow1;
    private ImageView downArrow2;
    private ImageView downArrow3;
    private Animation fadeInAnimationForArrow1;
    private Animation fadeInAnimationForArrow2;
    private Animation fadeInAnimationForArrow3;
    private Animation fadeOutAnimationForArrow1;
    private Animation fadeOutAnimationForArrow2;
    private Animation fadeOutAnimationForArrow3;

    private DBSAccountInfoListItemView sourceAccountInfo;
    private DBSAccountInfoListItemView destinationAccountInfo;

    private long animationDuration = 500;

    public DBSTransferView(@NonNull Context context) {
        super(context);
    }

    public DBSTransferView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSTransferView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        downArrow1 = view.findViewById(R.id.down_arrow1);
        downArrow2 = view.findViewById(R.id.down_arrow2);
        downArrow3 = view.findViewById(R.id.down_arrow3);

        sourceAccountInfo = view.findViewById(R.id.source_account_details);
        destinationAccountInfo = view.findViewById(R.id.destination_account_details);
    }

    @Override
    protected int provideLayoutId(String type) {
        return R.layout.dbs_transfer_view;
    }

    /**
     * Sets the duration for which animation should run.
     * The animation is to indicate the direction of transfer. From source account view to destination account view.
     */
    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    /**
     * Starts animation.
     */
    public void startAnimation() {
        initAnimations();
        makeAllAnimationImagesVisible();
        downArrow1.startAnimation(fadeInAnimationForArrow1);
    }

    /**
     * By default the right arrow visibility is GONE.
     * So explicitly need to set the visibility by calling this methode with Drawabel id
     */
    public void setSourceViewRightImage(int drawableId) {
        sourceAccountInfo.setRightNavImage(getContext().getResources().getDrawable(drawableId));

    }
    public void setDestinationViewRightImage(int drawableId) {
        sourceAccountInfo.setRightNavImage(getContext().getResources().getDrawable(drawableId));
    }

    /**
     * Sets drawable image on which animation needs to be applied.
     */
    public void setAnimationImage(Drawable animationImage) {
        downArrow1.setImageDrawable(animationImage);
        downArrow2.setImageDrawable(animationImage);
        downArrow3.setImageDrawable(animationImage);
    }

    /**
     * Stops animation.
     */
    public void stopAnimation() {
        resetAllAnimations();
        cancelAllAnimations();
        clearAnimationsFromViews();
        makeAllAnimationImagesVisible();
    }

    /**
     * Sets click listener for both source and destination account.
     */
    public void setOnTransferViewClickListener(OnClickListener onTransferViewClickListener) {
        destinationAccountInfo.setOnClickListener(onTransferViewClickListener::onDestinationAccountViewClicked);
        sourceAccountInfo.setOnClickListener(onTransferViewClickListener::onSourceAccountViewClicked);
    }

    /**
     * Returns source account info view
     */
    public DBSAccountInfoListItemView getSourceAccountInfoView() {
        return sourceAccountInfo;
    }

    /**
     * Returns destination account info view
     */
    public DBSAccountInfoListItemView getDestinationAccountInfoView() {
        return destinationAccountInfo;
    }

    private void makeAllAnimationImagesVisible() {
        downArrow1.setVisibility(VISIBLE);
        downArrow2.setVisibility(VISIBLE);
        downArrow3.setVisibility(VISIBLE);
    }

    private void clearAnimationsFromViews() {
        downArrow1.clearAnimation();
        downArrow2.clearAnimation();
        downArrow3.clearAnimation();
    }

    private void cancelAllAnimations() {
        fadeInAnimationForArrow1.cancel();
        fadeInAnimationForArrow2.cancel();
        fadeInAnimationForArrow3.cancel();
        fadeOutAnimationForArrow1.cancel();
        fadeOutAnimationForArrow2.cancel();
        fadeOutAnimationForArrow3.cancel();
    }

    private void resetAllAnimations() {
        fadeInAnimationForArrow1.reset();
        fadeInAnimationForArrow2.reset();
        fadeInAnimationForArrow3.reset();
        fadeOutAnimationForArrow1.reset();
        fadeOutAnimationForArrow2.reset();
        fadeOutAnimationForArrow3.reset();
    }

    private void initAnimations() {
        initAnimationForArrow1();
        initAnimationForArrow2();
        initAnimationForArrow3();
    }

    private void initAnimationForArrow3() {
        fadeInAnimationForArrow3 = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        fadeOutAnimationForArrow3 = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);

        fadeInAnimationForArrow3.setDuration(animationDuration);
        fadeOutAnimationForArrow3.setDuration(animationDuration);

        fadeInAnimationForArrow3.setAnimationListener(getAnimationListener(() -> downArrow1.startAnimation(fadeOutAnimationForArrow1)));

        fadeOutAnimationForArrow3.setAnimationListener(getAnimationListener(() -> {
            downArrow3.setVisibility(INVISIBLE);
            downArrow1.setVisibility(VISIBLE);
            downArrow1.startAnimation(fadeInAnimationForArrow1);
        }));
    }

    private void initAnimationForArrow2() {
        fadeInAnimationForArrow2 = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        fadeOutAnimationForArrow2 = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);

        fadeInAnimationForArrow2.setDuration(animationDuration);
        fadeOutAnimationForArrow2.setDuration(animationDuration);

        fadeInAnimationForArrow2.setAnimationListener(getAnimationListener(() -> {
            downArrow3.setVisibility(VISIBLE);
            downArrow3.startAnimation(fadeInAnimationForArrow3);
        }));
        fadeOutAnimationForArrow2.setAnimationListener(getAnimationListener(() -> {
            downArrow3.startAnimation(fadeOutAnimationForArrow3);
            downArrow2.setVisibility(INVISIBLE);
        }));
    }

    private void initAnimationForArrow1() {
        fadeInAnimationForArrow1 = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        fadeOutAnimationForArrow1 = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);

        fadeInAnimationForArrow1.setDuration(animationDuration);
        fadeOutAnimationForArrow1.setDuration(animationDuration);

        fadeInAnimationForArrow1.setAnimationListener(getAnimationListener(() -> {
            downArrow2.setVisibility(VISIBLE);
            downArrow2.startAnimation(fadeInAnimationForArrow2);
        }));
        fadeOutAnimationForArrow1.setAnimationListener(getAnimationListener(() -> {
            downArrow2.startAnimation(fadeOutAnimationForArrow2);
            downArrow1.setVisibility(INVISIBLE);
        }));
    }

    private Animation.AnimationListener getAnimationListener(Runnable callbackOnAnimationEnd) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // do nothing on onAnimationStart
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                callbackOnAnimationEnd.run();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // do nothing on onAnimationRepeat
            }
        };
    }

    /**
     * When an object of a type is attached to {@link DBSTransferView}, its methods will
     * be called when view is clicked.
     */
    public interface OnClickListener {
        /**
         * This method is called to notify you that,
         * the source account view was clicked.
         */
        void onSourceAccountViewClicked(View view);

        /**
         * This method is called to notify you that,
         * the destination account view was clicked.
         */
        void onDestinationAccountViewClicked(View view);
    }
}
