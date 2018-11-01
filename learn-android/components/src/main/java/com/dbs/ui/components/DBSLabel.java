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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.dbs.components.R;
import com.dbs.ui.BaseView;

/**
 * The DBSLabel component is to create view holding data on 3 lines
 * Title
 * Value (with/without Prefix)
 * Description
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSLabel extends BaseView {

    private AppCompatTextView title;
    private AppCompatTextView value;
    private AppCompatTextView prefix;
    private AppCompatTextView description;

    public DBSLabel(@NonNull Context context) {
        super(context);
    }

    public DBSLabel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSLabel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        TypedArray viewCustomAttributes = view.getContext().obtainStyledAttributes(attrs, R.styleable.DBSLabel);

        title = view.findViewById(R.id.title);
        value = view.findViewById(R.id.value);
        prefix = view.findViewById(R.id.prefix);
        description = view.findViewById(R.id.desc);

        if (viewCustomAttributes != null) {
            String titleText = viewCustomAttributes.getString(R.styleable.DBSLabel_dbs_title);
            String prefixText = viewCustomAttributes.getString(R.styleable.DBSLabel_dbs_labelPrefix);
            String valueText = viewCustomAttributes.getString(R.styleable.DBSLabel_dbs_labelValue);
            String descText = viewCustomAttributes.getString(R.styleable.DBSLabel_dbs_labelDescription);

            title.setText(titleText);
            value.setText(valueText);

            if (prefixText != null) {
                setPrefix(prefixText);
            }
            if (descText != null) {
                setDescription(descText);
            }

            int titleStyle = viewCustomAttributes.getResourceId(R.styleable.DBSLabel_dbs_labelTitleStyle, View.NO_ID);
            int valueStyle = viewCustomAttributes.getResourceId(R.styleable.DBSLabel_dbs_labelValueStyle, View.NO_ID);
            int prefixStyle = viewCustomAttributes.getResourceId(R.styleable.DBSLabel_dbs_labelPrefixStyle, View.NO_ID);
            int descStyle = viewCustomAttributes.getResourceId(R.styleable.DBSLabel_dbs_labelDescriptionStyle, View.NO_ID);

            setTitleStyle(titleStyle);
            setValueStyle(valueStyle);
            setPrefixStyle(prefixStyle);
            setDescriptionStyle(descStyle);

            boolean disableDefPadding = viewCustomAttributes.getBoolean(R.styleable.DBSLabel_dbs_labelDisableDefaultPadding, false);
            if (!disableDefPadding) {
                view.setPadding(getResources().getDimensionPixelOffset(R.dimen.medium_padding),
                        getResources().getDimensionPixelOffset(R.dimen.medium_padding),
                        getResources().getDimensionPixelOffset(R.dimen.medium_padding),
                        getResources().getDimensionPixelOffset(R.dimen.x_small_padding));
            }
            viewCustomAttributes.recycle();
        }
    }

    /**
     * Set style for title
     * Ex.
     * <code>dbsLabel.setTitleStyle(style);</code>
     * @param titleStyle style for title
     */
    public void setTitleStyle(int titleStyle) {
        TextViewCompat.setTextAppearance(title, titleStyle);
    }

    /**
     * Set style for Prefix
     * Ex.
     * <code>dbsLabel.setPrefixStyle(style);</code>
     * @param prefixStyle style for prefix
     */
    public void setPrefixStyle(int prefixStyle) {
        TextViewCompat.setTextAppearance(prefix, prefixStyle);
    }

    /**
     * Set style for Value
     * Ex.
     * <code>dbsLabel.setValueStyle(style);</code>
     * @param valueStyle style for value
     */
    public void setValueStyle(int valueStyle) {
        TextViewCompat.setTextAppearance(value, valueStyle);
    }

    /**
     * Set style for Description
     * Ex.
     * <code>dbsLabel.setDescriptionStyle(style);</code>
     * @param descriptionStyle style for description
     */
    public void setDescriptionStyle(int descriptionStyle) {
        TextViewCompat.setTextAppearance(description, descriptionStyle);
    }

    /**
     * Set Title text
     * Ex.
     * <code>dbsLabel.setTitle("Title");</code>
     * @param text text to be set for title
     */
    public void setTitle(String text) {
        title.setText(text);
    }

    /**
     * Set Prefix text
     * Ex.
     * <code>dbsLabel.setPrefix("Prefix");</code>
     * @param text text to be set for prefix
     */
    public void setPrefix(String text) {
        prefix.setText(text);
        if (!TextUtils.isEmpty(text)) {
            prefix.setVisibility(VISIBLE);
        }
    }

    /**
     * Set Description text
     * Ex.
     * <code>dbsLabel.setDescription("Description");</code>
     * @param text text to be set for description
     */
    public void setDescription(String text) {
        description.setText(text);
        if (!TextUtils.isEmpty(text)) {
            description.setVisibility(VISIBLE);
        }
    }

    /**
     * Set Value text
     * Ex.
     * <code>dbsLabel.setValue("Value");</code>
     * @param text text to be set for description
     */
    public void setValue(String text) {
        value.setText(text);
    }

    @Override
    protected int provideLayoutId(String type) {
        return R.layout.dbs_label;
    }
}
