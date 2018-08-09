package com.ptit.bb.timgiasu;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initFresco();
    }

    private void initFresco() {
        Fresco.initialize(getApplicationContext());
    }
}
