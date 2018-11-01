package com.dbs.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.dbs.components.R;

/**
 * The DBSDashboardToolbarView component is to create toolbar view for dashboard.
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSDashboardToolbarView extends ConstraintLayout {

    private DBSImageBadgeView leftNavigationItemView;
    private DBSImageBadgeView rightNavigationItemView;

    public DBSDashboardToolbarView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public DBSDashboardToolbarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dbs_dashboard_toolbar, this, true);
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.DBSDashboardToolbarView);
        @DrawableRes int leftIconDrawable = attributes.getResourceId(R.styleable.DBSDashboardToolbarView_leftIconDrawable, -1);
        @DrawableRes int rightIconDrawable = attributes.getResourceId(R.styleable.DBSDashboardToolbarView_rightIconDrawable, -1);
        int leftBadgeValue = attributes.getInt(R.styleable.DBSDashboardToolbarView_leftBadgeValue, 0);
        int rightBadgeValue = attributes.getInt(R.styleable.DBSDashboardToolbarView_rightBadgeValue, 0);
        attributes.recycle();

        leftNavigationItemView = findViewById(R.id.leftNavigationItem);
        rightNavigationItemView = findViewById(R.id.rightNavigationItem);

        configureNavigationItem(leftNavigationItemView, leftIconDrawable, leftBadgeValue);
        configureNavigationItem(rightNavigationItemView, rightIconDrawable, rightBadgeValue);
    }

    /**
     * Sets click listener for the left icon
     * @see ILeftItemClickListener
     */
    public void setOnLeftItemClickListener(ILeftItemClickListener onItemClickListener) {
        leftNavigationItemView.setOnClickListener(v -> onItemClickListener.onLeftItemClick());
    }

    /**
     * Sets click listener for the right icon
     * @see IRightItemClickListener
     */
    public void setOnRightItemClickListener(IRightItemClickListener onItemClickListener) {
        rightNavigationItemView.setOnClickListener(v -> onItemClickListener.onRightItemClick());
    }

    /**
     * Sets drawable for left icon
     */
    public void setLeftNavigationItemDrawable(@DrawableRes int iconDrawable) {
        setNavigationItemDrawable(leftNavigationItemView, iconDrawable);
    }

    /**
     * Sets badge value for left icon
     */
    public void setLeftNavigationItemBadgeValue(int badgeValue) {
        setNavigationItemBadgeValue(leftNavigationItemView, badgeValue);
    }

    /**
     * Sets drawable for right icon
     */
    public void setRightNavigationItemDrawable(@DrawableRes int iconDrawable) {
        setNavigationItemDrawable(rightNavigationItemView, iconDrawable);
    }

    /**
     * Sets badge value for right icon
     */
    public void setRightNavigationItemBadgeValue(int badgeValue) {
        setNavigationItemBadgeValue(rightNavigationItemView, badgeValue);
    }

    private void configureNavigationItem(DBSImageBadgeView navigationItem, @DrawableRes int iconDrawable, int badgeValue) {
        if (iconDrawable != -1) {
            setNavigationItemDrawable(navigationItem, iconDrawable);
            setNavigationItemBadgeValue(navigationItem, badgeValue);
        }
    }

    private void setNavigationItemBadgeValue(DBSImageBadgeView navigationItem, int badgeValue) {
        navigationItem.setBadgeValue(badgeValue);
    }

    private void setNavigationItemDrawable(DBSImageBadgeView navigationItem, @DrawableRes int iconDrawable) {
        navigationItem.setVisibility(VISIBLE);
        navigationItem.setImageDrawable(getResources().getDrawable(iconDrawable));
    }

    /**
     * When an object of a type is attached to {@link DBSDashboardToolbarView}, its methods will
     * be called when left icon is clicked.
     */
    public interface ILeftItemClickListener {
        /**
         * This method is called when left icon is clicked
         */
        void onLeftItemClick();
    }

    /**
     * When an object of a type is attached to {@link DBSDashboardToolbarView}, its methods will
     * be called when right icon is clicked.
     */
    public interface IRightItemClickListener {
        /**
         * This method is called when right icon is clicked
         */
        void onRightItemClick();
    }
}