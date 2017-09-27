package com.shqtn.wonong;

import android.app.Application;
import android.content.Context;

import com.shqtn.wonong.utils.IpChangeUtils;
import com.shqtn.wonong.utils.StringUtils;
import com.squareup.okhttp.OkHttpClient;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by android on 2017/8/14.
 */

public class MyApp extends Application {

    private static MyApp mInstance;

    public static MyApp getInstance() {
        return mInstance;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        OkHttpClient client =
                OkHttpUtils.getInstance().getOkHttpClient();
        client.setWriteTimeout(1, TimeUnit.MINUTES);
        client.setReadTimeout(1, TimeUnit.MINUTES);
        client.setConnectTimeout(1, TimeUnit.MINUTES);

        String ip = IpChangeUtils.getIp(this);
        String port = IpChangeUtils.getPort(this);
        if (StringUtils.isEmpty(ip)){

        }
        if (StringUtils.isEmpty(port)){

        }
    }


}
