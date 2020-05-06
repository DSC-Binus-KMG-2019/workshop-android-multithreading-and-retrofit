package com.fernandochristyanto.jsonplaceholderclient;

import android.app.Application;

import timber.log.Timber;

public class JsonPlaceHolderClientApplication extends Application {
    @Override
    public void onCreate () {
        super.onCreate();
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }
}
