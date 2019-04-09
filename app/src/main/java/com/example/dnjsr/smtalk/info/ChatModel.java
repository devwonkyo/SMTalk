package com.example.dnjsr.smtalk.info;

import android.os.Parcel;
import android.os.Parcelable;

public class ChatModel implements Parcelable {

    String id;
    String message;

    public ChatModel(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static Creator<ChatModel> getCREATOR() {
        return CREATOR;
    }

    protected ChatModel(Parcel in) {
        id = in.readString();
        message = in.readString();
    }

    public static final Creator<ChatModel> CREATOR = new Creator<ChatModel>() {
        @Override
        public ChatModel createFromParcel(Parcel in) {
            return new ChatModel(in);
        }

        @Override
        public ChatModel[] newArray(int size) {
            return new ChatModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(message);
    }
}
