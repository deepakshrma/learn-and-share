package com.dbs.ui.components.multiadapter.dummy;

import android.support.annotation.NonNull;

import com.dbs.ui.multiadapter.DispatchingItem;
import com.dbs.ui.multiadapter.GroupEvent;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.xwray.groupie.ViewHolder;

public class DummyItem extends DispatchingItem {


    public DummyItem(GroupEventDispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    @Override
    public int getLayout() {
        return 0;
    }


    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        dispatch(null);
    }

}
