package com.example.dnjsr.smtalk.info;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomInfo implements Parcelable {
    String roomId;
    String roomName;
    String memberNumber;

    public RoomInfo(String roomName, String memberNumber) {
        this.roomName = roomName;
        this.memberNumber = memberNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public static Creator<RoomInfo> getCREATOR() {
        return CREATOR;
    }

    protected RoomInfo(Parcel in) {
        roomName = in.readString();
        memberNumber = in.readString();
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
        dest.writeString(memberNumber);
    }
}

