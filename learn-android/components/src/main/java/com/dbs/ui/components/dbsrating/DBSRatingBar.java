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

package com.dbs.ui.components.dbsrating;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.dbs.components.R;
import com.dbs.ui.BaseView;
import com.dbs.ui.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import static com.dbs.ui.utils.ViewUtils.dpToPixels;

/**
 * The DBSRatingBar component is to get user ratings input as stars.
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSRatingBar extends BaseView {

    private static final int DEFAULT_NUMBER_OF_STARS = 5;
    private static final int DEFAULT_RATING = 0;
    private static final int DEFAULT_LENGTH_OF_STAR_IN_DP = 35;
    private static final int DEFAULT_GAP_BETWEEN_STARS = 15;
    private static final int DEFAULT_STAR_DRAWABLE_RESOURCE_ID = R.drawable.rating_star;

    private int rating;
    private int maxRating;
    private int gapBetweenStarsInDp;
    private int starImageLengthInDp;

    private LinearLayout starsContainer;
    private List<Star> stars;
    private OnRatingChangeListener ratingChangeListener;
    private Context context;
    private int starDrawableResourceId;

    public DBSRatingBar(@NonNull Context context) {
        super(context);
    }

    public DBSRatingBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSRatingBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * This method is to get MaxRating value
     *
     * @return
     */
    public int getMaxRating() {
        return maxRating;
    }

    /**
     * This method is to set MaxRating value
     *
     * @param maxRating
     */
    public void setMaxRating(int maxRating) {
        if (maxRating < 1)
            throw new IllegalArgumentException("max rating should be atleast 1 or more");
        this.maxRating = maxRating;
        redraw();
    }

    /**
     * This method is use to update custom rating Star Drawable
     *
     * @param starDrawableResourceId
     */
    public void setStarDrawableResourceId(int starDrawableResourceId) {
        this.starDrawableResourceId = starDrawableResourceId;
        redraw();
    }

    /**
     * This method is to set on rating change listener
     *
     * @param ratingChangeListener
     */
    public void setRatingChangeListener(OnRatingChangeListener ratingChangeListener) {
        this.ratingChangeListener = ratingChangeListener;
    }

    /**
     * This method is to get rating.
     *
     * @return
     */
    public int getRating() {
        return rating;
    }

    /**
     * This method is to set rating programmatically. Rating should not be more than maxRating.
     *
     * @param rating
     */
    public void setRating(int rating) {
        if (this.rating == rating) return;
        if (rating < 0 || rating > maxRating)
            throw new IllegalArgumentException("rating should neither be negative nor greater than max rating");
        this.rating = rating;
        updateStarsAsPerRating(this.rating);
        if (ratingChangeListener != null) {
            ratingChangeListener.onRatingUpdated(this.rating);
        }
    }

    /**
     * This method is to set gap between stars programmatically.
     *
     * @param gapBetweenStars
     */
    public void setGapBetweenStarsInDp(int gapBetweenStars) {
        this.gapBetweenStarsInDp = gapBetweenStars;
        int marginInPixels = ViewUtils.dpToPixels(context, gapBetweenStarsInDp);
        for (Star star : stars)
            ((LinearLayout.LayoutParams) star.getLayoutParams()).setMarginEnd(marginInPixels);
    }

    /**
     * This method is to set star image length programmatically. width will be same as set length.
     *
     * @param starImageLengthInDp
     */
    public void setStarImageLengthInDp(int starImageLengthInDp) {
        this.starImageLengthInDp = starImageLengthInDp;
        int lengthInPixels = ViewUtils.dpToPixels(context, starImageLengthInDp);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(lengthInPixels, lengthInPixels);
        layoutParams.setMarginEnd(gapBetweenStarsInDp);
        for (Star star : stars)
            star.setLayoutParams(layoutParams);
    }

    private void redraw() {
        int lengthOfImageInPixels = dpToPixels(context, starImageLengthInDp);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(lengthOfImageInPixels, lengthOfImageInPixels);
        layoutParams.setMarginEnd(dpToPixels(context, gapBetweenStarsInDp));
        starsContainer.removeAllViews();
        stars = new ArrayList<>(maxRating);
        for (int i = 0; i < maxRating; i++) {
            Star star = new Star(context, i, starDrawableResourceId);
            starsContainer.addView(star, layoutParams);
            stars.add(star);
        }
        rating = Math.min(maxRating, rating);
        updateStarsAsPerRating(rating);
    }

    private void updateStarsAsPerRating(int rating) {
        for (int i = 0; i < rating; i++)
            stars.get(i).select();
        for (int i = rating; i < maxRating; i++)
            stars.get(i).deselect();
    }

    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        context = getContext();
        starsContainer = view.findViewById(R.id.stars_container);

        TypedArray attributesFromXml = context.obtainStyledAttributes(attrs, R.styleable.DBSRatingBar);
        maxRating = attributesFromXml.getInt(R.styleable.DBSRatingBar_dbs_ratingMaxRating, DEFAULT_NUMBER_OF_STARS);
        starImageLengthInDp = attributesFromXml.getInt(R.styleable.DBSRatingBar_dbs_ratingStarLengthInDp, DEFAULT_LENGTH_OF_STAR_IN_DP);
        gapBetweenStarsInDp = attributesFromXml.getInt(R.styleable.DBSRatingBar_dbs_ratingSpacingInDp, DEFAULT_GAP_BETWEEN_STARS);
        rating = attributesFromXml.getInt(R.styleable.DBSRatingBar_dbs_rating, DEFAULT_RATING);
        starDrawableResourceId = attributesFromXml.getResourceId(R.styleable.DBSRatingBar_dbs_src, DEFAULT_STAR_DRAWABLE_RESOURCE_ID);
        attributesFromXml.recycle();

        redraw();
    }

    @Override
    protected int provideLayoutId(String type) {
        return R.layout.dbs_rating;
    }

    /**
     * When an object of a type is attached to {@link DBSRatingBar}, its methods will
     * be called when rating is updated
     */
    public interface OnRatingChangeListener {
        /**
         * This method is called to notify you that,
         * rating was updated.
         *
         * @param rating updated rating value
         */
        void onRatingUpdated(int rating);
    }

    private class Star extends android.support.v7.widget.AppCompatImageView implements OnClickListener {

        int index;

        public Star(Context context, int index, int drawableResource) {
            super(context);
            this.setImageDrawable(AppCompatResources.getDrawable(getContext(), drawableResource));
            this.index = index;
            init();
        }

        public Star(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public Star(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        private void init() {
            deselect();
            this.setOnClickListener(this);
        }

        public void select() {
            super.setSelected(true);
        }

        public void deselect() {
            super.setSelected(false);
        }

        @Override
        public void onClick(View v) {
            DBSRatingBar.this.setRating(index + 1);
        }
    }
}