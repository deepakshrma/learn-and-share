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
import android.support.annotation.Nullable;

import com.dbs.components.R;
import com.dbs.ui.components.DBSAccountInfoListItemView;
import com.dbs.ui.models.DBSAccount;
import com.dbs.ui.models.DBSCurrency;
import com.dbs.ui.multiadapter.DispatchingItem;
import com.dbs.ui.multiadapter.GroupEvent;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.dbs.ui.multiadapter.GroupItemFactory;
import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.ExpandableItem;
import com.xwray.groupie.Group;
import com.xwray.groupie.ViewHolder;

public class DBSAccountInfoItem extends DispatchingItem implements ExpandableItem {
    private final DBSAccount account;
    private ExpandableGroup group;

    DBSAccountInfoItem(DBSAccount account, GroupEventDispatcher dispatcher) {
        this.account = account;
        this.mDispatcher = dispatcher;
    }

    @Override
    public void bind(@NonNull ViewHolder holder, int position) {
        DBSAccountInfoListItemView accountInfoListItemView = (DBSAccountInfoListItemView) holder.getRoot();
        accountInfoListItemView.setAccountDetails(account);
        if (group != null && account.isMultiCurrencyAccount()) {
            accountInfoListItemView.setRightNavImage(R.drawable.ic_expand_icon);
            //Set right ic_expand_icon, May be remove, once use have all icons
            accountInfoListItemView.getRightNavImageView().animate().rotation(-90f).start();
            accountInfoListItemView.setOnClickListener(v -> {
                accountInfoListItemView.getRightNavImageView()
                        .animate()
                        .rotation(group.isExpanded() ? -90f : 0f).start();
                group.onToggleExpanded();
            });
        } else {
            accountInfoListItemView.setOnClickListener(v -> dispatch(new DBSAccountClick(account)));
        }
    }

    @Override
    public int getLayout() {
        return R.layout.item_dbs_m_account;
    }

    @Override
    public void setExpandableGroup(@NonNull ExpandableGroup onToggleListener) {
        this.group = onToggleListener;
    }

    static class DBSAccountClick implements GroupEvent {
        DBSAccount account;

        DBSAccountClick(DBSAccount account) {
            this.account = account;
        }
    }

    static class DBSAccountItemFactory extends GroupItemFactory<DBSAccount> {

        DBSAccountItemFactory() {
            super(DBSAccount.class);
        }

        @Override
        public Group create(DBSAccount account, @Nullable GroupEventDispatcher dispatcher) {
            DBSAccountInfoItem item = new DBSAccountInfoItem(account, dispatcher);
            if (account.isMultiCurrencyAccount()) {
                ExpandableGroup group = new ExpandableGroup(item);
                for (DBSCurrency currency : account.getCurrencies()) {
                    group.add(new DBSCurrencyItem(currency, account, dispatcher));
                }
                return group;
            } else {
                return item;
            }
        }
    }
}
