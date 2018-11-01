package com.dbs.replsdk.event;

import com.dbs.replsdk.model.Request;
import com.dbs.replsdk.ui.events.ButtonEvent;
import com.dbs.replsdk.uimodel.Button;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ButtonEventTest {

    ButtonEvent event1;
    ButtonEvent event2;


    @Before
    public void setUp() {
    }

    @Test
    public void createRequest_ShouldNotBeNull() {

        Button button = new Button();
        button.setLabel("Test label");
        button.setPayload("Test payload");
        button.setType(Button.ButtonType.POSTBACK);


        event1 = new ButtonEvent(button);

        Request request = event1.createRequest();

        assertNotNull(request);

        assertEquals("TEXT", request.getType());

        assertEquals("Test label",
                request.getPersistedMessage().getPayload().getText());

    }


    @Test
    public void createRequest_ShouldBeNull() {

        Button button = new Button();
        button.setLabel("Test label");
        button.setPayload("Test payload");
        button.setType(Button.ButtonType.HYPERLINK);

        event2 = new ButtonEvent(button);

        Request request = event2.createRequest();

        assertNull(request);


    }


}
