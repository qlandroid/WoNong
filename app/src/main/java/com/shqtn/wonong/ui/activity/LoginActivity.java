package com.shqtn.wonong.ui.activity;

import android.ql.bindview.BindView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shqtn.wonong.C;
import com.shqtn.wonong.R;
import com.shqtn.wonong.bean.Result;
import com.shqtn.wonong.info.ApiUrl;
import com.shqtn.wonong.ui.base.BaseActivity;
import com.shqtn.wonong.utils.StringUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.login_btn_login)
    Button btnLogin;
    @BindView(R.id.login_et_account)
    EditText etAccount;
    @BindView(R.id.login_et_pw)
    EditText etPw;
    @BindView(R.id.login_tv_change_ip)
    TextView tvChangeIp;

    private String mLoginUrl;

    @Override
    protected void createView() {
        setContentView(R.layout.activity_login);

    }

    @Override
    public void initView() {
        super.initView();
        btnLogin.setOnClickListener(this);
        tvChangeIp.setOnClickListener(this);
    }

    @Override
    public void clickWidget(View v) {
        super.clickWidget(v);
        switch (v.getId()) {
            case R.id.login_btn_login:
                if (StringUtils.isEmpty(ApiUrl.BASE_URL)) {
                    displayMsgDialog("请设置ip");
                    return;
                }
                String account = etAccount.getText().toString();
                String pw = etPw.getText().toString();
                if (StringUtils.isEmpty(account)) {
                    displayMsgDialog("请填写账号");
                    return;
                }
                if (StringUtils.isEmpty(pw)) {
                    displayMsgDialog("请填写密码");
                    return;
                }

                displayProgressDialog("登陆中");
                OkHttpUtils.post().addParams("user", etAccount.getText().toString())
                        .addParams("password", etPw.getText().toString())
                        .url(ApiUrl.LOGIN)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onAfter() {
                                super.onAfter();
                                cancelProgressDialog();
                            }

                            @Override
                            public void onError(Request request, Exception e) {
                                ByteArrayOutputStream byteArrayInputStream = null;
                                if (e != null) {
                                    byteArrayInputStream = new ByteArrayOutputStream();
                                    e.printStackTrace(new PrintStream(byteArrayInputStream));
                                }
                                e.printStackTrace();
                                if (e instanceof UnknownHostException) {
                                    displayMsgDialog("端口号异常");
                                } else if (e instanceof SocketTimeoutException) {
                                    displayMsgDialog("连接超时");
                                } else if (e instanceof SocketException) {


                                    displayMsgDialog("连接异常" + byteArrayInputStream.toString());
                                } else {

                                    displayMsgDialog("连接异常");
                                }

                                if (byteArrayInputStream != null) {
                                    try {
                                        byteArrayInputStream.close();
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onResponse(String response) {
                                response = response.replace("\\", "");
                                response = response.replaceFirst("\"", "");
                                response = response.substring(0, response.length() - 1);
                                Log.i(TAG, "onResponse: " + response);
                                Result result = C.getGson().fromJson(response, Result.class);
                                if (Result.SUCCESS.equals(result.getMescode())) {
                                    startActivity(ManifestListActivity.class);
                                    finish();
                                } else {
                                    displayMsgDialog(result.getMesmessage());
                                }
                            }
                        });
                break;
            case R.id.login_tv_change_ip:
                startActivity(IpChangeActivity.class);
                break;
            default:
                break;
        }
    }


}
