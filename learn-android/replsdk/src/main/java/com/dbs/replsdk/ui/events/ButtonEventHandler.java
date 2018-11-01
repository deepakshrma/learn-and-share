package com.dbs.replsdk.ui.events;

public class ButtonEventHandler extends KaiEventHandler {
    private HyperLinkOpener linkOpener;

    public ButtonEventHandler(RequestSender requestSender) {
        super(ButtonEvent.class, requestSender);
    }

    public ButtonEventHandler withLinkOpener(HyperLinkOpener opener) {
        this.linkOpener = opener;
        return this;
    }


    @Override
    public void handle(KaiEvent event) {
        if (event instanceof ButtonEvent && ((ButtonEvent) event).isHyperLinkEvent()) {
            if (linkOpener != null) {
                linkOpener.openLink(((ButtonEvent) event).getPayload());
            }
        } else {
            super.handle(event);
        }
    }
}