package com.dbs.ui.components.multiadapter.dummy;

import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.dbs.ui.multiadapter.GroupItemFactory;
import com.xwray.groupie.Group;

public class DummyGroupFactory extends GroupItemFactory<DummyModel> {


    public DummyGroupFactory() {
        super(DummyModel.class);
    }

    @Override
    public Group create(DummyModel model, GroupEventDispatcher dispatcher) {
        return new DummyItem(event -> {

        });
    }
}
