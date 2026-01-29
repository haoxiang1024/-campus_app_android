package com.hx.campus.utils.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // 提示：确保这里的 IP 地址与你当前后端服务的 IP 一致
    private static final String BASE_URL = "http://192.168.122.122:8081/school/";
    private static volatile RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {
        // 当服务器返回 1766910928000 这种数字时，强制转为 Java 的 Date 对象
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> {
                    try {
                        // 尝试将 JSON 中的数字（长整型）转为 Date
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    } catch (Exception e) {
                        // 如果后端偶尔返回的是字符串格式日期，则按默认方式解析
                        return new Date(json.getAsString());
                    }
                })
                .create();

        // 初始化 Retrofit 并关联自定义的 Gson
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitClient.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }

    public ApiService getApi() {
        return retrofit.create(ApiService.class);
    }
}