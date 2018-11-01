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
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dbs.components.R;
import com.dbs.ui.BaseView;
import com.dbs.ui.styleable.StyleableComponent;

import static com.dbs.components.R.id;
import static com.dbs.components.R.layout;

/**
 * The DBSMenuItemView component is to create item view for DBSMenusView
 *
 * @author DBS Bank, AppStudio Team
 * @see DBSMenusView
 */
public class DBSMenuItemView extends BaseView implements StyleableComponent {
    private TextView menuTitle;
    private ImageView menuIcon;

    public DBSMenuItemView(@NonNull Context context) {
        super(context);
    }

    public DBSMenuItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSMenuItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        menuTitle = view.findViewById(id.dbsMenuItemTitle);
        menuIcon = view.findViewById(id.dbsMenuItemIcon);
    }

    @Override
    protected int provideLayoutId(String type) {
        return layout.menu_item;
    }

    /**
     * Sets default layout properties for menu item
     */
    public void setDefaultLayout() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);
    }

    /**
     * Sets menu item
     * @see MenuItem
     */
    public void setMenuItem(MenuItem menuItem) {
        menuIcon.setImageResource(menuItem.getResId());
        menuTitle.setText(menuItem.getTitle());
    }

    /**
     * Returns view for menu title
     */
    public TextView getMenuTitle() {
        return menuTitle;
    }

    /**
     * Sets view for menu title
     */
    public void setMenuTitle(TextView menuTitle) {
        this.menuTitle = menuTitle;
    }

    /**
     * Returns imageView for menu icon
     */
    public ImageView getMenuIcon() {
        return menuIcon;
    }

    /**
     * Sets imageView for menu icon
     */
    public void setMenuIcon(ImageView menuIcon) {
        this.menuIcon = menuIcon;
    }

    /**
     * Sets width of menu item
     */
    public void setWidth(int width) {
        getLayoutParams().width = width;
    }

    /**
     * Change the style of DBS Badge Programmatically
     * Ex.
     * <code>
     * <style name="DBSMenuItemByTheme">
     * <item name="android:background">?dbs_themePrimaryTextColorInverse</item>
     * <item name="android:textColor">?dbs_themeTertiaryTextColor</item>
     * </style>
     * </code>
     *
     * @param style
     */
    @Override
    public void applyStyle(@StyleRes int style) {
        TypedArray attributes = getContext().obtainStyledAttributes(style, R.styleable.DBSMenuItem);
        int menuBGColor = attributes.getColor(R.styleable.DBSMenuItem_android_background, Color.TRANSPARENT);
        int titleColor = attributes.getColor(R.styleable.DBSMenuItem_android_textColor, Color.TRANSPARENT);
        menuTitle.setTextColor(titleColor);
        this.setBackgroundColor(menuBGColor);
        attributes.recycle();
    }
}