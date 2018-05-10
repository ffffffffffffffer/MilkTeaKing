package com.milkteaking.ui.recycler;

import java.util.LinkedHashMap;

import static android.R.attr.value;

/**
 * @author TanJJ
 * @time 2018/5/10 9:47
 */

public class MultipleItemBeanBuilder {
    private LinkedHashMap<String, Object> fields = new LinkedHashMap<>();

    public MultipleItemBeanBuilder() {
        // 使用前先清理之前的数据
        fields.clear();
    }

    public MultipleItemBeanBuilder setItemType(int itemType) {
        fields.put(MultipleField.ITEM_TYPE.name(), itemType);
        return this;
    }

    public MultipleItemBeanBuilder setField(String key, Object value) {
        fields.put(key, value);
        return this;
    }

    public MultipleItemBeanBuilder setField(LinkedHashMap<String, Object> fields) {
        this.fields.putAll(fields);
        return this;
    }


    public MultipleItemBean build() {
        return new MultipleItemBean(fields);
    }
}
