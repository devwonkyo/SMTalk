package com.example.dnjsr.smtalk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dnjsr.smtalk.info.RoomInfo;

public class ChatRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }
        Bundle bundle = getIntent().getExtras();
        RoomInfo roomInfo = bundle.getParcelable("roominfo");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(roomInfo.getRoomName()+"   "+roomInfo.getMemberNumber());
    }
}
