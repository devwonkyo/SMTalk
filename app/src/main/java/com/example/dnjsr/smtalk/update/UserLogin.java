package com.example.dnjsr.smtalk.update;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.dnjsr.smtalk.LoginActivity;
import com.example.dnjsr.smtalk.api.RetrofitApi;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.IsLogin;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.info.UserInfo;
import com.example.dnjsr.smtalk.result.LoginResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLogin {
    public void Login(final String id, final String password , final Intent intent, final Activity activity){

        final String strId = id;
        final String strPassword = password;

        try {
            HashMap<String, String> input = new HashMap<>();
            input.put("userId", strId);
            input.put("userPassword", strPassword);


            Retrofit retrofit = new Retrofit.Builder().baseUrl(ServerURL.getUrl())
                    .addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
            retrofitApi.postLoginUserInfo(input).enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                    if (response.isSuccessful()) {
                        LoginResult map = response.body();
                        if (map != null) {
                            switch (map.getResult()) {
                                case -2:
                                    Log.d("12321", "Login fail DB error");
                                    break;
                                case -1:
                                    Log.d("12321", "Login fail wrong password");
                                    break;
                                case 0:
                                    Log.d("12321", "Login fail wrong ID");
                                    break;
                                case 1:
                                    Log.d("12321", "Login ok");
                                    UserInfo userinfo = map.getUserInfo();
                                    userinfo.setChange(true);
                                    CurrentUserInfo.setUserInfo(userinfo);
                                    UserInfo asd = CurrentUserInfo.getUserInfo();
                                    IsLogin.setIsLogin(true);
                                    Log.d("12321",asd.get_id());
                                    activity.startActivity(intent);
                                    activity.finish();
                                    break;
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResult> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
