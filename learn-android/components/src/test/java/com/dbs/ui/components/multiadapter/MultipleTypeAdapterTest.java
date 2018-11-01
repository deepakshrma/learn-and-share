package com.dbs.ui.components.multiadapter;

import com.dbs.ui.components.multiadapter.dummy.DummyDataObserver;
import com.dbs.ui.components.multiadapter.dummy.DummyGroupFactory;
import com.dbs.ui.components.multiadapter.dummy.DummyModel;
import com.dbs.ui.multiadapter.MultipleTypeAdapter;
import com.thoughtworks.xstream.mapper.Mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class MultipleTypeAdapterTest {

    MultipleTypeAdapter groupAdapter;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        groupAdapter = new MultipleTypeAdapter();
        groupAdapter.registerFactory(new DummyGroupFactory());
    }

    @Test
    public void withRegisteredFactory_shouldRenderRegisteredTypeOnly() {
        ArrayList<DummyModel> models = new ArrayList<>();
        models.add(new DummyModel());
        models.add(new DummyModel());


        exception.expect(NullPointerException.class);
        groupAdapter.updateListModel(models);
        Mockito.verify(groupAdapter).notifyItemRangeInserted(0, 2);
        verifyNoMoreInteractions(groupAdapter);
        Assert.assertEquals(2,groupAdapter.getItemCount());
    }



}
