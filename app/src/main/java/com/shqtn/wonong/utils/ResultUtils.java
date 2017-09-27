package com.shqtn.wonong.utils;

/**
 * Created by android on 2017/9/26.
 */

public class ResultUtils {
    public static String replace(String response) {
        response = response.replace("\\", "");
        response = response.replaceFirst("\"", "");
        response = response.substring(0, response.length() - 1);
        return response;
    }
}
