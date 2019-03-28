package com.example.dnjsr.smtalk.api;

import com.example.dnjsr.smtalk.result.IdCheckResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IdCheckApi {
    @GET("/check/{userId}")
    Call<IdCheckResult> getUserId(@Path("userId") String userId);
}
