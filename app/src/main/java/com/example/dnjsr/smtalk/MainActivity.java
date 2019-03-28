package com.example.dnjsr.smtalk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dnjsr.smtalk.api.FriendListCallApi;
import com.example.dnjsr.smtalk.api.LoginApi;
import com.example.dnjsr.smtalk.fragment.ChatFragment;
import com.example.dnjsr.smtalk.fragment.PeopleFragment;
import com.example.dnjsr.smtalk.fragment.SettingFragment;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.info.RoomInfo;
import com.example.dnjsr.smtalk.info.UserInfo;
import com.example.dnjsr.smtalk.pattern.UserIdPattern;
import com.example.dnjsr.smtalk.result.FriendListCallResult;
import com.example.dnjsr.smtalk.result.LoginResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.chrono.MinguoChronology;
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
                DialogFragment friendDialogFragmnet = new FriendDialogFragment();
                friendDialogFragmnet.setCancelable(false);
                friendDialogFragmnet.show(getSupportFragmentManager(),"frienddialogfragment");
                return true;
            case R.id.item_newroom:
                DialogFragment roomDialogFragmnet = new RoomDialogFragment();
                roomDialogFragmnet.setCancelable(false);
                roomDialogFragmnet.show(getSupportFragmentManager(),"roomdialogfragment");
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServerURL serverURL = new ServerURL();
        final String currentServer = serverURL.getUrl();

        userInfos = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        final UserInfo userinfo = bundle.getParcelable("userinfo");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    HashMap<String, String> input = new HashMap<>();
                    input.put("_id", userinfo.get_id());

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(currentServer)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    FriendListCallApi friendListCallApi = retrofit.create(FriendListCallApi.class);
                    friendListCallApi.postUserInfo(input).enqueue(new Callback<FriendListCallResult>() {
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
                                            Toast.makeText(MainActivity.this, "로드 실패.", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 1:
                                            Thread thread_inner = new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    for (UserInfo userInfo : map.getFriendsList()) {
                                                        URL url = null;
                                                        try {
                                                            url = new URL(currentServer + userInfo.getProfileImgUrl());
                                                        } catch (MalformedURLException e) {
                                                            e.printStackTrace();
                                                        }
                                                            URI uri = null;
                                                        try {
                                                            uri = url.toURI();
                                                        } catch (URISyntaxException e) {
                                                            e.printStackTrace();
                                                        }

                                                        /*InputStream inputStream = null;
                                                        try {
                                                            inputStream = url.openStream();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        final Bitmap bm = BitmapFactory.decodeStream(inputStream);
                                                        userInfo.setProfileImg(bm);*/
                                                        userInfo.setProfileImg(uri);
                                                        userInfos.add(userInfo);
                                                    }
                                                }
                                            });
                                            thread_inner.start();


                                            Toast.makeText(MainActivity.this,map.getFriendsList().get(0).getUserName(), Toast.LENGTH_SHORT).show();
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
        /*try {
            HashMap<String, String> input = new HashMap<>();
            input.put("_id", userinfo.get_id());

            Retrofit retrofit = new Retrofit.Builder().baseUrl(currentServer)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            FriendListCallApi friendListCallApi = retrofit.create(FriendListCallApi.class);
            friendListCallApi.postUserInfo(input).enqueue(new Callback<FriendListCallResult>() {
                @Override
                public void onResponse(Call<FriendListCallResult> call, Response<FriendListCallResult> response) {
                    if (response.isSuccessful()) {
                        FriendListCallResult map = response.body();
                        if (map != null) {
                            switch (map.getResult()) {
                                case -1:
                                    Toast.makeText(MainActivity.this, "데이터 베이스 오류입니다.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 0:
                                    Toast.makeText(MainActivity.this, "로드 실패.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    for(UserInfo userInfo : map.getFriendsList()){
                                        userInfos.add(userInfo);
                                    }
                                    Toast.makeText(MainActivity.this,map.getFriendsList().get(0).getUserName(), Toast.LENGTH_SHORT).show();
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
        }*/

        //userInfos = new ArrayList<>();

        roomInfos = new ArrayList<>();



        roomInfos.add(new RoomInfo("프젝","3"));
        roomInfos.add(new RoomInfo("1학년과톡","15"));                                                              //roominfo 객체 input
        roomInfos.add(new RoomInfo("ㅂㄹㅊㄱ","6"));
        roomInfos.add(new RoomInfo("ㅂㄹㅊㄱ","6"));
        roomInfos.add(new RoomInfo("ㅂㄹㅊㄱ","6"));
        roomInfos.add(new RoomInfo("ㅂㄹㅊㄱ","6"));

//        ---------------------------------------------------------------------------------------------------------------------------------------------

//        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = getLayoutInflater();
        dialog_newfriend = inflater.inflate(R.layout.dialog_newfriend,null);
        dialog_newroom = inflater.inflate(R.layout.dialog_newroom,null);                      //dialog layout inflate

        peopleFragment = new PeopleFragment();
        chatFragment = new ChatFragment();
        settingFragment = new SettingFragment();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }




        /*Intent intent = getIntent();
        UserInfo userInfo = intent.getParcelableExtra("userinfo");*/
        chatFragment.setRoomAdapterList(roomInfos);                                                                     //chat fragment로 roominfos객체리스트 전달
        peopleFragment.setUserInfos(userInfos);                                                                         //people fragment로 userinfos객체리스트 전달
        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,peopleFragment).commit();  //people fragment로 초기화

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
                        //peopleFragment.setUserInfos(userInfos);
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

    public static class FriendDialogFragment extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("친구추가").setView(R.layout.dialog_newfriend).setPositiveButton("추가", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            return builder.create();
        }
    }


    public static class RoomDialogFragment extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("방 생성").setView(R.layout.dialog_newroom).setPositiveButton("생성", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText dialog_edittext_roomname = dialog_newroom.findViewById(R.id.dialog_edittext_roomname);
                    EditText dialog_edittext_membercount = dialog_newroom.findViewById(R.id.dialog_edittext_membercount);
                    roomname = dialog_edittext_roomname.getText().toString();
                    membercount = dialog_edittext_membercount.getText().toString();
                }
            }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            return builder.create();
        }
    }
}
