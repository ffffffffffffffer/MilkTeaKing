package com.milkteaking.ec.main.personal.address;

import android.view.View;

import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.util.log.MilkTeaLogger;
import com.milkteaking.ec.R;
import com.milkteaking.ec.constant.Constant;
import com.milkteaking.ui.recycler.MultipleField;
import com.milkteaking.ui.recycler.MultipleItemBean;
import com.milkteaking.ui.recycler.MultipleRecyclerAdapter;
import com.milkteaking.ui.recycler.MultipleRecyclerViewHolder;

import java.util.List;

/**
 * @author TanJJ
 * @time 2018/5/31 11:31
 * @des 收货地址的适配器
 */

public class AddressAdapter extends MultipleRecyclerAdapter {
    public AddressAdapter(List<MultipleItemBean> data) {
        super(data);
        initItemType();
    }

    private void initItemType() {
        addItemType(AddressItemType.ADDRESS_ITEM_TYPE, R.layout.item_address);
    }

    @Override
    protected void convert(final MultipleRecyclerViewHolder helper, MultipleItemBean item) {
        super.convert(helper, item);
        switch (item.getItemType()) {
            case AddressItemType.ADDRESS_ITEM_TYPE:
                // 获取数据
                final Integer id = item.getFiled(MultipleField.ID.name());
                String name = item.getFiled(MultipleField.NAME.name());
                String phone = item.getFiled(AddressFiled.PHONE.name());
                String address = item.getFiled(AddressFiled.ADDRESS.name());
                // 设置数据
                helper.setText(R.id.tv_address_name, name);
                helper.setText(R.id.tv_address_phone, phone);
                helper.setText(R.id.tv_address_address, address);
                // 设置删除的点击事件
                helper.setOnClickListener(R.id.tv_address_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 更新服务器数据
                        RestClient.builder()
                                .url(Constant.PERSONAL_ADDRESS)
                                .params("id", id)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        // 清除当前Item数据
                                        remove(helper.getLayoutPosition());
                                    }
                                })
                                .failed(new IFailed() {
                                    @Override
                                    public void onFailed(Throwable t) {
                                        MilkTeaLogger.e("onFailure", "更新服务器数据失败,收货地址");
                                    }
                                })
                                .build()
                                .post();
                    }
                });
                break;
            default:
                break;
        }
    }
}
