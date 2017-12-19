package com.shqtn.wonong.utils;

import android.util.Log;

/**
 * Created by android on 2017/9/26.
 */

public class ResultUtils {
    private static final String TAG = "ResultUtils";

    public static String replace(String response) {
        Log.i(TAG, "replace: " + response);
        response = response.replace("\\", "");
        response = response.replaceFirst("\"", "");
        response = response.substring(0, response.length() - 1);
        return response;
    }
}
