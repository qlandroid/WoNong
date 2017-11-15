package com.shqtn.wonong;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.shqtn.wonong.utils.IpChangeUtils;
import com.shqtn.wonong.utils.StringUtils;
import com.squareup.okhttp.OkHttpClient;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
        if (StringUtils.isEmpty(ip)) {

        }
        if (StringUtils.isEmpty(port)) {

        }

        MobclickAgent.setCatchUncaughtExceptions(true);

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler();
                Looper.loop();
            }
        }).start();
    }

    private Handler handler;
    public static final String name = "wonong_error.txt";

    public void saveError(final String e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();

                File file = new File(absolutePath, name);
                if (!file.exists()) {
                    try {
                        if (file.createNewFile()) {
                            return;
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                FileOutputStream fileOutputStream = null;
                BufferedWriter bw = null;
                try {
                    fileOutputStream = new FileOutputStream(file, true);
                    bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                    bw.write(e);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    if (bw != null) {
                        try {
                            bw.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

    }
}
