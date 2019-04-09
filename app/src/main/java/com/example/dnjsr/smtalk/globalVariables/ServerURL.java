package com.example.dnjsr.smtalk.globalVariables;

import android.app.Application;

import com.example.dnjsr.smtalk.info.UserInfo;

public class ServerURL extends Application {
    private static String url = "http://13.209.68.2:9999/";

    public static String getUrl() {
        return url;
    }
}
