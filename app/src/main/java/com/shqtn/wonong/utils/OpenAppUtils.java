package com.shqtn.wonong.utils;

import android.content.Context;

/**
 * Created by android on 2017/8/31.
 */

public class OpenAppUtils {

    public static final String FILE_NAME = "openApp";

    private static final String KEY_IS_FISRT_OPEN ="firstOpen";

    public static void save(Context context,boolean isFirstOpen){
        PreferencesUtils.saveBoolean(context,FILE_NAME,KEY_IS_FISRT_OPEN,isFirstOpen);
    }

    public static boolean isFirstOpen(Context context){
        return PreferencesUtils.queryBoolean(context,FILE_NAME,KEY_IS_FISRT_OPEN,true);
    }
}
