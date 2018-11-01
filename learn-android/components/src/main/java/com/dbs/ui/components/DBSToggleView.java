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
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;

import com.dbs.components.R;
import com.dbs.ui.BaseView;

import static com.dbs.ui.utils.ViewUtils.setTextIfNotNull;

/**
 * The DBSToggleView is a component to create a custom switch.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */

public class DBSToggleView extends BaseView implements CompoundButton.OnCheckedChangeListener {
    private SwitchCompat toggle;
    private AppCompatTextView title;
    private AppCompatTextView description;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    private boolean toggledByEndUser = true;

    public DBSToggleView(@NonNull Context context) {
        super(context);
    }

    public DBSToggleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSToggleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Sets the listener for change in state of toggle
     */
    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        onCheckedChangeListener = listener;
    }

    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        TypedArray viewCustomAttributes = view.getContext().obtainStyledAttributes(attrs, R.styleable.DBSToggleView);
        toggle = view.findViewById(R.id.toggle_switch);
        title = view.findViewById(R.id.toggle_title);
        description = view.findViewById(R.id.toggle_description);

        if (viewCustomAttributes != null) {
            String titleText = viewCustomAttributes.getString(R.styleable.DBSToggleView_dbs_title);
            String descriptionText = viewCustomAttributes.getString(R.styleable.DBSToggleView_dbs_description);
            boolean checked = viewCustomAttributes.getBoolean(R.styleable.DBSToggleView_dbs_toggleSwitchChecked, false);

            title.setText(titleText);
            setDescription(descriptionText);
            toggle.setChecked(checked);

            int titleStyle = viewCustomAttributes.getResourceId(R.styleable.DBSToggleView_dbs_toggleTitleStyle, View.NO_ID);
            int descriptionStyle = viewCustomAttributes.getResourceId(R.styleable.DBSToggleView_dbs_toggleDescriptionStyle, View.NO_ID);
            ColorStateList dbsSwitchTrackTint = getColorStateList(viewCustomAttributes, R.styleable.DBSToggleView_dbs_toggleSwitchTrackTint, R.color.toggle_track);
            ColorStateList dbsSwitchThumbTint = getColorStateList(viewCustomAttributes, R.styleable.DBSToggleView_dbs_toggleSwitchThumbTint, R.color.toggle_thumb);

            setTitleStyle(titleStyle);
            setDescriptionStyle(descriptionStyle);

            toggle.setThumbTintList(dbsSwitchThumbTint);
            toggle.setTrackTintList(dbsSwitchTrackTint);
            viewCustomAttributes.recycle();
        }
        toggle.setOnCheckedChangeListener(this);
    }

    /**
     * Sets Title for toggle
     */
    public void setTitle(String text) {
        title.setText(text);
    }

    /**
     * Returns if the toggle is checked or unchecked
     */
    public boolean isChecked() {
        return toggle.isChecked();
    }

    /**
     * Sets if the toggle is checked or unchecked
     */
    public void setChecked(boolean checked) {
        if (toggle.isChecked() == checked)
            return;
        toggledByEndUser = false;
        toggle.setChecked(checked);
    }

    /**
     * Sets Description for toggle
     */
    public void setDescription(String text) {
        setTextIfNotNull(description, text);
    }

    /**
     * Sets Title style for toggle
     */
    public void setTitleStyle(int titleStyle) {
        TextViewCompat.setTextAppearance(title, titleStyle);
    }

    /**
     * Sets Description style for toggle
     */
    public void setDescriptionStyle(int descriptionStyle) {
        TextViewCompat.setTextAppearance(description, descriptionStyle);
    }

    /**
     * Use this method only to style the component not to get the internal state
     *
     * @return android toggle component used internally
     */
    public SwitchCompat getToggle() {
        return toggle;
    }

    /**
     * @inheritDoc Store a state which says the toggle was interacted by end user.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (onCheckedChangeListener != null && toggledByEndUser)
            onCheckedChangeListener.onCheckedChanged(buttonView, isChecked);
        resetToggledByEndUserForFutureInteraction();
    }

    private ColorStateList getColorStateList(TypedArray viewCustomAttributes, int colorStateListId, int defaultColorStateListId) {
        ColorStateList colorStateList = viewCustomAttributes.getColorStateList(colorStateListId);
        return colorStateList != null ? colorStateList : ContextCompat.getColorStateList(getContext(), defaultColorStateListId);
    }

    @Override
    protected int provideLayoutId(String type) {
        return R.layout.dbs_toggle_view;
    }

    private void resetToggledByEndUserForFutureInteraction() {
        toggledByEndUser = true;
    }
}
