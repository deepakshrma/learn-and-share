package com.dbs.replsdk.arch;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Worker mWorker;

    public ViewModelFactory(Worker worker) {
        mWorker = worker;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ChatBotViewModel.class)) {
            return (T) new ChatBotViewModel(mWorker);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
