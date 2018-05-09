package com.milkteaking.core.fragments.bottom;

import java.util.WeakHashMap;

/**
 * @author TanJJ
 * @time 2018/5/9 13:37
 * @des 用来整合BottomBean和BottomItemFragment
 */

public class ItemBuilder {
    private WeakHashMap<BottomTabBean, BottomItemFragment> items = new WeakHashMap<>();

    public static ItemBuilder build() {
        return new ItemBuilder();
    }

    public void addItem(BottomTabBean bean, BottomItemFragment itemFragment) {
        items.put(bean, itemFragment);
    }

    public void addItems(WeakHashMap<BottomTabBean, BottomItemFragment> itemFragmentWeakHashMap) {
        items = itemFragmentWeakHashMap;
    }

    public WeakHashMap<BottomTabBean, BottomItemFragment> getItems() {
        return items;
    }
}
