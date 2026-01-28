package com.hx.campus.utils.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    //登录接口
    @GET("/login")
    Call<Result<String>> login(
            @Query("phone") String phone,
            @Query("pwd") String pwd
            );
}
