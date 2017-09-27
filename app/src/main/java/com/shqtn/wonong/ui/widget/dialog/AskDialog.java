package com.shqtn.wonong.ui.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by android on 2017/8/4.
 */

public class AskDialog {


    private AlertDialog ad;
    private OnAskDialogClickListener mOnAskDialogClickListener;

    public AskDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        ad = new AlertDialog.Builder(context)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mOnAskDialogClickListener != null) {
                            mOnAskDialogClickListener.onClickCancel(AskDialog.this);
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mOnAskDialogClickListener != null) {
                            mOnAskDialogClickListener.onClickTrue(AskDialog.this);
                        }
                    }
                })
                .setCancelable(false)
                .create();

    }

    public AskDialog setMessage(String msg) {
        ad.setMessage(msg);
        return this;
    }

    public AskDialog setTitle(String title) {
        ad.setTitle(title);
        return this;
    }

    public AskDialog setOnClickListener(OnAskDialogClickListener l) {
        mOnAskDialogClickListener = l;
        return this;
    }


    public void show() {
        ad.show();
    }

    public void cancel() {
        ad.cancel();
    }

    public interface OnAskDialogClickListener {
        void onClickTrue(AskDialog askDialog);

        void onClickCancel(AskDialog askDialog);
    }
}
