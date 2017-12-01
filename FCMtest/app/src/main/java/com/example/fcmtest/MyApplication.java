package com.example.fcmtest;

import android.app.Application;

/**
 * Created by 전지훈 on 2017-12-01.
 */

public class MyApplication extends Application{
    public static MyApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static MyApplication getInstanc(){
        return INSTANCE;
    }
}
