package com.dbs.ui.components.multiadapter;

import android.content.Context;
import android.view.View;

import com.dbs.ui.components.multiadapter.dummy.DummyBindableItem;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.xwray.groupie.ViewHolder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DispatchingBindableItemTest {

    @Mock
    private GroupEventDispatcher dispatcher;

    @Mock
    private Context context;

    @Test
    public void withNonNullDispatcher_WhenEventDispatched_DispatcherShouldCallDispatch() {
        DummyBindableItem item = new DummyBindableItem(dispatcher);
        Assert.assertNotNull(item);
        item.bind(new ViewHolder(new View(context)), 0);
        Mockito.verify(dispatcher).dispatch(null);
        Mockito.verifyNoMoreInteractions(dispatcher);


    }

    @Test
    public void withNullDispatcher_WhenEventDispatched_DispatcherNotCallDispatch() {
        NullDispatcherBindableItem nullDispatcherItem = new NullDispatcherBindableItem(dispatcher);
        nullDispatcherItem.bind(new ViewHolder(new View(context)), 0);
        Mockito.verify(dispatcher, Mockito.times(0)).dispatch(null);
        Mockito.verifyZeroInteractions(dispatcher);
    }


    private static class NullDispatcherBindableItem extends DummyBindableItem {
        NullDispatcherBindableItem(GroupEventDispatcher dispatcher) {
            super(null);
        }
    }
}