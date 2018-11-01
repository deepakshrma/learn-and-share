package com.dbs.replsdk.ui.events;

import com.dbs.replsdk.model.Request;
import com.dbs.replsdk.uimodel.Button;
import com.dbs.replsdk.uimodel.TextMessageContent;

public class ButtonEvent extends KaiEvent {

    private final Button button;

    public ButtonEvent(Button button) {
        this.button = button;
    }

    public boolean isHyperLinkEvent() {
        return button.getType() == Button.ButtonType.HYPERLINK;
    }

    public String getPayload() {
        return button.getPayload();
    }

    @Override
    public Request createRequest() {
        Request request = new Request();
        if (button.getType() == Button.ButtonType.POSTBACK) {
            request.setType("TEXT");
            request.setPayload(new TextMessageContent.Payload(button.getPayload()));
            request.createPersistedMessage(button.getLabel());
            return request;
        }
        return null;
    }
}
