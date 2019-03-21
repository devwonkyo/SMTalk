package com.example.dnjsr.smtalk.info;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable{
    String userId;
    String userPassword;
    String userName;
    String profileImg;
    String contents;
    String _id;

    public UserInfo(String userId, String userPassword, String userName, String profileImg, String contents, String _id) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.profileImg = profileImg;
        this.contents = contents;
        this._id = _id;
    }

    protected UserInfo(Parcel in) {
        userId = in.readString();
        userPassword = in.readString();
        userName = in.readString();
        profileImg = in.readString();
        contents = in.readString();
        _id = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userPassword);
        dest.writeString(userName);
        dest.writeString(profileImg);
        dest.writeString(contents);
        dest.writeString(_id);
    }

}
