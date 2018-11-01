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
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.dbs.components.R;
import com.dbs.ui.BaseView;

import static com.dbs.ui.utils.ViewUtils.toggle;

/**
 * The DBSExpandableCard Component is to create customized card view
 * with header, container and footer
 * <p>
 * Container can change state depending on the clicks on header/footer.
 * <p>
 * Default states of container are VISIBLE/GONE.
 * User can also customize the states instead of default ones.
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSExpandableCard extends BaseView {

    private View header;
    private View container;
    private View footer;
    private FrameLayout containerLayout;
    private FrameLayout footerLayout;
    private FrameLayout headerLayout;

    private boolean expanded = false;
    private ExpandableCardToggleListener toggleListener;
    private TogglerPane togglerPane;

    public DBSExpandableCard(@NonNull Context context) {
        super(context);
    }

    public DBSExpandableCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSExpandableCard(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        headerLayout = view.findViewById(R.id.expandable_card_header);
        containerLayout = view.findViewById(R.id.expandable_card_container);
        footerLayout = view.findViewById(R.id.expandable_card_footer);
    }

    @Override
    protected int provideLayoutId(String type) {
        return R.layout.expandable_card;
    }

    /**
     * Returns header view
     */
    public View getHeader() {
        return header;
    }

    /**
     * Sets header view
     */
    public void setHeader(View header) {
        this.header = header;
        headerLayout.addView(header);
    }

    /**
     * Returns container view
     */
    public View getContainer() {
        return container;
    }

    /**
     * Sets container view
     */
    public void setContainer(View container) {
        this.container = container;
        containerLayout.removeAllViews();
        containerLayout.addView(container);
    }

    /**
     * Returns footer view
     */
    public View getFooter() {
        return footer;
    }

    /**
     * Sets footer view
     */
    public void setFooter(View footer) {
        this.footer = footer;
        footerLayout.removeAllViews();
        footerLayout.addView(footer);
    }

    /**
     * Sets toggler pane.
     * The view pane clicking which container will change states.
     *
     * @see TogglerPane
     */
    public void setTogglerPane(TogglerPane pane) {
        this.togglerPane = pane;
    }

    /**
     * Sets id of toggler view.
     * The id of view clicking which container will change states.
     */
    public void setTogglerOn(int clickableViewId) {
        containerLayout.setVisibility(GONE);
        setToggler(clickableViewId, v -> toggle(containerLayout, expanded));
    }

    /**
     * Sets container state as expanded
     */
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        if (toggleListener != null) {
            toggleListener.onToggle(expanded);
        }
    }

    /**
     * Sets id of toggler view and callback to execute on click.
     *
     * @see ExpandableCardToggleListener
     */
    public void setToggler(int clickableViewId, ExpandableCardToggleListener toggleListener) {
        View toggler = togglerPane == TogglerPane.FOOTER ? footerLayout : headerLayout;
        this.toggleListener = toggleListener;
        toggleListener.onToggle(expanded);
        toggler.findViewById(clickableViewId).setOnClickListener(v -> {
            this.expanded = !this.expanded;
            toggleListener.onToggle(this.expanded);
        });
    }

    /**
     * Types of toggler pane with which you can change container state.
     */
    public enum TogglerPane {
        HEADER,
        FOOTER
    }

    /**
     * When an object of a type is attached to DBSExpandableCard, its methods will
     * be called when toggler view was clicked.
     */
    public interface ExpandableCardToggleListener {
        /**
         * This method is called to notify you that, the toggler view is clicked
         *
         * @param isExpanded new state of container.
         */
        void onToggle(boolean isExpanded);
    }
}