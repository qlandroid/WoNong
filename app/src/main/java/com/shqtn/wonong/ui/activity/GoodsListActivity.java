package com.shqtn.wonong.ui.activity;

import android.ql.bindview.BindView;
import android.text.TextUtils;
import android.widget.ListView;

import com.shqtn.wonong.C;
import com.shqtn.wonong.R;
import com.shqtn.wonong.bean.ManifestDetails;
import com.shqtn.wonong.ui.adapter.CommonAdapter;
import com.shqtn.wonong.ui.base.BaseActivity;
import com.shqtn.wonong.ui.widget.TitleView;

import java.util.ArrayList;

public class GoodsListActivity extends BaseActivity {


    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.goods_list)
    ListView lvGoods;

    private ArrayList<ManifestDetails> mGoods;

    private CommonAdapter<ManifestDetails> adapter;

    @Override
    protected void createView() {
        setContentView(R.layout.activity_goods_list);
    }

    @Override
    public void initData() {
        super.initData();
        mGoods = getBundle().getParcelableArrayList(C.MANIFEST_DETAILS);

        adapter = new CommonAdapter<ManifestDetails>(this, mGoods, R.layout.item_goods) {
            @Override
            public void setItemContent(ViewHolder holder, ManifestDetails manifestDetails, int position) {
                String ccode = manifestDetails.getCcode();
                if (!TextUtils.isEmpty(ccode)) {
                    holder.setLabel(R.id.ltv_manifest_no, ccode);
                }
                String cvenname = manifestDetails.getCvenname();
                if (!TextUtils.isEmpty(cvenname)) {
                    holder.setLabel(R.id.ltv_gongyingshang__name, cvenname);
                }
                String iquantity = manifestDetails.getIquantity();
                if (!TextUtils.isEmpty(iquantity)) {
                    holder.setLabel(R.id.ltv_goods_qty, iquantity);
                }

                String cinvname = manifestDetails.getCinvname();
                if (!TextUtils.isEmpty(cinvname)) {
                    holder.setLabel(R.id.ltv_goods_name, cinvname);
                }

                String cdepcode = manifestDetails.getCdepcode();
                if (!TextUtils.isEmpty(cdepcode)) {
                    holder.setLabel(R.id.ltv_work_name, cdepcode);
                }
            }
        };
    }

    @Override
    public void initView() {
        super.initView();
        titleView.setOnToBackClickListener(this);

        lvGoods.setAdapter(adapter);

    }
}
