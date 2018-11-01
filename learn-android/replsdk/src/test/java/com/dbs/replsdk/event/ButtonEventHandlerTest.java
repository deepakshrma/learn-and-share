package com.dbs.replsdk.event;

import com.dbs.replsdk.ui.events.ButtonEvent;
import com.dbs.replsdk.ui.events.ButtonEventHandler;
import com.dbs.replsdk.ui.events.HyperLinkOpener;
import com.dbs.replsdk.ui.events.KaiEvent;
import com.dbs.replsdk.ui.events.KaiEventHandler;
import com.dbs.replsdk.uimodel.Button;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class ButtonEventHandlerTest {


    @Mock
    KaiEventHandler.RequestSender sender;

    @Mock
    HyperLinkOpener linkOpener;

    private ButtonEventHandler handler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        handler = new ButtonEventHandler(sender).withLinkOpener(linkOpener);
    }

    @Test
    public void handleLink_ShouldInvokeLinkOpener() {
        Button button = new Button();
        button.setLabel("Test label");
        button.setPayload("link");
        button.setType(Button.ButtonType.HYPERLINK);
        KaiEvent event = new ButtonEvent(button);
        handler.handle(event);
        verifyZeroInteractions(sender);
        verify(linkOpener).openLink("link");
    }
}