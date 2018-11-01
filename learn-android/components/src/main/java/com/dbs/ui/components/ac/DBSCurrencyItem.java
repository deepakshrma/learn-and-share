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

import android.support.annotation.NonNull;
import android.support.text.emoji.widget.EmojiAppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dbs.components.R;
import com.dbs.ui.models.DBSAccount;
import com.dbs.ui.models.DBSCurrency;
import com.dbs.ui.multiadapter.DispatchingItem;
import com.dbs.ui.multiadapter.GroupEvent;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.xwray.groupie.ViewHolder;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.dbs.ui.utils.Utils.currencyToEmoFlagChar;

/**
 * Created by deepak on 27/9/18.
 */

public class DBSCurrencyItem extends DispatchingItem {
    final DBSCurrency currency;
    final DBSAccount account;

    DBSCurrencyItem(DBSCurrency currency, DBSAccount account, GroupEventDispatcher dispatch) {
        this.currency = currency;
        this.account = account;
        this.mDispatcher = dispatch;
    }

    @Override
    public void bind(@NonNull ViewHolder holder, int position) {
        View view = holder.getRoot();
        EmojiAppCompatTextView flagView = view.findViewById(R.id.currency_flag);
        TextView currencyText = view.findViewById(R.id.currency_text);
        TextView currencyLbl = view.findViewById(R.id.currency_lbl);
        TextView currencyAmt = view.findViewById(R.id.currency_amt);
        ImageView isSelectedIcon = view.findViewById(R.id.is_selected);
        flagView.setText(currencyToEmoFlagChar(currency.getCode()));
        currencyText.setText(currency.getCode());
        currencyLbl.setText(currency.getCode());
        currencyAmt.setText(String.valueOf(currency.getAmount()));
        isSelectedIcon.setVisibility(currency.isSelected() ? VISIBLE : INVISIBLE);
        view.setOnClickListener(v -> dispatch(new CurrencyClick(position, currency, account)));
    }

    @Override
    public int getLayout() {
        return R.layout.view_dbs_m_account_currency;
    }

    static class CurrencyClick implements GroupEvent {
        int position;
        DBSCurrency currency;
        DBSAccount account;

        CurrencyClick(int position, DBSCurrency currency, DBSAccount account) {
            this.position = position;
            this.currency = currency;
            this.account = account;
        }

    }
}
