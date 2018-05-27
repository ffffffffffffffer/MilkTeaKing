package com.milkteaking.ec.main.personal.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.milkteaking.ui.recycler.DataCovert;
import com.milkteaking.ui.recycler.MultipleField;
import com.milkteaking.ui.recycler.MultipleItemBean;

import java.util.LinkedList;

/**
 * @author TanJJ
 * @time 2018/5/27 9:47
 * @des 订单列表数据转换
 */

public class OrderListDataConvert extends DataCovert {
    @Override
    public LinkedList<MultipleItemBean> convert() {
        LinkedList<MultipleItemBean> linkedList = new LinkedList<>();
        JSONArray jsonArray = JSON.parseObject(getJson()).getJSONArray("data");
        int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Integer id = jsonObject.getInteger("id");
            String thumb = jsonObject.getString("thumb");
            String title = jsonObject.getString("title");
            String time = jsonObject.getString("time");
            Float price = jsonObject.getFloat("price");

            MultipleItemBean bean = MultipleItemBean.builder()
                    .setItemType(OrderListItemType.ORDER_LIST_ITEM_TYPE)
                    .setField(MultipleField.ID.name(), id)
                    .setField(MultipleField.TITLE.name(), title)
                    .setField(MultipleField.THUMB.name(), thumb)
                    .setField(OrderListField.TIME.name(), time)
                    .setField(OrderListField.PRICE.name(), price)
                    .build();
            linkedList.add(bean);
        }
        return linkedList;
    }
}
