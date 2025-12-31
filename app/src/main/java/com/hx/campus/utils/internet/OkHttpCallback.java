/*
 * Copyright (C) 2023 xuexiangjys()
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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

