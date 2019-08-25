package com.h.locationapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.h.locationapp.util.Trace;

public class LocationApplication  extends MultiDexApplication {
    private static LocationApplication application;
    private static Toast mToast;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static synchronized LocationApplication getInstance() {
        return application;
    }

    public static void showToast(@NonNull final String msg) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                Trace.i("toast :" + String.valueOf(msg));
                mToast = Toast.makeText(application, msg, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }
}
