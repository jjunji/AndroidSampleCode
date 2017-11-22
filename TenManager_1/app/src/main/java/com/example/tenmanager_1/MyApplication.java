package com.example.tenmanager_1;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by 전지훈 on 2017-11-08.
 */

public class MyApplication extends Application{
    private static MyApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        INSTANCE = this;
    }

    public static MyApplication getInstance(){
        return INSTANCE;
    }

}
