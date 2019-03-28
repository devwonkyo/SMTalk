package com.example.dnjsr.smtalk.globalVariables;

import android.app.Application;

import com.example.dnjsr.smtalk.info.UserInfo;

public class ServerURL extends Application {
    private String url = "http://18.188.144.183:9999/";

    private static UserInfo userInfo = new UserInfo();

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        ServerURL.userInfo = userInfo;
    }

    public String getUrl() {
        return url;
    }
}
