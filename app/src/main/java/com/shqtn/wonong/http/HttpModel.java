package com.shqtn.wonong.http;

import com.shqtn.wonong.utils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android on 2017/9/25.
 */

public class HttpModel {

    public static void post(String url, Map<String, String> map, Callback callback) {
        if (map == null) {
            map = new HashMap<>();
            map.put("","");
        }
        LogUtils.i("请求地址" + url);
        LogUtils.i(String.valueOf(map));
        OkHttpUtils.post().url(url)
                .params(map)
                .build()
                .execute(callback);

    }
}
