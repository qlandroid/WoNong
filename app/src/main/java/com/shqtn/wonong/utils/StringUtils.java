package com.shqtn.wonong.utils;

/**
 * Created by android on 2017/8/31.
 */

public class StringUtils {

    public static boolean isEmpty(String s){
        if (s == null || s.trim().isEmpty()) {
            return true;
        }
        return false;
    }
}
