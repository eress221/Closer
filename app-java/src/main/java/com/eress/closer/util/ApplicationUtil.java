package com.eress.closer.util;

import android.app.Application;
import android.content.res.Configuration;

public class ApplicationUtil extends Application {

    public int intro = 0;
    public int flag = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
