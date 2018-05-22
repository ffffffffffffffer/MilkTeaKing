package com.milkteaking.ec.main.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

/**
 * @author TanJJ
 * @time 2018/5/21 16:04
 * @des 购物车Fragment
 */

public class ShopFragment extends BottomItemFragment {
    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView;

    @Override
    public Object getLayout() {
        return R.layout.fragment_shop;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {

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
                        ShopCarAdapter adapter = new ShopCarAdapter(convert);
                        mRecyclerView.setAdapter(adapter);
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
