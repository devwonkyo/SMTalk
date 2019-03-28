package com.example.dnjsr.smtalk.api;

import com.example.dnjsr.smtalk.result.FriendListCallResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FriendListCallApi {

    @FormUrlEncoded
    @POST("/friend/list")
    Call<FriendListCallResult> postUserInfo(@FieldMap HashMap<String,String> map);

}
