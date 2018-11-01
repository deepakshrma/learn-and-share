package com.dbs.replsdk.model;

import com.dbs.replsdk.uimodel.TextMessageContent;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestTest {

    String payloadText = "PalyoadMessagetext";
    String messageText = "Messagetext";
    Request request;
    Payload payload;
    private RequestContext context;
    private String type;
    private TextMessageContent persistedMessage;

    @Before
    public void setUp() {
        context = new RequestContext();
        request = new Request();
        payload = new TextMessageContent.Payload(payloadText);
        request.setType(type);
        request.setPayload(payload);
        request.createPersistedMessage(messageText);
        request.setContext(context);
    }


    @Test
    public void shouldGetProperType() {
        assertEquals(type, request.getType());
    }

    @Test
    public void shouldGetProperMessage() {
        assertEquals(messageText, request.getPersistedMessage().getPayload().getText());
    }

    @Test
    public void shouldGetProperPayload() {
        assertEquals(payload, request.getPayload());
    }

    @Test
    public void shouldGetProperContext() {
        assertEquals(context, request.getContext());
    }

}
