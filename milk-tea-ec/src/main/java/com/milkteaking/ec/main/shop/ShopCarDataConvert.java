package com.milkteaking.ec.main.shop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.milkteaking.ui.recycler.DataCovert;
import com.milkteaking.ui.recycler.ItemType;
import com.milkteaking.ui.recycler.MultipleItemBean;

import java.util.LinkedList;

/**
 * @author TanJJ
 * @time 2018/5/22 15:15
 * @des 购物车数据转换
 */

public class ShopCarDataConvert extends DataCovert {
    @Override
    public LinkedList<MultipleItemBean> convert() {
        LinkedList<MultipleItemBean> linkedList = new LinkedList<>();
        JSONArray dataArray = JSON.parseObject(getJson()).getJSONArray("data");
        int dataArraySize = dataArray.size();
        for (int i = 0; i < dataArraySize; i++) {
            JSONObject jsonObject = dataArray.getJSONObject(i);
            // 取出数据
            String title = jsonObject.getString("title");
            String desc = jsonObject.getString("desc");
            String thumb = jsonObject.getString("thumb");
            Integer id = jsonObject.getInteger("id");
            Float price = jsonObject.getFloat("price");
            int count = jsonObject.getInteger("count");
            // 存放数据
            MultipleItemBean itemBean = MultipleItemBean.builder()
                    .setItemType(ItemType.SHOP_CAR_LIST)
                    .setField(ShopCarFields.ID.name(), id)
                    .setField(ShopCarFields.TITLE.name(), title)
                    .setField(ShopCarFields.DESC.name(), desc)
                    .setField(ShopCarFields.THUMB.name(), thumb)
                    .setField(ShopCarFields.PRICE.name(), price)
                    .setField(ShopCarFields.COUNT.name(), count)
                    .setField(ShopCarFields.IS_SELECTED.name(), false)
                    .build();
            linkedList.add(itemBean);
        }
        return linkedList;
    }
}
