package com.shqtn.wonong.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.shqtn.wonong.ui.widget.dialog.AskDialog;
import com.shqtn.wonong.ui.widget.dialog.MessageDialog;


/**
 * Created by android on 2017/7/11.
 */

public class DialogFactory {

    public static MessageDialog createMessageDialog(Context context) {
        MessageDialog msgDialog = new MessageDialog(context);
        return msgDialog;
    }

    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        return dialog;
    }

    public static AskDialog createAskDialog(Context context) {
        AskDialog askDialog = new AskDialog(context);
        return askDialog;
    }
}
