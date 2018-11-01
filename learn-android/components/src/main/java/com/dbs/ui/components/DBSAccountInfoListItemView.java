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
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dbs.components.R;
import com.dbs.ui.BaseView;
import com.dbs.ui.models.DBSAccountInfo;
import com.dbs.ui.utils.StringUtils;

/**
 * The DBSAccountInfoListItemView is a component which displays basic account info for a list of accounts
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSAccountInfoListItemView extends BaseView {
    private AppCompatTextView accountNameTextView;
    private AppCompatTextView accountBalanceTextView;
    private AppCompatTextView accountNumberTextView;
    private AppCompatTextView currencyLabelTextView;
    private LinearLayout accountDescriptionLayout;
    private DBSAvatarView accountImageView;
    private ImageView rightNavImageView;

    public DBSAccountInfoListItemView(@NonNull Context context) {
        super(context);
    }

    public DBSAccountInfoListItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSAccountInfoListItemView(@NonNull Context context,
                                      @Nullable AttributeSet attrs,
                                      int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Set Account details.
     *
     * @see DBSAccountInfo
     */
    public void setAccountDetails(DBSAccountInfo accountDetails) {
        setAccountName(accountDetails.getAccountName());
        setAccountNumber(accountDetails.getAccountNumber());
        setAccountBalance(accountDetails.getAccountBalance());
        setRightNavImage(accountDetails.getRightNavImage());
        setAccountImage(accountDetails);
        setCurrencyLabel(accountDetails.getCurrency());
    }

    /**
     * Returns Account name text view.
     */
    public AppCompatTextView getAccountNameTextView() {
        return accountNameTextView;
    }

    /**
     * Returns Account balance text view.
     */
    public AppCompatTextView getAccountBalanceTextView() {
        return accountBalanceTextView;
    }

    /**
     * Returns Account number text view.
     */
    public AppCompatTextView getAccountNumberTextView() {
        return accountNumberTextView;
    }

    /**
     * Returns Account image view.
     */
    public ImageView getAccountImageView() {
        return accountImageView;
    }

    /**
     * Returns navigation image view on right.
     */
    public ImageView getRightNavImageView() {
        return rightNavImageView;
    }

    /**
     * Returns Account currency label text view.
     */
    public AppCompatTextView getCurrencyLabelTextView() {
        return currencyLabelTextView;
    }

    public void setRightNavImage(Drawable rightNavImage) {
        if (rightNavImage == null) {
            rightNavImageView.setVisibility(GONE);
        } else {
            rightNavImageView.setVisibility(VISIBLE);
            rightNavImageView.setImageDrawable(rightNavImage);
        }
    }

    public void setRightNavImage(@DrawableRes int rightNavImageId) {
        Drawable rightIcon = ContextCompat.getDrawable(getContext(), rightNavImageId);
        setRightNavImage(rightIcon);
    }

    private void setCurrencyLabel(String currency) {
        if (StringUtils.isEmpty(currency)) {
            currencyLabelTextView.setVisibility(GONE);
        } else {
            currencyLabelTextView.setVisibility(VISIBLE);
            currencyLabelTextView.setText(currency);
        }
    }

    private void setAccountImage(DBSAccountInfo accountDetails) {
        if (accountDetails.getAccountImage() != null) {
            accountImageView.setImageDrawable(accountDetails.getAccountImage());
        } else {
            accountImageView.setName(accountDetails.getAccountName());
        }
    }

    private void setAccountName(String accountName) {
        if (!StringUtils.isEmpty(accountName)) {
            accountNameTextView.setText(accountName);
        }
    }

    private void setAccountBalance(String accountBalance) {
        if (StringUtils.isEmpty(accountBalance)) {
            accountBalanceTextView.setVisibility(GONE);
        } else {
            accountBalanceTextView.setVisibility(VISIBLE);
            accountBalanceTextView.setText(accountBalance);
        }
        setAccountDescriptionVisibility();
    }

    private void setAccountDescriptionVisibility() {
        if (accountBalanceTextView.getVisibility() == GONE && accountNumberTextView.getVisibility() == GONE) {
            accountDescriptionLayout.setVisibility(GONE);
        } else {
            accountDescriptionLayout.setVisibility(VISIBLE);
        }
    }

    private void setAccountNumber(String accountNumber) {
        if (!StringUtils.isEmpty(accountNumber)) {
            accountNumberTextView.setVisibility(VISIBLE);
            accountNumberTextView.setText(accountNumber);
        } else {
            accountNumberTextView.setVisibility(GONE);
        }
        setAccountDescriptionVisibility();
    }

    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        accountNameTextView = view.findViewById(R.id.account_name);
        accountBalanceTextView = view.findViewById(R.id.account_balance);
        accountNumberTextView = view.findViewById(R.id.account_number);
        accountImageView = view.findViewById(R.id.account_image);
        rightNavImageView = view.findViewById(R.id.right_nav_image);
        accountDescriptionLayout = view.findViewById(R.id.account_description_layout);
        currencyLabelTextView = view.findViewById(R.id.currency_label);
    }

    @Override
    protected int provideLayoutId(String type) {
        return R.layout.dbs_account_info_list_item;
    }
}
