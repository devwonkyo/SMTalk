package com.example.dnjsr.smtalk.api;

import com.example.dnjsr.smtalk.JoinResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JoinApi {
    @FormUrlEncoded
    @POST("/join")
    Call<JoinResult> postJoinUserInfo(@FieldMap HashMap<String,String> map);
}
