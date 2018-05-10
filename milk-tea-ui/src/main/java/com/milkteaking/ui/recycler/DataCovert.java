package com.milkteaking.ui.recycler;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * @author TanJJ
 * @time 2018/5/10 9:54
 * @des 数据转换基类
 */

public abstract class DataCovert {
    protected final LinkedList<MultipleItemBean> BEANS = new LinkedList<>();
    private String json;

    // 让子类实现数据转换,转换成需要的数据
    public abstract LinkedHashMap<String, Object> convert();

    public void setJson(String data) {
        json = data;
    }

    public String getJson() {
        if (json == null || json.isEmpty()) {
            throw new RuntimeException("json data is null,call setJson() please.");
        }
        return json;
    }
}
