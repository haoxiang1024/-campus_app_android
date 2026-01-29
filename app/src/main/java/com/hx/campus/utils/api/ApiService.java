package com.hx.campus.utils.api;

import com.hx.campus.adapter.entity.LostFound;
import com.hx.campus.adapter.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    //登录
    @POST("login")
    Call<Result<User>> login(@Query("phone") String phone, @Query("pwd") String pwd);
    //获取置顶信息
    @POST("showTopList")
    Call<Result<List<LostFound>>> showTopList(@Query("stick") int stick);

}
