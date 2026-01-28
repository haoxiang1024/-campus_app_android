
package com.hx.campus.utils.internet;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

//OkHttpUtils文件，用于获取URL上get或者post、downFile等方法的信息
public class OkhttpUtils {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final static int READ_TIMEOUT = 9999;

    private final static int CONNECT_TIMEOUT = 9999;

    private final static int WRITE_TIMEOUT = 9999;
    private static final OkHttpClient okHttpClient = new OkHttpClient();

    public static void get(String url, OkHttpCallback Callback) {
        Callback.url = url;
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        //读取超时
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        //连接超时
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        //写入超时
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        //自定义连接池最大空闲连接数和等待时间大小，否则默认最大5个空闲连接
        clientBuilder.connectionPool(new ConnectionPool(32, 5, TimeUnit.MINUTES));

        //构建请求头
        Request request = new Request.Builder().url(url).build();
        //接收服务端的响应
        clientBuilder.build().newCall(request).enqueue(Callback);

    }

    public static void post(String url, String json, OkHttpCallback callback) {
        callback.url = url;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void downFile(String url, final String saveDir, OkHttpCallback callback) {
        callback.url = url;
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * @param address     服务器地址
     * @param requestBody 请求体数据
     * @param callback    回调接口
     */
    public static void uploadFile(String address, RequestBody requestBody, okhttp3.Callback callback) {

        //发送请求
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(9999, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(9999, TimeUnit.SECONDS)//设置读取超时时间
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);

    }

}
