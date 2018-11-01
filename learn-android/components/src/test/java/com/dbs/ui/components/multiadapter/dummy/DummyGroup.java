package com.dbs.ui.components.multiadapter.dummy;

import android.support.annotation.NonNull;

import com.xwray.groupie.Group;
import com.xwray.groupie.GroupDataObserver;
import com.xwray.groupie.Item;

public abstract class DummyGroup implements Group {

    @NonNull
    @Override
    public Item getItem(int position) {
        return null;
    }

    @Override
    public int getPosition(@NonNull Item item) {
        return 0;
    }

    @Override
    public void registerGroupDataObserver(@NonNull GroupDataObserver groupDataObserver) {

    }

    @Override
    public void unregisterGroupDataObserver(@NonNull GroupDataObserver groupDataObserver) {

    }
}
