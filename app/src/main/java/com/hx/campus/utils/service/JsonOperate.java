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

package com.hx.campus.utils.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hx.campus.adapter.entity.NewInfo;
import com.hx.campus.utils.internet.OkHttpCallback;
import com.hx.campus.utils.internet.OkhttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class JsonOperate {
    //    public static String newsKey = "bec633393690881151584f0ce9462ecf";//新闻key
    public static String newsKey = "9fbfe1092fa33bf4bf99d8b6a661963e";//新闻key
    public static String APPID = "nwrkqmmklavajgpp";//新闻接口id
    public static String APPSECRET = "b0MwcllXNVB4eHdBaDN1cFFqQmR0QT09";//新闻接口密钥

    /**
     * @param jsonStr json格式的字符串
     * @param key     要获取值的键
     * @return Object
     * @Title getJsonValueByKey
     * @Description 获取Json格式字符串中key对应的值
     * @version V1.0
     */
//    获取指定key的json数据
    public static Object getJsonValueByKey(String jsonStr, String key) {
        // 此处引入的是 com.alibaba.fastjson.JSONObject; 对象
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        return jsonObject.get(key);
    }

    //根据json数据获取字符数组
    public static String[] getJsonArray(String jsonStr, String key) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        //转换json数组
        JSONArray jsonArray = jsonObject.getJSONArray(key);
        String[] repGroup = jsonArray.toArray(new String[]{});
        return repGroup;
    }


    /**
     * @param jsonStr 要转换的json对象
     * @return JSONObject 转化为List<Map<String, Object>>:
     */
    public static List<Map<String, Object>> getJsonList(String jsonStr) {
        JSONObject obj = JSONObject.parseObject(jsonStr);
        JSONArray arr = obj.getJSONArray("data");
        String js = JSON.toJSONString(arr, SerializerFeature.WriteClassName);
        List<Map<String, Object>> mapList = JSON.parseObject(js, List.class);
        return mapList;

    }

    /**
     * @param json 要转换的json对象
     * @param c    实体类
     * @return json转list
     */
    public static List getList(String json, Class c) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String jsonObjectString = jsonObject.getString("data");
        return JSONArray.parseArray(jsonObjectString, c);
    }

    /**
     * @param json 要转换的json对象
     * @param key  key值
     * @return 返回key值对应的string值
     */
    public static String getValue(String json, String key) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        return jsonObject.getString(key);

    }

    /**
     * 解析动态页面的新闻资讯
     *
     * @param jsonStr   传入json数据
     * @param fieldName 要解析的字段
     */

    public static List<NewInfo> newsParseJsonData(String jsonStr, String fieldName) {
        // 使用 Gson 解析 JSON 字符串，获取指定字段对应的 JsonObject 对象
        JsonObject jsonObj = new Gson().fromJson(jsonStr, JsonObject.class);
        JsonObject fieldObj = jsonObj.getAsJsonObject(fieldName);
        // 获取 data 数组对应的 JsonArray 对象
        JsonArray dataArray = fieldObj.getAsJsonArray("data");
        List<NewInfo> list = new ArrayList<>();//资讯List
        for (int i = 0; i < dataArray.size(); i++) {
            JsonObject dataObj = dataArray.get(i).getAsJsonObject();
            String title = dataObj.get("title").getAsString();
            String category = dataObj.get("category").getAsString();
            String author_name = dataObj.get("author_name").getAsString();
            String newsurl = dataObj.get("url").getAsString();
            String thumbnail_pic_s = dataObj.get("thumbnail_pic_s").getAsString();//新闻图片链接
            String uniquekey = dataObj.get("uniquekey").getAsString();//新闻uniquekey用于获取新闻内容
            list.add(new NewInfo(category, title)
                    .setDetailUrl(newsurl)
                    .setUserName(author_name)
                    .setUniquekey(uniquekey)
                    .setImageUrl(thumbnail_pic_s));
        }
        //设置新闻摘要
        for (NewInfo news : list) {
            String uniquekey = news.getUniquekey();
            //根据key获取摘要
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    OkhttpUtils.get("http://v.juhe.cn/toutiao/content?key=" + newsKey + "&uniquekey=" + uniquekey, new OkHttpCallback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            super.onResponse(call, response);
                            String result1 = JsonOperate.getValue(result, "result");
                            String content = JsonOperate.getValue(result1, "content");
                            content = content.replaceAll("<p.*?>", "").replaceAll("</p>", "");
                            news.setSummary(content);

                        }
                    });
                }
            }.start();

        }
        return list;
    }

}
