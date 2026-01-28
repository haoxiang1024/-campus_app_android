
package com.hx.campus.utils;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    Properties properties = new Properties();

    //加载url属性文件
    public Properties LoadProperties(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("url.properties");
            properties.load(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }


}
