package com.milkteaking.core.fragments.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.milkteaking.core.fragments.MilkTeaFragment;

import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author TanJJ
 * @time 2018/5/9 13:40
 */

public abstract class BaseBottomFragment extends MilkTeaFragment {
    private final WeakHashMap<BottomTabBean, BottomItemFragment> ITEMS = new WeakHashMap<>();
    private final ArrayList<BottomTabBean> BOTTOM_BEEN = new ArrayList<>();
    private final ArrayList<BottomItemFragment> ITEM_FRAGMENTS = new ArrayList<>();
    private int mIndexFragment;
    private int mCurrentFragment;
    private int mClickColor = Color.parseColor("#95ff4444");


    public abstract WeakHashMap<BottomTabBean, BottomItemFragment> getItems(ItemBuilder builder);

    public abstract int setIndexFragment();

    public abstract int setColor();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIndexFragment = setIndexFragment();

        if (setColor() != 0) {
            mClickColor = setColor();
        }

        WeakHashMap<BottomTabBean, BottomItemFragment> items = getItems(ItemBuilder.build());
        // 将获取到的map储存起来
        ITEMS.putAll(items);
        // 遍历集合
        for (Map.Entry<BottomTabBean, BottomItemFragment> entry : items.entrySet()) {
            BOTTOM_BEEN.add(entry.getKey());
            ITEM_FRAGMENTS.add(entry.getValue());
        }
    }
}
