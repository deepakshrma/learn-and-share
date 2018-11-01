package com.dbs.replsdk.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChatResponseTest {

    ResponseContext context;
    Status status;
    ChatResponse response;

    @Before
    public void setUp() {
        response = new ChatResponse();
        context = new ResponseContext();
        status = new Status();
        response.setContext(context);
        response.setStatus(status);
    }


    @Test
    public void shouldGetContext() {
        assertEquals(context, response.getContext());
    }

    @Test
    public void shouldGetStatus() {
        assertEquals(status, response.getStatus());
    }
}
