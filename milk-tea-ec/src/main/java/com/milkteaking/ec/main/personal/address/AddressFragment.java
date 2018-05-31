package com.milkteaking.ec.main.personal.address;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.constant.Constant;
import com.milkteaking.ui.recycler.MultipleItemBean;

import java.util.LinkedList;

import butterknife.BindView;

/**
 * @author TanJJ
 * @time 2018/5/31 10:00
 * @des 收货地址管理Fragment
 */

public class AddressFragment extends MilkTeaFragment {

    @BindView(R2.id.rv_address)
    RecyclerView mRecyclerView;

    @Override
    public Object getLayout() {
        return R.layout.fragment_address;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        initRecycler();
        initData();
    }

    private void initData() {
        RestClient.builder()
                .url(Constant.PERSONAL_ADDRESS)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LinkedList<MultipleItemBean> convert = new AddressDataConvert().setJson(response).convert();
                        AddressAdapter addressAdapter = new AddressAdapter(convert);
                        mRecyclerView.setAdapter(addressAdapter);
                    }
                })
                .failed(new IFailed() {
                    @Override
                    public void onFailed(Throwable t) {

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
