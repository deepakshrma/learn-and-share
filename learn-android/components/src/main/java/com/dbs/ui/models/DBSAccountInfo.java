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

package com.dbs.ui.models;

import android.graphics.drawable.Drawable;

/**
 * DBSAccountInfo is data which can be set to {@link com.dbs.ui.components.DBSAccountInfoListItemView}
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */
public class DBSAccountInfo {
    private String accountName;
    private String accountNumber;
    private String accountBalance;
    private Drawable accountImage;
    private Drawable rightNavImage;
    private String currency;

    public DBSAccountInfo(String accountName,
                          String accountNumber,
                          String accountBalance,
                          String currency,
                          Drawable accountImage,
                          Drawable rightNavImage) {
        this(accountName, accountNumber, accountBalance, currency);
        this.accountImage = accountImage;
        this.rightNavImage = rightNavImage;
    }

    public DBSAccountInfo(String accountName,
                          String accountNumber,
                          String accountBalance,
                          String currency) {
        this(accountName, accountNumber, accountBalance);
        this.currency = currency;
    }

    public DBSAccountInfo(String accountName,
                          String accountNumber,
                          String accountBalance) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Drawable getAccountImage() {
        return accountImage;
    }

    public void setAccountImage(Drawable accountImage) {
        this.accountImage = accountImage;
    }

    public Drawable getRightNavImage() {
        return rightNavImage;
    }

    public void setRightNavImage(Drawable rightNavImage) {
        this.rightNavImage = rightNavImage;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "DBSAccountInfo{" +
                "accountName='" + accountName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountBalance='" + accountBalance + '\'' +
                ", accountImage=" + accountImage +
                ", rightNavImage=" + rightNavImage +
                ", currency='" + currency + '\'' +
                '}';
    }
}
