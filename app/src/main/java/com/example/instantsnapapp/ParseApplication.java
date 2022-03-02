package com.example.instantsnapapp;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("wopR2672aSLGOKqYh6XkanTSRNTHeCCJcqFnXF1t")
                .clientKey("aYSRk3FYwhRPSue7a6lMBTSn3DNiCX4AeO4l7MBF")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
