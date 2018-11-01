package com.dbs.replsdk.arch;


import com.dbs.replsdk.uimodel.CardMessageContent;
import com.dbs.replsdk.uimodel.ContainerMessageContent;
import com.dbs.replsdk.uimodel.MessageContentType;
import com.dbs.replsdk.uimodel.TextMessageContent;
import com.dbs.replsdk.uimodel.UiMessageContent;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import static com.dbs.replsdk.MessageUtils.card;
import static com.dbs.replsdk.MessageUtils.carousel;
import static com.dbs.replsdk.MessageUtils.messageContent;
import static com.dbs.replsdk.MessageUtils.test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GsonWrapperTest {

    private Gson gson;

    @Before
    public void setup() {
        gson = GsonWrapper.getInstance();
    }

    @Test
    public void testParse() {
        UiMessageContent mess = gson.fromJson(messageContent, UiMessageContent.class);
        assertThat(mess, instanceOf(TextMessageContent.class));
        UiMessageContent cardM = gson.fromJson(card, UiMessageContent.class);
        assertThat(cardM, instanceOf(CardMessageContent.class));
        CardMessageContent.Payload cardPayload = (CardMessageContent.Payload) cardM.getPayload();
        assertThat(cardPayload.getTitle(), equalTo("Spending Patterns"));
        assertThat(cardM.getType(), equalTo(MessageContentType.CARD));
        UiMessageContent[] arr = gson.fromJson(test, UiMessageContent[].class);
        assertThat(arr.length, is(1));

        UiMessageContent carouselTest = gson.fromJson(carousel, UiMessageContent.class);
        assertThat(carouselTest, instanceOf(ContainerMessageContent.class));
        assertThat(carouselTest.getPayload(), instanceOf(ContainerMessageContent.Payload.class));
        assertThat(((ContainerMessageContent.Payload) carouselTest.getPayload()).getMode(), equalTo("LIST"));
        assertThat(((ContainerMessageContent.Payload) carouselTest.getPayload()).getCardMessageContents().size(), equalTo(5));

    }
}
