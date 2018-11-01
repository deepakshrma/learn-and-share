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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by deepak on 27/9/18.
 */

public class DBSAccount extends DBSAccountInfo {
    private List<DBSCurrency> currencies = new ArrayList<>();

    public DBSAccount(String accountName, String accountNumber, String accountBalance, String currency) {
        super(accountName, accountNumber, accountBalance, currency);
    }

    public List<DBSCurrency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(DBSCurrency... currencies) {
        setCurrencies(Arrays.asList(currencies));
    }

    public void setCurrencies(List<DBSCurrency> currencies) {
        this.currencies.clear();
        this.currencies.addAll(currencies);
    }

    public boolean isMultiCurrencyAccount() {
        return currencies != null && !currencies.isEmpty();
    }

    @Override
    public String toString() {
        return "DBSAccount{" +
                super.toString() +
                "currencies=" + currencies +
                '}';
    }
}
