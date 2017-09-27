package com.shqtn.wonong.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.ql.bindview.BindViewUtils;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.shqtn.wonong.R;
import com.shqtn.wonong.ui.widget.BtnViewGroup;
import com.shqtn.wonong.ui.widget.TitleView;
import com.shqtn.wonong.ui.widget.dialog.MessageDialog;
import com.shqtn.wonong.utils.ActivityUtils;
import com.shqtn.wonong.utils.DialogFactory;


/**
 * Created by Administrator on 2017-7-5.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener ,TitleView.OnClickToBackListener ,BtnViewGroup.OnClickListener{

    public static final String PRESENTER = "presenter";
    public static final String LOGIN_CODE = "loginCode";

    public static final String TEXT_FLAG = "textFlag";
    public static final String TEXT_FAILED = "textFailed";
    public static final String TEXT_SUCCESS = "textSuccess";


    private static final long MIN_CLICK_DOUBLE = 300; //过滤 连续点击
    public static final String INTENT_BUNDLE = "bundle";
    public static final String MANIFEST_NO = "manifestNo";
    public static final String BOX_TOTAL_QTY = "boxTotalQty";
    public static final String BOX_LIST = "boxList";

    private long mLastClickTime;//记录上次单击时间
    private BaseFragment currentKJFragment;
    private MessageDialog mMessageDialog;
    private ProgressDialog mProgressDialog;
    private boolean isResume;//判断当前页面是否存在焦点

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorBlack);//通知栏所需颜色
        }
        super.onCreate(savedInstanceState);
        createView();

        BindViewUtils.find(this);
        ActivityUtils.getInstance().addAty(this);
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        ActivityUtils.getInstance().removeAty(this);
        super.onDestroy();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void toBack() {
        finish();
    }


    public void initView() {
    }
    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResume =  false;
    }
    public void initData() {
    }


    @Override
    public void onClick(View v) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - mLastClickTime > MIN_CLICK_DOUBLE) {
            mLastClickTime = clickTime;
            clickWidget(v);
        }
    }

    public boolean isResume(){
        return isResume;
    }

    public void clickWidget(View v) {

    }

    public String getPresenterName(){
        return getBundle().getString(PRESENTER);
    }

    public void putPresenterName(Class clazz ,Bundle bundle){
        bundle.putString(PRESENTER,clazz.getCanonicalName());
    }

    protected abstract void createView();

    public void startActivity(Class aty) {
        Intent intent = new Intent(this, aty);
        startActivity(intent);
    }

    public void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtra(INTENT_BUNDLE, bundle);
        startActivity(intent);
    }

    public Bundle getBundle() {
        Intent intent = getIntent();
        if (intent == null) {
            return null;
        }
        return intent.getBundleExtra(INTENT_BUNDLE);
    }

    public void startActivity(Class clazz, int requestCode) {
        startActivity(clazz, null, requestCode);
    }

    public void startActivity(Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(INTENT_BUNDLE, bundle);
        startActivityForResult(intent, requestCode);
    }
    /*********************************************************************/
    /*********************************************************************/
    /********************
     * 用于隐藏软键盘
     **********************************/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /********************************************************************/

    public void changeFragment(int fm_content, BaseFragment targetFragment) {
        if (targetFragment == null) {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(fm_content, targetFragment, targetFragment.getClass().getName());
        }
        if (currentKJFragment != null && currentKJFragment.isAdded() && !currentKJFragment.isHidden()) {
            transaction.hide(currentKJFragment);
        }
        currentKJFragment = targetFragment;

        transaction.show(targetFragment).commit();
    }

    /**
     * 用于接收信息 扫描到的字符串
     *
     * @param content
     */
    public void onClipContentChange(String content) {

    }

    public void displayMsgDialog(String msg) {
        if (mMessageDialog == null) {
            createMsgDialog();
        }
        if (!isFinishing())
            mMessageDialog.show(msg);
    }

    public void cancelMsgDialog() {
        if (mMessageDialog == null) {
            createMsgDialog();
        }
        mMessageDialog.cancel();
    }

    private void createMsgDialog() {
        mMessageDialog = DialogFactory.createMessageDialog(this);
    }

    public void displayProgressDialog(String msg) {
        if (mProgressDialog == null) {
            createProgressDialog();
        }
        if (!isFinishing()) {
            mProgressDialog.setMessage(msg);
            mProgressDialog.show();
        }
    }

    public void cancelProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
    }

    private void createProgressDialog() {
        mProgressDialog = DialogFactory.createProgressDialog(this);
    }

    public void clickBack(){
        finish();
    }

    @Override
    public void onClickBtn(int tag) {
        switch (tag){
            case BtnViewGroup.TAG_BACK:
                clickBack();
                break;
            case BtnViewGroup.TAG_TO_LOGIN:
                clickToLogin();
                break;
            default:
                onOtherBtnClick(tag);
                break;
        }
    }

    public void clickToLogin() {

    }

    public void putTextFailed(Bundle bundle){
        bundle.putString(TEXT_FLAG,TEXT_FAILED);
    }

    public void putTextSuccess(Bundle bundle){
        bundle.putString(TEXT_FLAG,TEXT_SUCCESS);
    }
    public String getTextFlag(){
        if (getBundle() ==  null){
            return null;
        }
        return getBundle().getString(TEXT_FLAG);
    }


    public void onOtherBtnClick(int tag) {

    }
}
