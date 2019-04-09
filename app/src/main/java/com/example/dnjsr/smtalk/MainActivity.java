package com.example.dnjsr.smtalk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dnjsr.smtalk.api.RetrofitApi;
import com.example.dnjsr.smtalk.fragment.ChatFragment;
import com.example.dnjsr.smtalk.fragment.PeopleFragment;
import com.example.dnjsr.smtalk.fragment.SettingFragment;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.IsLogin;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.info.RoomInfo;
import com.example.dnjsr.smtalk.info.UserInfo;
import com.example.dnjsr.smtalk.result.FriendListCallResult;
import com.example.dnjsr.smtalk.result.RoomListCallResult;
import com.example.dnjsr.smtalk.update.RoomsListCall;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ActionBar actionBar;
    MenuInflater inflater;
    MenuItem item_newfriend;
    MenuItem item_newroom;
    PeopleFragment peopleFragment;
    ChatFragment chatFragment;
    SettingFragment settingFragment;
    static View dialog_newroom;
    static View dialog_newfriend;
    static String roomname;
    static String membercount;
    List<UserInfo> userInfos;
    List<RoomInfo> roomInfos;
    Handler handler;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu,menu);
        item_newfriend = menu.findItem(R.id.item_newfriend);
        item_newroom = menu.findItem(R.id.item_newroom);
        item_newroom.setVisible(false);
        for(int i = 0; i < menu.size(); i++){                                                                                   //optionsmenu icon color change
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_ATOP);
            }
        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_newfriend:
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
                return true;
            case R.id.item_newroom:

                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String currentServer = ServerURL.getUrl();

        peopleFragment = new PeopleFragment();
        chatFragment = new ChatFragment();
        settingFragment = new SettingFragment();

        userInfos = new ArrayList<>();
        roomInfos = new ArrayList<>();


        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0) {
                    peopleFragment.notifyDataSetChangeed((ArrayList<UserInfo>) userInfos);
                    peopleFragment.setUserInfos(userInfos);
                    Log.d("test123","핸들러받고 다시 초기화");
                }
                if(msg.what ==1){
                    Log.d("test123","핸들러2 받고 다시초기화");
                    //chatFragment.notifyDataSetChangeed((ArrayList<RoomInfo>) roomInfos);
                    chatFragment.setRoomAdapterList(roomInfos);
                }
            }
        };




      /*  Bundle bundle = getIntent().getExtras();
        final UserInfo userinfo = bundle.getParcelable("userinfo");*/

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("test123","쓰레드1 시작");
                    HashMap<String, String> input = new HashMap<>();
                    input.put("_id", CurrentUserInfo.getUserInfo().get_id());

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(currentServer)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    RetrofitApi friendListCallApi = retrofit.create(RetrofitApi.class);
                    friendListCallApi.postUserInfoForFriendList(input).enqueue(new Callback<FriendListCallResult>() {
                        @Override
                        public void onResponse(Call<FriendListCallResult> call, Response<FriendListCallResult> response) {
                            if (response.isSuccessful()) {
                                final FriendListCallResult map = response.body();
                                if (map != null) {
                                    switch (map.getResult()) {
                                        case -1:
                                            Toast.makeText(MainActivity.this, "데이터 베이스 오류입니다.", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 0:
                                            Toast.makeText(MainActivity.this, "등록된 친구가 없습니다.", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 1:
                                            Thread thread_inner = new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Log.d("test123","쓰레드2 시작");
                                                    for (UserInfo userInfo : map.getFriendsList()) {
                                                        URL url = null;
                                                        try {
                                                            url = new URL(currentServer + userInfo.getProfileImgUrl());
                                                        } catch (MalformedURLException e) {
                                                            e.printStackTrace();
                                                        }

                                                        InputStream inputStream = null;
                                                        try {
                                                            inputStream = url.openStream();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        final Bitmap bm = BitmapFactory.decodeStream(inputStream);
                                                        userInfo.setProfileImg(bm);
                                                        userInfos.add(userInfo);
                                                    }
                                                    Log.d("test123","userInfos 완료");
                                                    handler.sendEmptyMessage(0);
                                                    Log.d("test123","핸들러메시지보냄");
                                                }
                                            });
                                            thread_inner.start();
                                            Toast.makeText(MainActivity.this,"소캣톡 환영합니다!", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<FriendListCallResult> call, Throwable t) {

                        }

                    });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        thread.start();



        LayoutInflater inflater = getLayoutInflater();
        dialog_newfriend = inflater.inflate(R.layout.dialog_newfriend,null);
        dialog_newroom = inflater.inflate(R.layout.dialog_newroom,null);                      //dialog layout inflate


        getRoomsList(CurrentUserInfo.getUserInfo().get_id());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }

        /*roomInfos.add(new RoomInfo("이동영","ㅎㅎ","1"));
        roomInfos.add(new RoomInfo("정원교","ㅋㅋㅋㅋㅋㅋ","1"));
        roomInfos.add(new RoomInfo("방효근","???????","1"));
        roomInfos.add(new RoomInfo("이동영","ㅎㅎ","1"));
        chatFragment.setRoomAdapterList(roomInfos);
*/


        /*Intent intent = getIntent();
        UserInfo userInfo = intent.getParcelableExtra("userinfo");*/


        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,peopleFragment).commit();  //people fragment로 초기화
        Log.d("test123","프래그먼트 화면 초기화");

        BottomNavigationView mainactivity_bottomnavigationview = findViewById(R.id.mainactivity_bottomnavigationview);

        mainactivity_bottomnavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_people :
                        actionBar = getSupportActionBar();
                        actionBar.setTitle("친구");
                        item_newfriend.setVisible(true);
                        item_newroom.setVisible(false);
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,peopleFragment).commit();
                        return true;
                    case R.id.action_chat:
                        item_newfriend.setVisible(false);
                        item_newroom.setVisible(true);
                        actionBar = getSupportActionBar();
                        actionBar.setTitle("채팅");
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,chatFragment).commit();
                        return true;
                    case R.id.action_setting:
                        actionBar = getSupportActionBar();
                        actionBar.setTitle("설정");
                        item_newfriend.setVisible(false);
                        item_newroom.setVisible(false);
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,settingFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }


    public void getRoomsList(String _id){

            try {
                HashMap<String, String> input = new HashMap<>();
                input.put("_id", _id);
                Retrofit retrofit = new Retrofit.Builder().baseUrl(ServerURL.getUrl())
                        .addConverterFactory(GsonConverterFactory.create()).build();
                RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
                retrofitApi.post_idForRoomList(input).enqueue(new Callback<RoomListCallResult>() {
                    @Override
                    public void onResponse(Call<RoomListCallResult> call, final Response<RoomListCallResult> response) {
                            Thread roomThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if (response.isSuccessful()) {
                                        RoomListCallResult map = response.body();
                                        if (map != null) {
                                            switch (map.getResult()) {
                                                case 0:
                                                    Log.d("12321","room listr fail");
                                                    break;
                                                case 1:
                                                    Log.d("12321","room list ok");
                                                    CurrentUserInfo.getUserInfo().setRoomsList(map.getRoomsList());
                                                    roomInfos = map.getRoomsList();
                                                    handler.sendEmptyMessage(1);
                                                    break;
                                            }
                                        }
                                    }
                                }
                            });
                            roomThread.start();
                    }
                    @Override
                    public void onFailure(Call<RoomListCallResult> call, Throwable t) {
                        Log.d("12321","fail to connect :  roomlist");
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }



}
