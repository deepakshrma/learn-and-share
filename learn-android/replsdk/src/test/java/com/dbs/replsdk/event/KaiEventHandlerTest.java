package com.dbs.replsdk.event;

import com.dbs.replsdk.ui.events.ButtonEvent;
import com.dbs.replsdk.ui.events.KaiEvent;
import com.dbs.replsdk.ui.events.KaiEventHandler;
import com.dbs.replsdk.uimodel.Button;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class KaiEventHandlerTest {

    @Mock
    KaiEventHandler.RequestSender sender;

    private KaiEventHandler handler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        handler = new KaiEventHandler(Object.class, sender);
    }

    @Test
    public void handleNonNullEvent_ShouldInvokeSender() {

        Button button = new Button();
        button.setLabel("Test label");
        button.setPayload("Test payload");
        button.setType(Button.ButtonType.POSTBACK);

        KaiEvent event = new ButtonEvent(button);

        handler.handle(event);

        verify(sender).sendRequest(any());

    }
}