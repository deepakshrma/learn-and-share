package com.dbs.replsdk.event;

import com.dbs.replsdk.ui.events.KaiEventHandler;
import com.dbs.replsdk.ui.events.TextEventHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;

public class TextEventHandlerTest {

    @Mock
    KaiEventHandler.RequestSender sender;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void nonsenseTest() {
        TextEventHandler handler = new TextEventHandler(sender);
        assertNotNull(handler);
    }
}