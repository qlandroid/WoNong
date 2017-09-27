package com.shqtn.wonong.ui.activity;

import android.ql.bindview.BindView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shqtn.wonong.R;
import com.shqtn.wonong.ui.base.BaseActivity;
import com.shqtn.wonong.ui.widget.TitleView;
import com.shqtn.wonong.utils.IpChangeUtils;
import com.shqtn.wonong.utils.StringUtils;

public class IpChangeActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.ip_change_et_ip)
    EditText etIp;
    @BindView(R.id.ip_change_et_port)
    EditText etPort;
    @BindView(R.id.ip_chenge_btn_yes)
    Button btnYes;


    @Override
    protected void createView() {
        setContentView(R.layout.activity_ip_change);
    }


    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initView() {
        super.initView();

        titleView.setOnToBackClickListener(this);

        String ip = IpChangeUtils.getIp(this);
        String port = IpChangeUtils.getPort(this);
        etIp.setText(ip);
        etPort.setText(port);
        btnYes.setOnClickListener(this);
    }

    @Override
    public void clickWidget(View v) {
        super.clickWidget(v);
        switch (v.getId()) {
            case R.id.ip_chenge_btn_yes:
                String ip = etIp.getText().toString();
                String port = etPort.getText().toString();

                if (StringUtils.isEmpty(ip)) {
                    displayMsgDialog("请填写ip");
                    return;
                }
                IpChangeUtils.saveIp(this, ip);

                if (!StringUtils.isEmpty(port)) {
                    IpChangeUtils.savePort(this, port);
                }
                IpChangeUtils.changeIp(ip, port);
                finish();
                break;
        }
    }
}
