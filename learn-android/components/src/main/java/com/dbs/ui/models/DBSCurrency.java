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

/**
 * Created by deepak on 27/9/18.
 */

public class DBSCurrency {
    String code;
    String amount;
    boolean isSelected;

    public DBSCurrency(String code, String amount) {
        this(code, amount, false);
    }

    public DBSCurrency(String code, String amount, boolean isSelected) {
        this.code = code;
        this.amount = amount;
        this.isSelected = isSelected;
    }

    public String getCode() {
        return code;
    }

    public String getAmount() {
        return amount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public String toString() {
        return "DBSCurrency{" +
                "code='" + code + '\'' +
                ", amount='" + amount + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
