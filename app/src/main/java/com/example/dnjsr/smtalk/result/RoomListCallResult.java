package com.example.dnjsr.smtalk.result;

import com.example.dnjsr.smtalk.info.RoomInfo;
import com.example.dnjsr.smtalk.info.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class RoomListCallResult {
    int result;
    List<RoomInfo> roomsList;
    List<UserInfo> friendsList;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<RoomInfo> getRoomsList() {
        return roomsList;
    }

    public void setRoomsList(List<RoomInfo> roomsList) {
        this.roomsList = roomsList;
    }

    public List<UserInfo> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<UserInfo> friendsList) {
        this.friendsList = friendsList;
    }
}
