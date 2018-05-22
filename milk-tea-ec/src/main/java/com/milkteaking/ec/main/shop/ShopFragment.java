package com.milkteaking.ec.main.shop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.milkteaking.core.fragments.bottom.BottomItemFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.util.log.MilkTeaLogger;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.constant.Constant;
import com.milkteaking.ui.recycler.MultipleItemBean;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author TanJJ
 * @time 2018/5/21 16:04
 * @des 购物车Fragment
 */

public class ShopFragment extends BottomItemFragment {
    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mSelectAll;
    private ShopCarAdapter mAdapter;

    @Override
    public Object getLayout() {
        return R.layout.fragment_shop;
    }

    @OnClick(R2.id.icon_shop_cart_select_all)
    public void selectAll() {
        int tag = (int) mSelectAll.getTag();
        // 反选
        switch (tag) {
            case 0:
                mSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
                mAdapter.setSelectAll(true);
                mSelectAll.setTag(1);
                break;
            case 1:
                mSelectAll.setTextColor(Color.GRAY);
                mAdapter.setSelectAll(false);
                mSelectAll.setTag(0);
                break;
            default:
                break;
        }
        // 更新RecyclerView状态
        mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
    }


    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        // 设置tag
        mSelectAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRecycler();
        initData();
    }

    private void initData() {
        // 请求数据
        RestClient.builder()
                .url(Constant.SHOP_INDEX)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LinkedList<MultipleItemBean> convert = new ShopCarDataConvert().setJson(response).convert();
                        mAdapter = new ShopCarAdapter(convert);
                        mRecyclerView.setAdapter(mAdapter);
                        // 只要设置为false，就可以不显示动画了
                        ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                    }
                })
                .failed(new IFailed() {
                    @Override
                    public void onFailed(Throwable t) {
                        MilkTeaLogger.e(ShopFragment.class.getSimpleName(), t.getMessage());
                    }
                })
                .build()
                .get();
    }

    private void initRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }
}
