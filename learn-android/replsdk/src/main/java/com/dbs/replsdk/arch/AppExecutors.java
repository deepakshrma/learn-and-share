package com.dbs.replsdk.arch;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A static class that serves as a central point to execute common tasks.
 * <p>
 */
public class AppExecutors {
    private static final int THREAD_COUNT = 3;

    private static volatile AppExecutors sInstance;

    private final Executor diskIO;

    private final Executor networkIO;

    private final Executor mainThread;

    @VisibleForTesting
    AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT),
                new MainThreadExecutor());
    }

    /**
     * Returns an instance of the task executor.
     *
     * @return The singleton AppExecutors.
     */
    public static AppExecutors getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (AppExecutors.class) {
            if (sInstance == null) {
                sInstance = new AppExecutors();
            }
        }
        return sInstance;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }


    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
