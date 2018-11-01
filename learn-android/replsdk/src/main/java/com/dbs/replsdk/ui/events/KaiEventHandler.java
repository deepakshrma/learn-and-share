package com.dbs.replsdk.ui.events;

import com.dbs.replsdk.model.Request;
import com.dbs.ui.multiadapter.GroupEventHandler;

public class KaiEventHandler extends GroupEventHandler<KaiEvent> {

    protected RequestSender mSender;

    public KaiEventHandler(Class klass, RequestSender sender) {
        super(klass);
        this.mSender = sender;
    }

    @Override
    public void handle(KaiEvent event) {
        Request request = event.createRequest();
        if (request != null && mSender != null) {
            mSender.sendRequest(request);
        }
    }

    public interface RequestSender {
        void sendRequest(Request request);
    }
}
