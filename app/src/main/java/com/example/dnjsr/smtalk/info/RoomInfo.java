package com.example.dnjsr.smtalk.info;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class RoomInfo implements Parcelable {
    String roomName;
    String lastChat;
    String unreadCount;
    List<UserInfo> usersList;
    Date createAt;

    public RoomInfo() {
    }

    public RoomInfo(String roomName, String lastChat, String unreadCount) {
        this.roomName = roomName;
        this.lastChat = lastChat;
        this.unreadCount = unreadCount;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getLastChat() {
        return lastChat;
    }

    public void setLastChat(String lastChat) {
        this.lastChat = lastChat;
    }

    public String getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        this.unreadCount = unreadCount;
    }

    public List<UserInfo> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<UserInfo> usersList) {
        this.usersList = usersList;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public static Creator<RoomInfo> getCREATOR() {
        return CREATOR;
    }

    protected RoomInfo(Parcel in) {
        roomName = in.readString();
        lastChat = in.readString();
        unreadCount = in.readString();
        usersList = in.createTypedArrayList(UserInfo.CREATOR);
    }

    public static final Creator<RoomInfo> CREATOR = new Creator<RoomInfo>() {
        @Override
        public RoomInfo createFromParcel(Parcel in) {
            return new RoomInfo(in);
        }

        @Override
        public RoomInfo[] newArray(int size) {
            return new RoomInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(roomName);
        dest.writeString(lastChat);
        dest.writeString(unreadCount);
        dest.writeTypedList(usersList);
    }
}

