package com.dbs.replsdk.arch;

import android.content.Context;

import com.dbs.replsdk.MessageDataSource;
import com.dbs.replsdk.persistence.LocalMessageDataSource;
import com.dbs.replsdk.persistence.MessageDatabase;

public final class Injection {

    private Injection() {
        // Do nothing
    }

    public static MessageDataSource provideDataSource(Context context) {
        MessageDatabase database = MessageDatabase.getDatabase(context);
        return new LocalMessageDataSource(database.getMessageDao());
    }

    private static Worker provideWorker(Context context) {
        return new Worker(provideDataSource(context));
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        Worker worker = provideWorker(context);
        return new ViewModelFactory(worker);
    }
}
