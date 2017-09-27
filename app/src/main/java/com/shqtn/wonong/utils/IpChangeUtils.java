package com.shqtn.wonong.utils;

import android.content.Context;

import com.shqtn.wonong.info.ApiUrl;


/**
 * Created by android on 2017/7/12.
 */

public class IpChangeUtils {

    private static final String FILE_NAME = "ip";
    private static final String KEY_IP = "KEY_IP";
    private static final String KEY_PORT = "KEY_PORT";


    public static void saveIp(Context context, String ip) {
        PreferencesUtils.saveString(context, FILE_NAME, KEY_IP, ip);
    }

    public static void savePort(Context context, String port) {
        PreferencesUtils.saveString(context, FILE_NAME, KEY_PORT, port);
    }

    public static String getIp(Context context) {
        return PreferencesUtils.queryString(context, FILE_NAME, KEY_IP);
    }

    public static String getPort(Context context) {
        return PreferencesUtils.queryString(context, FILE_NAME, KEY_PORT);
    }

    public static void changeIp(String ip, String port) {
        ApiUrl.changeBase(ip,port);
    }
}
