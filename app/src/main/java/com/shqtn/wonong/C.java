package com.shqtn.wonong;

import com.google.gson.Gson;

/**
 * Created by android on 2017/9/26.
 */

public class C {
    public static final Gson sGosn = new Gson();

    public static Gson getGson() {
        return sGosn;
    }

    public static final String MANIFEST_DETAILS = "manifest_details";
}
