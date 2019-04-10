package com.example.dnjsr.smtalk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnjsr.smtalk.api.RetrofitApi;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.SelectedUserInfo;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.info.UserInfo;
import com.example.dnjsr.smtalk.result.JoinResult;
import com.example.dnjsr.smtalk.update.UserInfoUpdate;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profileactivity_imageview_profileimage;
    private ImageView profileactivity_imageview_friend;
    private ImageView profileactivity_imageview_chatCreate;
    private TextView profileactivity_textview_username;
    private TextView profileactivity_textview_usermessage;
    private TextView profileactivity_textview_friend;


    Bitmap reciveImage;
    Boolean isFriend = false;
    Boolean isCurrentUser =false;
    UserInfo userInfo;
    UserInfoUpdate userInfoUpdate = new UserInfoUpdate();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }

        userInfo = SelectedUserInfo.getUserInfo();
        Log.d("testfriend",userInfo.get_id()+"  "+userInfo.getUserName()+"선택");

        profileactivity_imageview_profileimage = findViewById(R.id.profileactivity_imageview_profileimage);
        profileactivity_textview_username = findViewById(R.id.profileactivity_textview_username);
        profileactivity_textview_usermessage =findViewById(R.id.profileactivity_textview_usermessage);
        profileactivity_imageview_friend = findViewById(R.id.profileactivity_imageview_friend);
        profileactivity_textview_friend = findViewById(R.id.profileactivity_textview_friend);
        profileactivity_imageview_chatCreate =findViewById(R.id.profileactivity_imageview_chatCreate);
//--------------------------------------------------------------------------------------------------------------------------------------- 내 프로필 초기화
        profileactivity_imageview_profileimage.setImageBitmap(userInfo.getProfileImg());
        profileactivity_textview_username.setText(userInfo.getUserName());
        profileactivity_textview_usermessage.setText(userInfo.getComment());

//--------------------------------------------------------------------------------------------------------------------------------------- 친구여부에 따른 추가 삭제 ui변경

        for (int i = 0; i<CurrentUserInfo.getUserInfo().getFriendsList().size(); i++){
            if(CurrentUserInfo.getUserInfo().getFriendsList().get(i).get_id().equals(userInfo.get_id())){
                isFriend=true;
                break;
            }
        }

        if(isFriend){
            profileactivity_imageview_friend.setImageResource(R.drawable.icon_user_delete);
            profileactivity_textview_friend.setText("친구 삭제");
        }
        else{
            profileactivity_imageview_friend.setImageResource(R.drawable.icon_user_plus);
            profileactivity_textview_friend.setText("친구 추가");
        }

//--------------------------------------------------------------------------------------------------------------------------------------- 프로필사진 액티비티
        profileactivity_imageview_profileimage.setOnClickListener(new View.OnClickListener() {               //image눌러 화면이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ProfilepictureActivity.class);
                startActivity(intent);
            }
        });

//--------------------------------------------------------------------------------------------------------------------------------------- 친구 추가 삭제

        profileactivity_imageview_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myId = CurrentUserInfo.getUserInfo().get_id();
                String friendId = userInfo.get_id();
                if(isFriend){
                    deleteFriend(myId,friendId);
                    userInfoUpdate.Update(CurrentUserInfo.getUserInfo().get_id(),ProfileActivity.this);
                }else{
                    addFriend(myId,friendId);
                    userInfoUpdate.Update(CurrentUserInfo.getUserInfo().get_id(),ProfileActivity.this);
                    sendBroadcast(new Intent("finish_activity"));                                      //search activity 종료 신호보내기, -->mainactivity로 이동
                }

            }
        });


//--------------------------------------------------------------------------------------------------------------------------------------- 채팅방 이동





    }
    public void addFriend(String myId,String friendId){
        HashMap<String, String> input = new HashMap<>();
        input.put("_id", myId);
        input.put("friendId",friendId);
        Log.d("testfriend",friendId+"  "+userInfo.getUserName()+"추가");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ServerURL.getUrl())
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        retrofitApi.addFriend(input).enqueue(new Callback<JoinResult>() {
            @Override
            public void onResponse(Call<JoinResult> call, Response<JoinResult> response) {
                if (response.isSuccessful()) {
                    JoinResult searchResult = response.body();
                    switch (searchResult.getResult()) {
                        case 0:
                            Log.d("test123","FAfail");
                            break;
                        case 1:
                            Log.d("test123","FAsucess");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<JoinResult> call, Throwable t) {

            }
        });
    }

    public void deleteFriend(String myId,String friendId){
        HashMap<String, String> input = new HashMap<>();
        input.put("_id", myId);
        input.put("friendId",friendId);
        Log.d("testfriend",friendId+"  "+userInfo.getUserName()+"삭제");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ServerURL.getUrl())
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        retrofitApi.deleteFriend(input).enqueue(new Callback<JoinResult>() {
            @Override
            public void onResponse(Call<JoinResult> call, Response<JoinResult> response) {
                if (response.isSuccessful()) {
                    JoinResult result = response.body();
                    switch (result.getResult()) {
                        case 0:
                            Log.d("test123","FDfail");
                            break;
                        case 1:
                            Log.d("test123","FDsucess");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<JoinResult> call, Throwable t) {

            }
        });
    }
}
