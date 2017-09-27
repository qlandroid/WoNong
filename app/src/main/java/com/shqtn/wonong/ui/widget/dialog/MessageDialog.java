package com.shqtn.wonong.ui.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.lang.ref.SoftReference;

/**
 * Created by Administrator on 2017-7-10.
 */
public class MessageDialog {
    private static final String NORMAL_TITLE = "提示信息";
    private AlertDialog alertDialog;

    private SoftReference<Context> mContext;

    public MessageDialog(Context context) {
        mContext = new SoftReference<Context>(context);
    }

    public void show(String content) {
        show(NORMAL_TITLE, content);
    }

    public void show(String title, String content) {
        if (alertDialog == null) {
            Context context = mContext.get();
            if (content == null) return;
            alertDialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(content)
                    .setNegativeButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .create();
            alertDialog.setCancelable(false);
        } else {
            alertDialog.setMessage(content);
        }
        alertDialog.show();
    }

    public void cancel() {
        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.cancel();
    }

}
