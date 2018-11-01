package com.dbs.replsdk.model;

import com.dbs.replsdk.uimodel.TextMessageContent;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuickReplyTest {

    QuickReply item;
    QuickReply item1;
    QuickReply item2;

    private String text1 = "TEXT";
    private String payload1 = "payload1";
    private String type1 = "type1";

    private String text2 = "LINK";
    private String payload2 = "payload2";
    private String type2 = "type2";

    Request request;

    @Before
    public void setUp() {
        item = new QuickReply(text1, payload1, type1);
        item1 = new QuickReply(text1, payload1, type1);
        item2 = new QuickReply(text2, payload2, type2);

        request = new Request();
        request.setType(text1);
        request.setPayload(new TextMessageContent.Payload(payload1));
        request.createPersistedMessage(text1);
    }


    @Test
    public void shouldGetText() {
        assertEquals(text1, item.getText());
    }

    @Test
    public void shouldGetPayload() {
        assertEquals(payload1, item.getPayload());
    }

    @Test
    public void shouldGetType() {
        assertEquals(type1, item.getType());
    }

    @Test
    public void shouldAssertSameItem() {
        assertEquals(true, item.areItemsTheSame(item1));
    }


    @Test
    public void shouldAssertNotSameItem() {
        assertEquals(false, item.areItemsTheSame(item2));
    }

    @Test
    public void shouldAssertAllContentSame() {
        assertEquals(true, item.areContentsTheSame(item1));
    }

    @Test
    public void shouldAssertAllContentNotSame() {
        assertEquals(false, item.areContentsTheSame(item2));
    }

    @Test
    public void shouldAssertRequestTypeProper(){
        Request req = item.createRequest();
        assertEquals(request.getType(), req.getType());
    }

    @Test
    public void shouldAssertRequestMessageProper(){
        Request req = item.createRequest();
        assertEquals(request.getPersistedMessage().getPayload().getText(), req.getPersistedMessage().getPayload().getText());
    }
}
