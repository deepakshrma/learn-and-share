package com.dbs.ui.components.multiadapter.dummy;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;

import com.dbs.ui.multiadapter.DispatchingBindableItem;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.xwray.groupie.ViewHolder;

public class DummyBindableItem extends DispatchingBindableItem {


    public DummyBindableItem(GroupEventDispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }


    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        dispatch(null);
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void bind(@NonNull ViewDataBinding viewBinding, int position) {
        dispatch(null);
    }
}
