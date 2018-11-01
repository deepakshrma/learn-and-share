package com.dbs.ui.components.multiadapter;

import android.content.Context;
import android.view.View;

import com.dbs.ui.components.multiadapter.dummy.DummyItem;
import com.dbs.ui.multiadapter.DispatchingItem;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.xwray.groupie.ViewHolder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

//import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class DispatchingItemTest {

    @Mock
    GroupEventDispatcher dispatcher;

    @Mock
    Context context;

    @Test
    public void withNonNullDispatcher_WhenEventDispatched_DispatcherShouldCallDispatch() {
        DispatchingItem item = new DummyItem(dispatcher);
        Assert.assertNotNull(item);
        item.bind(new ViewHolder(new View(context)), 0);
        Mockito.verify(dispatcher).dispatch(null);
        Mockito.verifyNoMoreInteractions(dispatcher);


    }

    @Test
    public void withNullDispatcher_WhenEventDispatched_DispatcherNotCallDispatch() {
        NullDispatcherItem nullDispatcherItem = new NullDispatcherItem(dispatcher);
        nullDispatcherItem.bind(new ViewHolder(new View(context)), 0);
        Mockito.verify(dispatcher, Mockito.times(0)).dispatch(null);
        Mockito.verifyZeroInteractions(dispatcher);
    }


    private static class NullDispatcherItem extends DummyItem {
        NullDispatcherItem(GroupEventDispatcher dispatcher) {
            super(null);
        }
    }
}