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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dbs.components.R;
import com.dbs.ui.BaseView;
import com.dbs.ui.adapters.DBSRecyclerViewAdapter;
import com.dbs.ui.styleable.BiStyleableComponent;
import com.dbs.ui.styleable.StyleableComponent;

import java.util.List;

/**
 * The DBSMenusView component is to create scrollable horizontal list
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSMenusView extends BaseView implements StyleableComponent, BiStyleableComponent {
    private static float defaultNumberOfItem = 4.5f;
    private DBSRecyclerViewAdapter dbsRecyclerViewAdapter;
    private OnMenuItemClickListener onItemClickListener;
    private DBSRecyclerView dbsRecyclerView;
    private float itemsCountFraction = defaultNumberOfItem;
    private int menuItemTitleStyle;

    public DBSMenusView(@NonNull Context context) {
        super(context);
    }

    public DBSMenusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSMenusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        dbsRecyclerView = view.findViewById(R.id.dbsHorizontalScrollableMenu);
    }

    @Override
    protected int provideLayoutId(String type) {
        return R.layout.horizontal_scrollable_menus;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void applyStyle(int menusStyle) {
        // Nothing to style now
    }

    /**
     * @inheritDoc
     * Apply style for each item.
     */
    @Override
    public void applyStyle(int menusStyle, int menuItemTitleStyle) {
        this.menuItemTitleStyle = menuItemTitleStyle;
    }

    /**
     * This method add menu item and initialize scrollable menu
     *
     * @param menus
     */
    public void addMenus(List<MenuItem> menus) {
        dbsRecyclerViewAdapter = new DBSRecyclerViewAdapter<MenuItem>(menus) {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                DBSMenuItemView menuItemView = new DBSMenuItemView(getContext());
                menuItemView.applyStyle(menuItemTitleStyle);
                return new MenuViewHolder(menuItemView, itemsCountFraction);
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, MenuItem val, int position) {
                MenuViewHolder menuViewHolder = (MenuViewHolder) holder;
                menuViewHolder.setData(val);
                if (onItemClickListener != null) {
                    menuViewHolder.setMenuViewOnClickListener(v -> onItemClickListener.onItemClick(v, val));
                }
            }
        };
        if (dbsRecyclerView != null) {
            dbsRecyclerView.setRecyclerViewAdapter(dbsRecyclerViewAdapter);
        }
    }

    /**
     * To set on item click listener
     * <p>
     * [Note]: This has to be called before addMenus
     *
     * @param listener
     */
    public void setOnItemClickListener(OnMenuItemClickListener listener) {
        onItemClickListener = listener;
    }

    /**
     * This method take fraction of menu item u want to show
     * <p>
     * For example:
     * 1. If u want to show 3 menu item in screen
     * setNumberOfVisibleItems(3)
     * 2. If u want to show 4 menu item and half of the 5th menu item in screen
     * setNumberOfVisibleItems(4.5)
     *
     * @param count
     */
    public void setNumberOfVisibleItems(float count) {
        itemsCountFraction = count;
    }

    /**
     * This method to set divider between menu items, Default would be no divider.
     */
    public void setDivider() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dbsRecyclerView.getContext(), LinearLayout.HORIZONTAL);
        dbsRecyclerView.getRecyclerView().addItemDecoration(dividerItemDecoration);
    }

    /**
     * Return menu adapter for complex uses.
     *
     * @return
     */
    public RecyclerView.Adapter getMenuAdapter() {
        return dbsRecyclerViewAdapter;
    }

    /**
     * Returns recycler view used for menu, for complex uses
     *
     * @return
     */
    public DBSRecyclerView getDBSRecyclerView() {
        return dbsRecyclerView;
    }

    /**
     * Interface to set MenuItemClickListener
     */
    public interface OnMenuItemClickListener {
        void onItemClick(View view, MenuItem item);
    }
}
