package com.shqtn.wonong.ui.activity;

import android.content.Context;
import android.ql.bindview.BindView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.shqtn.wonong.C;
import com.shqtn.wonong.R;
import com.shqtn.wonong.bean.ManifestDetails;
import com.shqtn.wonong.bean.ManifestNo;
import com.shqtn.wonong.bean.Result;
import com.shqtn.wonong.http.HttpModel;
import com.shqtn.wonong.info.ApiUrl;
import com.shqtn.wonong.ui.adapter.CommonAdapter;
import com.shqtn.wonong.ui.base.BaseActivity;
import com.shqtn.wonong.utils.DataUtils;
import com.shqtn.wonong.utils.ResultUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManifestListActivity extends BaseActivity {

    private static final String TAG = "ManifestListActivity";
    @BindView(R.id.manifest_list)
    ListView lv;
    @BindView(R.id.manifest_list_et_inputManifest)
    EditText etInputManifest;
    @BindView(R.id.manifest_list_ib_search)
    ImageButton ib;

    private CommonAdapter<ManifestNo> mManifestListAdapter;
    private List<ManifestNo> mManifestList;

    @Override
    protected void createView() {
        setContentView(R.layout.activity_maniest_list);
    }


    @Override
    public void initData() {
        super.initData();
        mManifestListAdapter = new CommonAdapter<ManifestNo>(this, null, R.layout.item_manifest) {
            @Override
            public void setItemContent(ViewHolder holder, ManifestNo manifestNo, int position) {
                holder.setText(R.id.item_manifest_tv_manifestNo, manifestNo.getCcode());
            }
        };
    }

    @Override
    public void initView() {
        super.initView();
        lv.setAdapter(mManifestListAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                searchManifestDetails(mManifestList.get(i).getCcode());
            }
        });

        etInputManifest.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO
                        || (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        || event.getKeyCode() == KeyEvent.KEYCODE_MEDIA_NEXT))) {
                    //隐藏软键盘

                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }
                    toSearch();
                    return true;
                }
                return false;
            }


        });

        ib.setOnClickListener(this);

        displayProgressDialog("加载数据中");
        HttpModel.post(ApiUrl.MANIFEST_LIST, null, new StringCallback() {
            @Override
            public void onAfter() {
                super.onAfter();
                cancelProgressDialog();
            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof SocketTimeoutException) {
                    displayMsgDialog("连接超时");
                } else if (e instanceof SocketException) {
                    displayMsgDialog("连接异常");
                } else {
                    displayMsgDialog("连接异常");
                }
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response) {
                String replace = ResultUtils.replace(response);
                Log.i(TAG, "onResponse: " + replace);
                Result re = C.getGson().fromJson(replace, Result.class);
                if (Result.SUCCESS.equals(re.getMescode())) {
                    ArrayList<ManifestNo> arrayResult = DataUtils.getArrayResult(re.getData(), ManifestNo.class);
                    mManifestList = arrayResult;
                    mManifestListAdapter.update(arrayResult);
                } else {
                    displayMsgDialog(re.getMesmessage());
                }
            }
        });
    }

    private void toSearch() {
        String s = etInputManifest.getText().toString();
        if (s == null || s.isEmpty()) {
            return;
        }
        searchManifestDetails(s);
    }

    private void searchManifestDetails(String s) {
        displayProgressDialog("查询详情中");
        Map<String, String> map = new HashMap<>();
        map.put("code", s);
        HttpModel.post(ApiUrl.MANIFEST_DEATILS, map, new StringCallback() {
            @Override
            public void onAfter() {
                super.onAfter();
                cancelProgressDialog();
            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof SocketTimeoutException) {
                    displayMsgDialog("连接超时");
                } else if (e instanceof SocketException) {
                    displayMsgDialog("连接异常");
                } else {
                    displayMsgDialog("连接异常");
                }
            }

            @Override
            public void onResponse(String response) {
                String replace = ResultUtils.replace(response);
                Log.i(TAG, "onResponse: " + replace);
                Result re = C.getGson().fromJson(replace, Result.class);
                if (Result.SUCCESS.equals(re.getMescode())) {
                    ArrayList<ManifestDetails> arrayResult = DataUtils.getArrayResult(re.getData(), ManifestDetails.class);
                    if (arrayResult == null || arrayResult.size() == 0) {
                        displayMsgDialog("未找到任务单据或当前任务单据下无内容");
                    } else {

                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(C.MANIFEST_DETAILS, arrayResult);
                        startActivity(ManifestDetailsActivity.class, bundle);
                    }


                } else {
                    displayMsgDialog(re.getMesmessage());
                }
            }
        });
    }

    @Override
    public void clickWidget(View v) {
        super.clickWidget(v);
        switch (v.getId()) {
            case R.id.manifest_list_ib_search:
                toSearch();
                break;
        }
    }
}
