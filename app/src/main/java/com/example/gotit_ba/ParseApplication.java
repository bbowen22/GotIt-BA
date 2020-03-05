package com.example.gotit_ba;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("mySeniorProj")
                .clientKey("myKey2298")
                .server("https://senior-proj-ex.herokuapp.com/parse/").build()
        );
    }
}
