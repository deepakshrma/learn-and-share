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

package com.dbs.ui.components.ac;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.dbs.components.R;
import com.dbs.ui.models.DBSAccount;
import com.dbs.ui.models.DBSCurrency;
import com.dbs.ui.multiadapter.GroupEventHandler;
import com.dbs.ui.multiadapter.MultipleTypeAdapter;
import com.dbs.ui.multiadapter.StrategyHandlers;

import java.util.List;


/**
 * DBSAccountListView to create list of accounts with multi-currency
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-10-01
 */
public class DBSAccountListView extends RelativeLayout {
    MultipleTypeAdapter multipleTypeAdapter;

    public DBSAccountListView(Context context) {
        this(context, null);
    }

    public DBSAccountListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DBSAccountListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_dbs_m_account_list, this);
        init(view);
    }

    /**
     * Will update and replace all accounts with new accounts
     *
     * @param accounts
     */
    public void updateAccounts(List<DBSAccount> accounts) {
        multipleTypeAdapter.updateListModel(accounts);
    }

    /**
     * Set OnAccountSelectListener, when user click on account it will call back.
     * If account is multi-currency account, it will call on currency click not on account item click
     *
     * @param accountSelectLister
     */

    public void setOnAccountSelectLister(OnAccountSelectListener accountSelectLister) {
        StrategyHandlers handlers = new StrategyHandlers();
        handlers.registerHandler(new CurrencyEventHandler(accountSelectLister));
        handlers.registerHandler(new AccountEventHandler(accountSelectLister));
        multipleTypeAdapter.setGroupEventHandler(handlers);
    }

    /**
     * OnAccountSelectListener to assign on account selection callback with account and currency
     * [account, currency(null if it is not multi-currency account)]
     * <p>
     * DBSAccount account, DBSCurrency currency
     */
    public interface OnAccountSelectListener {
        void onAccountSelect(DBSAccount account, DBSCurrency currency);
    }

    private void init(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Drawable itemDecoratorDrawable = ContextCompat.getDrawable(getContext(), R.drawable.rc_view_item_decorator);
        if (itemDecoratorDrawable != null) {
            DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(itemDecoratorDrawable);
            recyclerView.addItemDecoration(itemDecorator);
        }
        multipleTypeAdapter = new MultipleTypeAdapter();
        multipleTypeAdapter.registerFactory(new DBSAccountInfoItem.DBSAccountItemFactory());
        recyclerView.setAdapter(multipleTypeAdapter);
    }

    private static class AccountEventHandler extends GroupEventHandler<DBSAccountInfoItem.DBSAccountClick> {
        OnAccountSelectListener accountSelectListener;

        AccountEventHandler(OnAccountSelectListener accountSelectListener) {
            super(DBSAccountInfoItem.DBSAccountClick.class);
            this.accountSelectListener = accountSelectListener;
        }

        @Override
        public void handle(DBSAccountInfoItem.DBSAccountClick event) {
            if (accountSelectListener != null) {
                accountSelectListener.onAccountSelect(event.account, null);
            }
        }
    }

    private static class CurrencyEventHandler extends GroupEventHandler<DBSCurrencyItem.CurrencyClick> {
        OnAccountSelectListener accountSelectListener;

        CurrencyEventHandler(OnAccountSelectListener accountSelectListener) {
            super(DBSCurrencyItem.CurrencyClick.class);
            this.accountSelectListener = accountSelectListener;
        }

        @Override
        public void handle(DBSCurrencyItem.CurrencyClick event) {
            if (accountSelectListener != null) {
                accountSelectListener.onAccountSelect(event.account, event.currency);
            }
        }
    }
}
