package com.dbs.replsdk.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlatformTest {

    Platform item1;
    String name = "platform_name";
    String conversation_id = "conversation_id";
    String session_id = "session_id";
    String version = "version";

    @Before
    public void setUp() {
        item1 = new Platform();
        item1.setName(name);
        item1.setVersion(version);
        item1.setConversation_id(conversation_id);
        item1.setSession_id(session_id);
    }

    @Test
    public void shouldGetName() {
        assertEquals(name, item1.getName());
    }

    @Test
    public void shouldGetVersion() {
        assertEquals(version, item1.getVersion());
    }

    @Test
    public void shouldGetConversion_id() {
        assertEquals(conversation_id, item1.getConversation_id());
    }

    @Test
    public void shouldGetSession() {
        assertEquals(session_id, item1.getSession_id());
    }

}
