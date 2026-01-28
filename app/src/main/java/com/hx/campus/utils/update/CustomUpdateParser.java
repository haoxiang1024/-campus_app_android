
package com.hx.campus.utils.update;

import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.proxy.impl.AbstractUpdateParser;


public class CustomUpdateParser extends AbstractUpdateParser {

    @Override
    public UpdateEntity parseJson(String json) throws Exception {
        // TODO: 2020-02-18 这里填写你需要自定义的json格式，如果使用默认的API就不需要设置
        return null;
    }

}
