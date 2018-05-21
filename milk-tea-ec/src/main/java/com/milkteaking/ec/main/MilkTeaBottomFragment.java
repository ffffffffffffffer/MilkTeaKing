package com.milkteaking.ec.main;

import android.graphics.Color;

import com.milkteaking.core.fragments.bottom.BaseBottomFragment;
import com.milkteaking.core.fragments.bottom.BottomItemFragment;
import com.milkteaking.core.fragments.bottom.BottomTabBean;
import com.milkteaking.core.fragments.bottom.ItemBuilder;
import com.milkteaking.ec.main.discover.DiscoverFragment;
import com.milkteaking.ec.main.index.IndexFragment;
import com.milkteaking.ec.main.shop.ShopFragment;
import com.milkteaking.ec.main.sort.SortFragment;

import java.util.LinkedHashMap;

/**
 * @author TanJJ
 * @time 2018/5/9 17:09
 * @des 实现BaseBottomFragment
 */

public class MilkTeaBottomFragment extends BaseBottomFragment {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemFragment> getItems(ItemBuilder builder) {
        LinkedHashMap<BottomTabBean, BottomItemFragment> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "首页"), new IndexFragment());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new SortFragment());
        items.put(new BottomTabBean("{fa-compass}", "发现"), new DiscoverFragment());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new ShopFragment());
        items.put(new BottomTabBean("{fa-user}", "我的"), new IndexFragment());
        return builder.addItems(items).getItems();
    }

    @Override
    public int setIndexFragment() {
        return 0;
    }

    @Override
    public int setColor() {
        return Color.parseColor("#95ff4444");
    }
}
