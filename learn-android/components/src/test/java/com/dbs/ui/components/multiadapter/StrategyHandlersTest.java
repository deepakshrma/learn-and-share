package com.dbs.ui.components.multiadapter;

import com.dbs.ui.multiadapter.MultipleTypeAdapter;
import com.dbs.ui.multiadapter.GroupEvent;
import com.dbs.ui.multiadapter.GroupEventHandler;
import com.dbs.ui.multiadapter.StrategyHandlers;
import com.dbs.ui.multiadapter.UnknownEventHandler;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class StrategyHandlersTest {


    MultipleTypeAdapter groupAdapter;
    StrategyHandlers handlers;

    @Mock
    UnknownEventHandler handler;

    @Mock
    DummyEventHandler2 dummyEventHandler;

    static DummyEvent2 event2;

    @Before
    public void setUp() throws Exception {
        handlers = new StrategyHandlers(handler);
        handlers.registerHandler(dummyEventHandler);
        groupAdapter = new MultipleTypeAdapter();
        groupAdapter.setGroupEventHandler(handlers);
    }

    @BeforeClass
    public static void beforeClass() {
        event2 = mock(DummyEvent2.class);
    }

    @Test
    public void unknownEventDispatched_UnknownHandlerShouldBeCalled() {
        GroupEvent event = new GroupEvent() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
        handlers.handle(event);
        Mockito.verify(handler, Mockito.times(1)).handle(event);
    }

    @Test
    public void KnownEvent_shouldGetProperHandler() {
        DummyEvent event = new DummyEvent();
        DummyEventHandler handler = new DummyEventHandler();
        Assert.assertEquals(handler.getKey(), event.getClass().hashCode());
        handlers.registerHandler(handler);
        Assert.assertEquals(handlers.getHandlers().size(), 2);
        Assert.assertThat(handlers.getHandlers().get(event.getClass().hashCode()), CoreMatchers.instanceOf(DummyEventHandler.class));
    }

    private static class DummyEvent implements GroupEvent {
    }

    private static class DummyEvent2 implements GroupEvent {
    }

    private static class DummyEventHandler extends GroupEventHandler<DummyEvent> {
        DummyEventHandler() {
            super(DummyEvent.class);
        }

        @Override
        public void handle(DummyEvent event) {
            // no op
        }
    }

    private static class DummyEventHandler2 extends GroupEventHandler<DummyEvent2> {
        public DummyEventHandler2() {
            super(DummyEvent2.class);
        }

        @Override
        public void handle(DummyEvent2 event) {
            // no op
        }
    }


}