package com.milkteaking.ec.main.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;

import butterknife.BindView;

/**
 * @author TanJJ
 * @time 2018/6/17 17:35
 * @des 商品详情信息Fragment
 */

public class GoodsInfoDetailFragment extends MilkTeaFragment {

    private static final String ARG_GOODS_INFO_DATA = "ARG_GOODS_INFO_DATA";
    @BindView(R2.id.tv_goods_info_title)
    AppCompatTextView mGoodsInfoTitle;
    @BindView(R2.id.tv_goods_info_desc)
    AppCompatTextView mGoodsInfoDesc;
    @BindView(R2.id.tv_goods_info_price)
    AppCompatTextView mGoodsInfoPrice;
    private JSONObject mInfoData;

    @Override
    public Object getLayout() {
        return R.layout.fragment_goods_info;
    }

    public static GoodsInfoDetailFragment create(String dataInfo) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_GOODS_INFO_DATA, dataInfo);
        GoodsInfoDetailFragment goodsInfoDetailFragment = new GoodsInfoDetailFragment();
        goodsInfoDetailFragment.setArguments(bundle);
        return goodsInfoDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString(ARG_GOODS_INFO_DATA);
            mInfoData = JSON.parseObject(string);
        }
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        // 取出数据
        String name = mInfoData.getString("name");
        mGoodsInfoTitle.setText(name);
        String description = mInfoData.getString("description");
        mGoodsInfoDesc.setText(description);
        Float price = mInfoData.getFloat("price");
        mGoodsInfoPrice.setTag(String.valueOf(price));
    }
}
