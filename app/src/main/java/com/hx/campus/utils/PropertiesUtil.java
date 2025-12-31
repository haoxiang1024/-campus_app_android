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
