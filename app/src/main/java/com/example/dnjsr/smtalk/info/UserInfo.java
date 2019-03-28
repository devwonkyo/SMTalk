package com.example.dnjsr.smtalk.info;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI;
import java.util.List;

public class UserInfo implements Parcelable{
    String userId;
    String userPassword;
    String userName;
    String profileImgUrl;
    String comment;
    String _id;
    List<UserInfo> friendsList;
    URI profileImg;

    public UserInfo(String userId, String comment, URI profileImg) {
        this.userId = userId;
        this.comment = comment;
        this.profileImg = profileImg;
    }

    public UserInfo(String userId, String userPassword, String userName, String profileImgUrl, String comment, String _id, List<UserInfo> friendsList) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
        this.comment = comment;
        this._id = _id;
        this.friendsList = friendsList;
    }

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

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<UserInfo> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<UserInfo> friendsList) {
        this.friendsList = friendsList;
    }

    public URI getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(URI profileImg) {
        this.profileImg = profileImg;
    }

    public static Creator<UserInfo> getCREATOR() {
        return CREATOR;
    }

    protected UserInfo(Parcel in) {
        userId = in.readString();
        userPassword = in.readString();
        userName = in.readString();
        profileImgUrl = in.readString();
        comment = in.readString();
        _id = in.readString();
        friendsList = in.createTypedArrayList(UserInfo.CREATOR);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userPassword);
        dest.writeString(userName);
        dest.writeString(profileImgUrl);
        dest.writeString(comment);
        dest.writeString(_id);
        dest.writeTypedList(friendsList);
    }
}


