package com.example.dnjsr.smtalk.globalVariables;

import android.app.Application;

public class ServerURL extends Application {
    private String url = "http://18.188.144.183:9999/";

    public String getUrl() {
        return url;
    }
}
