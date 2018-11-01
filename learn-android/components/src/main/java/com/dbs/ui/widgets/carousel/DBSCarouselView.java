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

package com.dbs.ui.widgets.carousel;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews.RemoteView;

import com.dbs.components.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The DBSCarouselView component is to create scrollable horizontal carousel
 *
 * @author DBS Bank, AppStudio Team
 */
@RemoteView
public class DBSCarouselView extends FrameLayout {

    private static final int DEFAULT_SLIDE_INTERVAL = 3500;
    private static final int DEFAULT_SLIDE_VELOCITY = 400;
    private final int DEFAULT_GRAVITY = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private int mPageCount;
    private int slideInterval = DEFAULT_SLIDE_INTERVAL;
    private int mIndicatorGravity = DEFAULT_GRAVITY;
    private int indicatorMarginVertical;
    private int indicatorMarginHorizontal;

    private DBSCarouselViewPager containerViewPager;
    private CirclePageIndicator mIndicator;
    private ViewListener mViewListener = null;
    private ImageListener mImageListener = null;

    private Timer swipeTimer;
    private SwipeTask swipeTask;

    private boolean autoPlay;
    private boolean disableAutoPlayOnUserInteraction;
    private boolean animateOnBoundary = true;

    private int previousState;
    ViewPager.OnPageChangeListener carouselOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            //Programmatic scroll

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

            //User initiated scroll

            if (previousState == ViewPager.SCROLL_STATE_DRAGGING
                    && state == ViewPager.SCROLL_STATE_SETTLING) {

                if (disableAutoPlayOnUserInteraction) {
                    pauseCarousel();
                } else {
                    playCarousel();
                }

            }

            previousState = state;

        }
    };
    private ViewPager.PageTransformer pageTransformer;

    public DBSCarouselView(Context context) {
        super(context);
    }

    public DBSCarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0, 0);
    }

    public DBSCarouselView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DBSCarouselView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (!isInEditMode()) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_carousel, this, true);
            containerViewPager = view.findViewById(R.id.containerViewPager);
            mIndicator = view.findViewById(R.id.indicator);

            containerViewPager.addOnPageChangeListener(carouselOnPageChangeListener);


            //Retrieve styles attributes
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DBSCarouselView, defStyleAttr, 0);
            try {
                indicatorMarginVertical = a.getDimensionPixelSize(R.styleable.DBSCarouselView_dbs_indicatorMarginVertical, getResources().getDimensionPixelSize(R.dimen.default_indicator_margin_vertical));
                indicatorMarginHorizontal = a.getDimensionPixelSize(R.styleable.DBSCarouselView_dbs_indicatorMarginHorizontal, getResources().getDimensionPixelSize(R.dimen.default_indicator_margin_horizontal));
                setPageTransformInterval(a.getInt(R.styleable.DBSCarouselView_dbs_pageTransformInterval, DEFAULT_SLIDE_VELOCITY));
                setSlideInterval(a.getInt(R.styleable.DBSCarouselView_dbs_slideInterval, DEFAULT_SLIDE_INTERVAL));
                setOrientation(a.getInt(R.styleable.DBSCarouselView_dbs_indicatorOrientation, LinearLayout.HORIZONTAL));
                setIndicatorGravity(a.getInt(R.styleable.DBSCarouselView_dbs_indicatorGravity, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL));
                setAutoPlay(a.getBoolean(R.styleable.DBSCarouselView_dbs_autoPlay, true));
                setDisableAutoPlayOnUserInteraction(a.getBoolean(R.styleable.DBSCarouselView_dbs_disableAutoPlayOnUserInteraction, false));
                setAnimateOnBoundary(a.getBoolean(R.styleable.DBSCarouselView_dbs_animateOnBoundary, true));

                setPageTransformer(a.getInt(R.styleable.DBSCarouselView_dbs_pageTransformer, CarouselViewPagerTransformer.DEFAULT));

                int fillColor = a.getColor(R.styleable.DBSCarouselView_dbs_fillColor, 0);
                if (fillColor != 0) {
                    setFillColor(fillColor);
                }
                int pageColor = a.getColor(R.styleable.DBSCarouselView_dbs_pageColor, 0);
                if (pageColor != 0) {
                    setPageColor(pageColor);
                }
                float radius = a.getDimensionPixelSize(R.styleable.DBSCarouselView_dbs_radius, 0);
                if (radius != 0) {
                    setRadius(radius);
                }
                setSnap(a.getBoolean(R.styleable.DBSCarouselView_dbs_snap, getResources().getBoolean(R.bool.default_circle_indicator_snap)));
                int strokeColor = a.getColor(R.styleable.DBSCarouselView_dbs_strokeColor, 0);
                if (strokeColor != 0) {
                    setStrokeColor(strokeColor);
                }
                float strokeWidth = a.getDimensionPixelSize(R.styleable.DBSCarouselView_dbs_strokeWidth, 0);
                if (strokeWidth != 0) {
                    setStrokeWidth(strokeWidth);
                }

            } finally {
                a.recycle();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        swipeTimer.cancel();
    }

    /**
     * Returns interval for one slide in milliseconds.
     */
    public int getSlideInterval() {
        return slideInterval;
    }

    /**
     * Set interval for one slide in milliseconds.
     *
     * @param slideInterval integer
     */
    public void setSlideInterval(int slideInterval) {
        this.slideInterval = slideInterval;

        if (null != containerViewPager) {
            playCarousel();
        }
    }

    /**
     * Set interval for one slide in milliseconds.
     *
     * @param slideInterval integer
     */
    public void reSetSlideInterval(int slideInterval) {
        setSlideInterval(slideInterval);

        if (null != containerViewPager) {
            playCarousel();
        }
    }

    /**
     * Sets speed at which page will slide from one to another in milliseconds
     *
     * @param pageTransformInterval integer
     */
    public void setPageTransformInterval(int pageTransformInterval) {
        int scrollFactor = pageTransformInterval > 0 ? pageTransformInterval : DEFAULT_SLIDE_VELOCITY;
        containerViewPager.setTransitionVelocity(scrollFactor);
    }

    /**
     * Returns the page transformer
     */
    public ViewPager.PageTransformer getPageTransformer() {
        return pageTransformer;
    }

    /**
     * Sets page transition animation.
     *
     * @param transformer Pass {@link CarouselViewPagerTransformer#FLOW}, {@link CarouselViewPagerTransformer#ZOOM}, {@link CarouselViewPagerTransformer#DEPTH} or {@link CarouselViewPagerTransformer#SLIDE_OVER}
     */
    public void setPageTransformer(@CarouselViewPagerTransformer.Transformer int transformer) {
        setPageTransformer(new CarouselViewPagerTransformer(transformer));

    }

    /**
     * Sets page transition animation.
     *
     * @param pageTransformer Choose from zoom, flow, depth, slide_over .
     */
    public void setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this.pageTransformer = pageTransformer;
        containerViewPager.setPageTransformer(true, pageTransformer);
    }

    /**
     * Sets whether to animate transition from last position to first or not.
     *
     * @param animateOnBoundary .
     */
    public void setAnimateOnBoundary(boolean animateOnBoundary) {
        this.animateOnBoundary = animateOnBoundary;
    }

    /**
     * Returns if the carousel is with auto-play enabled
     */
    public boolean isAutoPlay() {
        return autoPlay;
    }

    private void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    /**
     * Returns if the auto-play is disabled on user interaction
     */
    public boolean isDisableAutoPlayOnUserInteraction() {
        return disableAutoPlayOnUserInteraction;
    }

    private void setDisableAutoPlayOnUserInteraction(boolean disableAutoPlayOnUserInteraction) {
        this.disableAutoPlayOnUserInteraction = disableAutoPlayOnUserInteraction;
    }

    private void setData() {
        CarouselPagerAdapter carouselPagerAdapter = new CarouselPagerAdapter(getContext());
        containerViewPager.setAdapter(carouselPagerAdapter);
        if (getPageCount() > 1) {
            mIndicator.setViewPager(containerViewPager);
            mIndicator.requestLayout();
            mIndicator.invalidate();
            containerViewPager.setOffscreenPageLimit(getPageCount());
            playCarousel();
        }
    }

    private void stopScrollTimer() {

        if (null != swipeTimer) {
            swipeTimer.cancel();
        }

        if (null != swipeTask) {
            swipeTask.cancel();
        }
    }

    private void resetScrollTimer() {

        stopScrollTimer();

        swipeTask = new SwipeTask();
        swipeTimer = new Timer();

    }

    /**
     * Starts auto scrolling if
     * auto-play is enabled,
     * slideInterval is more than 0,
     * viewPager adapter is not null,
     * viewPager items count is more than 1
     */
    public void playCarousel() {

        resetScrollTimer();

        if (autoPlay && slideInterval > 0 && containerViewPager.getAdapter() != null && containerViewPager.getAdapter().getCount() > 1) {

            swipeTimer.schedule(swipeTask, slideInterval, slideInterval);
        }
    }

    /**
     * Pause auto scrolling unless user interacts provided autoPlay is enabled.
     */
    public void pauseCarousel() {

        resetScrollTimer();
    }

    /**
     * Stops auto scrolling.
     */
    public void stopCarousel() {

        this.autoPlay = false;
    }

    /**
     * Set listener for image
     *
     * @see ImageListener
     */
    public void setImageListener(ImageListener mImageListener) {
        this.mImageListener = mImageListener;
    }

    /**
     * Set listener for view
     *
     * @see ViewListener
     */
    public void setViewListener(ViewListener mViewListener) {
        this.mViewListener = mViewListener;
    }

    /**
     * Set listener for image click
     *
     * @see ImageClickListener
     */
    public void setImageClickListener(ImageClickListener imageClickListener) {
        containerViewPager.setImageClickListener(imageClickListener);
    }

    /**
     * Returns page count
     */
    public int getPageCount() {
        return mPageCount;
    }

    /**
     * Set page count
     */
    public void setPageCount(int mPageCount) {
        this.mPageCount = mPageCount;

        setData();
    }

    /**
     * Set on page change listener
     */
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        containerViewPager.addOnPageChangeListener(listener);
    }

    /**
     * Clears all on page change listeners
     */
    public void clearOnPageChangeListeners() {
        containerViewPager.clearOnPageChangeListeners();
    }

    /**
     * Returns index of current visible carousel item
     */
    public int getCurrentItem() {
        return containerViewPager.getCurrentItem();
    }

    /**
     * Set index of current visible carousel item
     */
    public void setCurrentItem(int item) {
        containerViewPager.setCurrentItem(item);
    }

    /**
     * Returns vertical margin for indicator
     */
    public int getIndicatorMarginVertical() {
        return indicatorMarginVertical;
    }

    /**
     * Set vertical margin for indicator
     */
    public void setIndicatorMarginVertical(int _indicatorMarginVertical) {
        indicatorMarginVertical = _indicatorMarginVertical;
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.topMargin = indicatorMarginVertical;
        params.bottomMargin = indicatorMarginVertical;
    }

    /**
     * Returns horizontal margin for indicator
     */
    public int getIndicatorMarginHorizontal() {
        return indicatorMarginHorizontal;
    }

    /**
     * Set horizontal margin for indicator
     */
    public void setIndicatorMarginHorizontal(int _indicatorMarginHorizontal) {
        indicatorMarginHorizontal = _indicatorMarginHorizontal;
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.leftMargin = indicatorMarginHorizontal;
        params.rightMargin = indicatorMarginHorizontal;
    }

    /**
     * Returns viewPager
     */
    public DBSCarouselViewPager getContainerViewPager() {
        return containerViewPager;
    }

    /**
     * Returns gravity for indicator, default is: Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM
     */
    public int getIndicatorGravity() {
        return mIndicatorGravity;
    }

    /**
     * Set gravity for indicator
     */
    public void setIndicatorGravity(int gravity) {
        mIndicatorGravity = gravity;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = mIndicatorGravity;
        params.setMargins(indicatorMarginHorizontal, indicatorMarginVertical, indicatorMarginHorizontal, indicatorMarginVertical);
        mIndicator.setLayoutParams(params);
    }

    /**
     * Returns orientation for indicator
     */
    public int getOrientation() {
        return mIndicator.getOrientation();
    }

    /**
     * Set orientation for indicator (HORIZONTAL|VERTICAL)
     */
    public void setOrientation(int orientation) {
        mIndicator.setOrientation(orientation);
    }

    /**
     * Returns fill color for indicator
     */
    public int getFillColor() {
        return mIndicator.getFillColor();
    }

    /**
     * Set fill color for indicator
     */
    public void setFillColor(int fillColor) {
        mIndicator.setFillColor(fillColor);
    }

    /**
     * Returns stroke color for indicator
     */
    public int getStrokeColor() {
        return mIndicator.getStrokeColor();
    }

    /**
     * Set stroke color for indicator
     */
    public void setStrokeColor(int strokeColor) {
        mIndicator.setStrokeColor(strokeColor);
    }

    /**
     * Returns stroke width for indicator
     */
    public float getStrokeWidth() {
        return mIndicator.getStrokeWidth();
    }

    /**
     * Set stroke width for indicator
     */
    public void setStrokeWidth(float strokeWidth) {
        mIndicator.setStrokeWidth(strokeWidth);
        int padding = (int) strokeWidth;
        mIndicator.setPadding(padding, padding, padding, padding);
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
    }

    /**
     * Returns background drawable for indicator
     */
    public Drawable getIndicatorBackground() {
        return mIndicator.getBackground();
    }

    /**
     * Returns color for indicator
     */
    public int getPageColor() {
        return mIndicator.getPageColor();
    }

    /**
     * Set color for indicator
     */
    public void setPageColor(int pageColor) {
        mIndicator.setPageColor(pageColor);
    }

    /**
     * Returns if snap to circle enabled
     */
    public boolean isSnap() {
        return mIndicator.isSnap();
    }

    /**
     * Set if snap is enabled/disabled
     */
    public void setSnap(boolean snap) {
        mIndicator.setSnap(snap);
    }

    /**
     * Returns radius of circle indicator
     */
    public float getRadius() {
        return mIndicator.getRadius();
    }

    /**
     * Set the radius for circle indicator
     */
    public void setRadius(float radius) {
        mIndicator.setRadius(radius);
    }

    private class CarouselPagerAdapter extends PagerAdapter {
        private Context mContext;

        public CarouselPagerAdapter(Context context) {
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {

            Object objectToReturn;

            //Either let user set image to ImageView
            if (mImageListener != null) {

                ImageView imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));  //setting image position
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                objectToReturn = imageView;
                mImageListener.setImageForPosition(position, imageView);

                collection.addView(imageView);

                //Or let user add his own ViewGroup
            } else if (mViewListener != null) {

                View view = mViewListener.setViewForPosition(position);

                if (null != view) {
                    objectToReturn = view;
                    collection.addView(view);
                } else {
                    throw new RuntimeException("View can not be null for position " + position);
                }

            } else {
                throw new RuntimeException("View must set " + ImageListener.class.getSimpleName() + " or " + ViewListener.class.getSimpleName() + ".");
            }

            return objectToReturn;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return getPageCount();
        }
    }

    private class SwipeTask extends TimerTask {
        public void run() {
            containerViewPager.post(() -> {

                int nextPage = (containerViewPager.getCurrentItem() + 1) % getPageCount();

                containerViewPager.setCurrentItem(nextPage, 0 != nextPage || animateOnBoundary);
            });
        }
    }
}
