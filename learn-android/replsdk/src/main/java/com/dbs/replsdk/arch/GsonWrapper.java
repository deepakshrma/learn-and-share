package com.dbs.replsdk.arch;

import com.dbs.replsdk.uimodel.ButtonMessageContent;
import com.dbs.replsdk.uimodel.CardMessageContent;
import com.dbs.replsdk.uimodel.ContainerMessageContent;
import com.dbs.replsdk.uimodel.TextMessageContent;
import com.dbs.replsdk.uimodel.UiMessageContent;
import com.dbs.replsdk.util.RuntimeTypeAdapterFactory;
import com.google.gson.Gson;

public final class GsonWrapper {
    private static Gson INSTANCE;

    private GsonWrapper() {
        // Do nothing
    }

    public static Gson getInstance() {
        if (INSTANCE == null) {
            synchronized (Gson.class) {
                RuntimeTypeAdapterFactory<UiMessageContent> messageAdapterFactory = RuntimeTypeAdapterFactory.of(UiMessageContent.class, "type")
                        .registerSubtype(TextMessageContent.class, "TEXT")
                        .registerSubtype(ContainerMessageContent.class, "CONTAINER")
                        .registerSubtype(ButtonMessageContent.class, "BUTTON")
                        .registerSubtype(CardMessageContent.class, "CARD");


                INSTANCE = new Gson().newBuilder()
                        .setPrettyPrinting()
                        .disableHtmlEscaping()
                        .registerTypeAdapterFactory(messageAdapterFactory).create();
            }
        }
        return INSTANCE;
    }
}
