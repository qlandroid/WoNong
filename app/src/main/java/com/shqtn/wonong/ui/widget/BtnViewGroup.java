package com.shqtn.wonong.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.shqtn.wonong.R;


/**
 * Created by android on 2017/8/30.
 */

public class BtnViewGroup extends LinearLayout implements View.OnClickListener {
    public static final int TAG_BACK = 0X0021;
    public static final int TAG_TO_LOGIN = 0x1231;

    private OnClickListener mOnClickListener;

    public BtnViewGroup(Context context) {
        super(context);
        this.init(context, null, 0);
    }

    public BtnViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs, 0);
    }


    public BtnViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.setOrientation(LinearLayout.HORIZONTAL);
        String back = context.getString(R.string.back);
        String backToLogin = context.getString(R.string.backToLogin);
        addChildrenBtn(context, back, TAG_BACK);
        addChildrenBtn(context, backToLogin, TAG_TO_LOGIN);

    }
    public void addChildrenBtn(int btnName, int tag) {
        removeAllViews();
        addChildrenBtn(getContext(), btnName, tag);
        String back = getContext().getString(R.string.back);
        String backToLogin = getContext().getString(R.string.backToLogin);
        addChildrenBtn(getContext(), back, TAG_BACK);
        addChildrenBtn(getContext(), backToLogin, TAG_TO_LOGIN);
    }

    public void addChildrenBtn(String btnName, int tag) {
        removeAllViews();
        addChildrenBtn(getContext(), btnName, tag);
        String back = getContext().getString(R.string.back);
        String backToLogin = getContext().getString(R.string.backToLogin);
        addChildrenBtn(getContext(), back, TAG_BACK);
        addChildrenBtn(getContext(), backToLogin, TAG_TO_LOGIN);
    }


    private void addChildrenBtn(Context context, String btnName, int tag) {
        Button button = new Button(context);
        button.setText(btnName);
        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        button.setLayoutParams(lp);
        button.setTag(tag);
        button.setOnClickListener(this);
        addView(button);
    }
    private void addChildrenBtn(Context context, int btnName, int tag) {
        Button button = new Button(context);
        button.setText(btnName);
        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        button.setLayoutParams(lp);
        button.setTag(tag);
        button.setOnClickListener(this);
        addView(button);
    }



    public void setOnChildrenButtonClickListener(OnClickListener l) {
        this.mOnClickListener = l;
    }

    @Override
    public void onClick(View view) {
        if (mOnClickListener != null) {
            int tag = (int) view.getTag();
            mOnClickListener.onClickBtn(tag);
        }


    }

    public interface OnClickListener {
        void onClickBtn(int tag);
    }
}
