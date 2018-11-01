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

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dbs.ui.utils.ViewUtils;


/**
 * The MenuViewHolder is a custom view holder for {@link DBSMenuItemView}
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */

public class MenuViewHolder extends RecyclerView.ViewHolder {
    private DBSMenuItemView menuItemView;

    public MenuViewHolder(DBSMenuItemView itemView, float itemsCountFraction) {
        super(itemView);

        itemView.setDefaultLayout();
        final int itemCount = (int) itemsCountFraction;
        int[] windowDimensions = ViewUtils.getScreenSize();
        int widthOfItem = windowDimensions[0] / itemCount;
        int widthOfNextItemToShow = (int) (widthOfItem * (itemsCountFraction - itemCount));
        int width = widthOfItem - widthOfNextItemToShow / itemCount;
        itemView.setWidth(width);

        menuItemView = itemView;
    }

    /**
     * Sets data for view.
     */
    public void setData(MenuItem data) {
        menuItemView.setMenuItem(data);
    }

    /**
     * Sets view click listener.
     */
    public void setMenuViewOnClickListener(View.OnClickListener menuViewOnClickListener) {
        menuItemView.setOnClickListener(menuViewOnClickListener);
    }
}
