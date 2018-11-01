package com.dbs.replsdk.event;

import com.dbs.replsdk.model.Request;
import com.dbs.replsdk.ui.events.TextEvent;
import com.dbs.replsdk.uimodel.TextMessageContent;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TextEventTest {

    @Before
    public void setUp() {
    }

    @Test
    public void createRequest_ShouldNotBeNull() {

        TextEvent event1 = new TextEvent("Test");

        Request request = event1.createRequest();

        assertNotNull(request);

        assertEquals("TEXT", request.getType());

        assertEquals("Test",
                request.getPersistedMessage().getPayload().getText());

        assertThat(request.getPayload(), instanceOf(TextMessageContent.Payload.class));

    }

}
