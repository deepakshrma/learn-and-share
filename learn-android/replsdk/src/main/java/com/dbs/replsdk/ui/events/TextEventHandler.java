package com.dbs.replsdk.ui.events;

public class TextEventHandler extends KaiEventHandler {
    public TextEventHandler(RequestSender sender) {
        super(TextEvent.class, sender);
    }
}
