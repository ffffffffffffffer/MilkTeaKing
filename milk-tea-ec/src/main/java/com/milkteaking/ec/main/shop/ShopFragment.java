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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    public void removeSelected() {
        // 获取数据
        List<MultipleItemBean> data = mAdapter.getData();
        // 记录要被删除的Bean
        ArrayList<MultipleItemBean> beanArrayList = new ArrayList<>();
        // 记录循环的index
        LinkedList<Integer> linkedList = new LinkedList<>();
        int size = data.size();
        for (int i = 0; i < size; i++) {
            MultipleItemBean multipleItemBean = data.get(i);
            // 是否勾选上
            boolean isSelected = multipleItemBean.getFiled(ShopCarFields.IS_SELECTED.name());
            if (isSelected) {
                // 存储Bean
                beanArrayList.add(multipleItemBean);
                // 存储index
                linkedList.add(i);
            }
        }
        for (int i = 0; i < beanArrayList.size(); i++) {
            MultipleItemBean multipleItemBean = beanArrayList.get(i);
            // 将整个Bean从list中删除
            data.remove(multipleItemBean);
            // 获取存储的index
            Integer index = linkedList.get(i);
            // 根据index更新Adapter
            mAdapter.notifyItemRemoved(index);
        }
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    public void clearAll() {
        // 清除Adapter里所有的数据
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
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
