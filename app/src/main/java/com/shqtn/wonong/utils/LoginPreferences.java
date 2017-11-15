package com.shqtn.wonong.utils;

import android.content.Context;

/**
 * Created by android on 2017/11/15.
 */

public class LoginPreferences {
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";

    public static void save(Context context, String account, String pw) {
        PreferencesUtils.saveString(context, ACCOUNT, account);
        PreferencesUtils.saveString(context, PASSWORD, pw);
    }

    public static String getAccount(Context context) {
        return PreferencesUtils.queryString(context, ACCOUNT);
    }

    public static String getPassword(Context context) {
        return PreferencesUtils.queryString(context, PASSWORD);
    }


}
