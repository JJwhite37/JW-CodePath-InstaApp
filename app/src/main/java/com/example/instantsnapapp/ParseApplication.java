package com.example.instantsnapapp;

import android.app.Application;

import com.example.instantsnapapp.models.Post;
import com.example.instantsnapapp.models.User;
import com.parse.Parse;
import com.parse.ParseObject;



public class ParseApplication extends Application {
    public static final String APPLICATION_ID = BuildConfig.APPLICATION_ID;
    public static final String CLIENT_KEY = BuildConfig.CLIENT_KEY;
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(User.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(APPLICATION_ID)
                .clientKey(CLIENT_KEY)
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
