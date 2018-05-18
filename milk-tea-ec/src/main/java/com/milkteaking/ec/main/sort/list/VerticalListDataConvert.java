package com.milkteaking.ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.milkteaking.ui.recycler.DataCovert;
import com.milkteaking.ui.recycler.ItemType;
import com.milkteaking.ui.recycler.MultipleField;
import com.milkteaking.ui.recycler.MultipleItemBean;

import java.util.LinkedList;

/**
 * @author TanJJ
 * @time 2018/5/18 15:25
 * @des 左边Fragment的数据转换
 */

public class VerticalListDataConvert extends DataCovert {
    @Override
    public LinkedList<MultipleItemBean> convert() {
        LinkedList<MultipleItemBean> linkedList = new LinkedList<>();
        JSONObject jsonObject = JSON.parseObject(getJson()).getJSONObject("data");
        JSONArray listArray = jsonObject.getJSONArray("list");
        int size = listArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject1 = listArray.getJSONObject(i);
            Integer id = jsonObject1.getInteger("id");
            String name = jsonObject1.getString("name");

            MultipleItemBean multipleItemBean = MultipleItemBean.builder()
                    .setItemType(ItemType.SORT_MENU_LIST)
                    .setField(MultipleField.TAG.name(), false)
                    .setField(MultipleField.ID.name(), id)
                    .setField(MultipleField.NAME.name(), name)
                    .build();
            linkedList.add(multipleItemBean);
        }
        // 设置默认选中第0个
        linkedList.get(0).setField(MultipleField.TAG.name(), true);
        return linkedList;
    }
}
