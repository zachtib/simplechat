package com.zachtib.simplechat;

import android.app.Application;

import com.zachtib.simplechat.module.AppComponent;
import com.zachtib.simplechat.module.AppModule;
import com.zachtib.simplechat.module.DaggerAppComponent;

public class SimpleChat extends Application{

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
