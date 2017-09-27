package com.shqtn.wonong;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.ql.bindview.BindView;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shqtn.wonong.info.ApiUrl;
import com.shqtn.wonong.ui.activity.LoginActivity;
import com.shqtn.wonong.ui.base.BaseActivity;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.nereo.multi_image_selector.MultiImageSelector;

public class MainActivity extends BaseActivity {


    private ArrayList<String> mSelectPath;

    @Override
    protected void createView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initData() {
        super.initData();
        startActivity(LoginActivity.class);
        finish();
    }


}

