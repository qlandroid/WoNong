package com.shqtn.wonong.utils;

import android.content.Context;

/**
 * Created by android on 2017/8/14.
 */

public class LoginCodeUtils {
    private static final String FILE_NAME = "login_code";
    private static final String KEY_CODE = "KEY_CODE";

    public static void save(Context context, String code) {
        PreferencesUtils.saveString(context, FILE_NAME, KEY_CODE, code);
    }

    public static void delete(Context context) {
        PreferencesUtils.deleteFile(context, FILE_NAME);
    }

    public static String queryLoginCode(Context context) {
        return PreferencesUtils.queryString(context, FILE_NAME, KEY_CODE);
    }
}
