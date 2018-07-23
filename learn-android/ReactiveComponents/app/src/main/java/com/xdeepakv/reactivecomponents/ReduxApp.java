package com.xdeepakv.reactivecomponents;

import android.app.Application;
import android.os.SystemClock;
import android.util.Log;

public class ReduxApp extends Application {
    private Redux bus;

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new Redux();
        Log.d("before", "" + System.currentTimeMillis());
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                bus.send(new Events.Message("This will update soon!!!"));
            }
        }.start();
        Log.d("after ", "" + System.currentTimeMillis());
        TimeComponent.register(this);
    }

    public Redux bus() {
        return bus;
    }
}
