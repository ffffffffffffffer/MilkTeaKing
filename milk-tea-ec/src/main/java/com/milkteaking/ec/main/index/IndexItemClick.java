package com.milkteaking.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.ec.main.detail.IndexDetailsFragment;
import com.milkteaking.ui.recycler.MultipleField;
import com.milkteaking.ui.recycler.MultipleItemBean;

/**
 * @author TanJJ
 * @time 2018/5/18 12:19
 * @des index的点击事件
 */

public class IndexItemClick extends SimpleClickListener {
    private final MilkTeaFragment mTeaFragment;

    public IndexItemClick(MilkTeaFragment teaFragment) {
        mTeaFragment = teaFragment;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MultipleItemBean bean = (MultipleItemBean) baseQuickAdapter.getData().get(position);
        int id = bean.getFiled(MultipleField.ID.name());
        mTeaFragment.start(IndexDetailsFragment.create(id));
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
