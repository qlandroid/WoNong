package com.shqtn.wonong.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.ql.bindview.BindView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shqtn.wonong.R;
import com.shqtn.wonong.ui.base.BaseActivity;
import com.shqtn.wonong.ui.widget.TitleView;

public class EditMessageActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.activity_edit_message_btn_yes)
    Button btnYes;
    @BindView(R.id.activity_edit_message_et_message)
    EditText etMessage;


    @Override
    protected void createView() {
        setContentView(R.layout.activity_edit_message);
    }

    @Override
    public void initView() {
        super.initView();
        titleView.setOnToBackClickListener(this);
        btnYes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_edit_message_btn_yes:
                String message = etMessage.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("message", message);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            default:
        }
    }
}
