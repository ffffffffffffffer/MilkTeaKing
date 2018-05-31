package com.milkteaking.ec.main.personal.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.milkteaking.ui.recycler.DataCovert;
import com.milkteaking.ui.recycler.MultipleField;
import com.milkteaking.ui.recycler.MultipleItemBean;

import java.util.LinkedList;

/**
 * @author TanJJ
 * @time 2018/5/31 10:13
 * @des 收货地址的数据转换器
 */

public class AddressDataConvert extends DataCovert {
    @Override
    public LinkedList<MultipleItemBean> convert() {
        LinkedList<MultipleItemBean> linkedList = new LinkedList<>();
        JSONArray data = JSON.parseObject(getJson()).getJSONArray("data");
        int size = data.size();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = data.getJSONObject(i);
            Integer id = jsonObject.getInteger("id");
            String name = jsonObject.getString("name");
            String address = jsonObject.getString("address");
            String phone = jsonObject.getString("phone");
            Boolean aDefault = jsonObject.getBoolean("default");

            MultipleItemBean bean = MultipleItemBean.builder()
                    .setItemType(AddressItemType.ADDRESS_ITEM_TYPE)
                    .setField(MultipleField.ID.name(), id)
                    .setField(MultipleField.NAME.name(), name)
                    .setField(MultipleField.TAG.name(), aDefault)
                    .setField(AddressFiled.ADDRESS.name(), address)
                    .setField(AddressFiled.PHONE.name(), phone)
                    .build();
            linkedList.add(bean);
        }
        return linkedList;
    }
}
