package com.example.dnjsr.smtalk.globalVariables;

import android.app.Application;

import com.example.dnjsr.smtalk.info.UserInfo;

public class SelectedUserInfo extends Application {

    private static UserInfo userInfo = new UserInfo();

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        SelectedUserInfo.userInfo = userInfo;
    }

}
