
package com.hx.campus.utils.internet;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//监听服务端的响应，获取服务端的正确/报错信息
public class OkHttpCallback implements Callback {
    public final String TAG = com.hx.campus.utils.internet.OkHttpCallback.class.getSimpleName();

    public String url;
    public String result;

    //接口调用成功
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.e(TAG, "url:" + url);
        //成功时获取接口数据
        result = response.body().string();
        Log.e(TAG, "请求成功:" + result);
        //调用onFinish输出获取的信息，可用通过重写onFinish()方法，运用hashmap获取需要的值并存储
        onFinish("success", result);
    }

    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "url:" + url);
        Log.e(TAG, "请求失败:" + e.toString());
        //请求失败，输出失败的原因
        onFinish("failure", e.toString());

    }

    public void onFinish(String status, String msg) {
        Log.e(TAG, "url:" + url + ";" + "status:" + status + ";" + "msg:" + msg);
    }

}

