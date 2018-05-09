package com.milkteaking.core.fragments.bottom;

import java.util.LinkedHashMap;

/**
 * @author TanJJ
 * @time 2018/5/9 13:37
 * @des 用来整合BottomBean和BottomItemFragment
 */

public class ItemBuilder {
    private LinkedHashMap<BottomTabBean, BottomItemFragment> items = new LinkedHashMap<>();

    public static ItemBuilder build() {
        return new ItemBuilder();
    }

    public void addItem(BottomTabBean bean, BottomItemFragment itemFragment) {
        items.put(bean, itemFragment);
    }

    public ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemFragment> itemFragmentWeakHashMap) {
        items = itemFragmentWeakHashMap;
        return this;
    }

    public LinkedHashMap<BottomTabBean, BottomItemFragment> getItems() {
        return items;
    }
}
