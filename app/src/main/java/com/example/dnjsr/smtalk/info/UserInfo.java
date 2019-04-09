package com.example.dnjsr.smtalk.info;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.List;

public class UserInfo implements Parcelable {
    Boolean isChange = false;
    String userId;
    String userPassword;
    String userName;
    String profileImgUrl;
    String comment;
    String _id;
    List<UserInfo> friendsList;
    List<RoomInfo> roomsList;
    Bitmap profileImg;

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        userId = in.readString();
        userPassword = in.readString();
        userName = in.readString();
        profileImgUrl = in.readString();
        comment = in.readString();
        _id = in.readString();
        friendsList = in.createTypedArrayList(UserInfo.CREATOR);
        roomsList = in.createTypedArrayList(RoomInfo.CREATOR);
        profileImg = in.readParcelable(Bitmap.class.getClassLoader());
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

    public Boolean getChange() {
        return isChange;
    }

    public void setChange(Boolean change) {
        isChange = change;
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

    public List<RoomInfo> getRoomsList() {
        return roomsList;
    }

    public void setRoomsList(List<RoomInfo> roomsList) {
        this.roomsList = roomsList;
    }

    public Bitmap getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(Bitmap profileImg) {
        this.profileImg = profileImg;
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
        dest.writeString(profileImgUrl);
        dest.writeString(comment);
        dest.writeString(_id);
        dest.writeTypedList(friendsList);
        dest.writeTypedList(roomsList);
        dest.writeParcelable(profileImg, flags);
    }
}


