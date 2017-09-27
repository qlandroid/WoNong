package com.shqtn.wonong.bean;

import java.util.List;

/**
 * Created by android on 2017/9/26.
 */

public class Result {

    /*
    {
"mescode":"200",
"mesmessage":"登陆成功！"
}

     */

    public static final String SUCCESS = "200";

    private String mescode;

    private String mesmessage;
    private List data;

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public String getMescode() {
        return mescode;
    }

    public void setMescode(String mescode) {
        this.mescode = mescode;
    }

    public String getMesmessage() {
        return mesmessage;
    }

    public void setMesmessage(String mesmessage) {
        this.mesmessage = mesmessage;
    }
}
