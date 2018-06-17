package com.milkteaking.ec.main.index.search;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.blankj.utilcode.util.StringUtils;
import com.milkteaking.core.util.storage.Preference;
import com.milkteaking.ui.recycler.DataCovert;
import com.milkteaking.ui.recycler.MultipleField;
import com.milkteaking.ui.recycler.MultipleItemBean;

import java.util.LinkedList;

/**
 * @author TanJJ
 * @time 2018/6/17 13:46
 * @des 搜索数据转换
 */

public class SearchDataConvert extends DataCovert {

    public static final String TAG_SEARCH_HISTORY = "search_history";

    @Override
    public LinkedList<MultipleItemBean> convert() {
        LinkedList<MultipleItemBean> bean = new LinkedList<>();
        // 取出存储好的搜索历史记录
        String json = Preference.getCustomAppProfile(TAG_SEARCH_HISTORY);
        if (!StringUtils.isEmpty(json)) {
            JSONArray jsonArray = JSON.parseArray(json);
            int size = jsonArray.size();
            for (int i = 0; i < size; i++) {
                String historyText = jsonArray.getString(i);
                MultipleItemBean build = MultipleItemBean.builder()
                        .setItemType(SearchItemType.search_type)
                        .setField(MultipleField.TEXT.name(), historyText)
                        .build();
                bean.add(build);
            }
        }
        return bean;
    }
}
