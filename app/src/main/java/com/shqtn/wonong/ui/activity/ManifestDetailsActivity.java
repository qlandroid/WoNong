package com.shqtn.wonong.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.ql.bindview.BindView;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shqtn.wonong.C;
import com.shqtn.wonong.R;
import com.shqtn.wonong.bean.ManifestDetails;
import com.shqtn.wonong.bean.Result;
import com.shqtn.wonong.info.ApiUrl;
import com.shqtn.wonong.ui.adapter.CommonAdapter;
import com.shqtn.wonong.ui.base.BaseActivity;
import com.shqtn.wonong.ui.widget.LabelTextView;
import com.shqtn.wonong.ui.widget.TitleView;
import com.shqtn.wonong.utils.ResultUtils;
import com.shqtn.wonong.utils.ToastUtils;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelector;

public class ManifestDetailsActivity extends BaseActivity {


    private static final String TAG = "MainActivity";
    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    public static final int REQUEST_EDIT_MSG = 3;

    int SAVE_IMAGE_MAX_SIZE = 18;
    @BindView(R.id.ltv_gongyingshang__name)
    LabelTextView ltvGongYIingShangName;
    @BindView(R.id.ltv_goods_name)
    LabelTextView ltvGoodsName;
    @BindView(R.id.ltv_goods_qty)
    LabelTextView ltvGoodsQty;
    @BindView(R.id.ltv_manifest_no)
    LabelTextView ltvManifestNo;
    @BindView(R.id.ltv_work_name)
    LabelTextView ltvWorkName;
    @BindView(R.id.grid)
    GridView gridView;
    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.manifest_details_submit)
    Button btnSubmit;
    @BindView(R.id.activity_manifest_details_btn_to_add_message)
    Button btnToAddMesasge;
    @BindView(R.id.activity_manifest_details_btn_to_see_more_data)
    Button btnToSeeMoreData;
    @BindView(R.id.activity_manifest_details_tv_message)
    TextView tvMessage;

    private ArrayList<ManifestDetails> mManifestList;
    private ManifestDetails mFirstManifestDetails;
    CommonAdapter<String> mSelectImageAdapter;

    private ArrayList<String> mSelectPath;
    private ArrayList<String> mShowImagePath = new ArrayList<>();
    private int itemWidth;

    Transformation transformation = new Transformation() {

        @Override
        public Bitmap transform(Bitmap source) {

            int targetWidth = itemWidth;
            Log.i(TAG, "source.getHeight()=" + source.getHeight() + ",source.getWidth()=" + source.getWidth() + ",targetWidth=" + targetWidth);

            if (source.getWidth() == 0) {
                return source;
            }

            //如果图片小于设置的宽度，则返回原图
            if (source.getWidth() < targetWidth) {
                return source;
            } else {
                //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                if (targetHeight != 0 && targetWidth != 0) {
                    Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                    if (result != source) {
                        // Same bitmap is returned if sizes are the same
                        source.recycle();
                    }
                    return result;
                } else {
                    return source;
                }
            }

        }

        @Override
        public String key() {
            return "transformation" + " desiredWidth";
        }
    };

    @Override
    protected void createView() {
        setContentView(R.layout.activity_manifest_details);
    }


    @Override
    public void initData() {
        super.initData();
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        itemWidth = (widthPixels - 20) / 5;

        mManifestList = getBundle().getParcelableArrayList(C.MANIFEST_DETAILS);
        mFirstManifestDetails = mManifestList.get(0);

        mSelectImageAdapter = new CommonAdapter<String>(this, null, R.layout.item_image) {
            @Override
            public void setItemContent(ViewHolder holder, String s, int position) {

                ViewGroup.LayoutParams layoutParams = holder.getConvertView().getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new ViewGroup.LayoutParams(itemWidth, itemWidth);
                }

                layoutParams.width = itemWidth;
                layoutParams.height = itemWidth;

                holder.getConvertView().setLayoutParams(layoutParams);


                if ("-1".equals(s)) {
                    Picasso.with(ManifestDetailsActivity.this).load(R.drawable.icon_image_add).transform(transformation).into((ImageView) holder.getViewById(R.id.item_image));
                } else {
                    Picasso.with(ManifestDetailsActivity.this).load(new File(s)).transform(transformation).into((ImageView) holder.getViewById(R.id.item_image));
                }
            }
        };
    }

    @Override
    public void initView() {
        super.initView();
        titleView.setOnToBackClickListener(this);

        btnToAddMesasge.setOnClickListener(this);
        btnToSeeMoreData.setOnClickListener(this);

        String ccode = mFirstManifestDetails.getCcode();
        if (!TextUtils.isEmpty(ccode)) {
            ltvManifestNo.setText(ccode);
        }

        String cvenname = mFirstManifestDetails.getCvenname();
        if (!TextUtils.isEmpty(cvenname)) {
            ltvGongYIingShangName.setText(cvenname);
        }

        String iquantity = mFirstManifestDetails.getIquantity();
        if (!TextUtils.isEmpty(iquantity)) {
            ltvGoodsQty.setText(iquantity);
        }


        String cinvname = mFirstManifestDetails.getCinvname();
        if (!TextUtils.isEmpty(cinvname)) {
            ltvGoodsName.setText(cinvname);
        }

        String cdepcode = mFirstManifestDetails.getCdepcode();
        if (!TextUtils.isEmpty(cdepcode)) {
            ltvWorkName.setText(cdepcode);
        }

        gridView.setAdapter(mSelectImageAdapter);
        gridView.setNumColumns(4);
        btnSubmit.setOnClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openCamare();
            }
        });

        mShowImagePath.add("-1");
        mSelectImageAdapter.update(mShowImagePath);
    }

    @Override
    public void clickWidget(View v) {
        super.clickWidget(v);
        switch (v.getId()) {
            case R.id.manifest_details_submit:
                upload();
                break;
            case R.id.activity_manifest_details_btn_to_add_message:
                startActivity(EditMessageActivity.class, REQUEST_EDIT_MSG);
                break;
            case R.id.activity_manifest_details_btn_to_see_more_data:
                startActivity(GoodsListActivity.class, getBundle());
                break;
            default:
        }
    }


    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            int maxNum = SAVE_IMAGE_MAX_SIZE;


            MultiImageSelector selector = MultiImageSelector.create(ManifestDetailsActivity.this);
            selector.showCamera(false);
            selector.count(maxNum);
            // selector.single();
            selector.multi();
            selector.origin(mSelectPath);
            selector.start(ManifestDetailsActivity.this, REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ManifestDetailsActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                if (mSelectPath != null) {
                    mShowImagePath.clear();
                    mShowImagePath.addAll(mSelectPath);

                    if (mSelectPath.size() < SAVE_IMAGE_MAX_SIZE) {
                        mShowImagePath.add("-1");
                    }
                }
                mSelectImageAdapter.update(mShowImagePath);
            }
        } else if (REQUEST_EDIT_MSG == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                String message = data.getStringExtra("message");
                tvMessage.setText(message);
            }
        }
    }

    public void openCamare() {
        pickImage();
    }


    public void upload() {

        if (mSelectPath == null || mSelectPath.size() == 0) {

            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("code", mFirstManifestDetails.getCcode());
        map.put("cdefine", tvMessage.getText().toString());
        PostFormBuilder params = OkHttpUtils.post().params(map);

        for (String s : mSelectPath) {
            params.addFile("test", s, new File(s));
        }


        params.url(ApiUrl.FILE_UPDATE)
                .build().execute(new StringCallback() {
            @Override
            public void onAfter() {
                super.onAfter();
                cancelProgressDialog();
            }

            @Override
            public void inProgress(float progress) {
                super.inProgress(progress);
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
                Result re = C.getGson().fromJson(replace, Result.class);
                if (Result.SUCCESS.equals(re.getMescode())) {
                    ToastUtils.show(ManifestDetailsActivity.this, "提交成功");
                } else {
                    displayMsgDialog(re.getMesmessage());
                }
            }
        });
    }
}
