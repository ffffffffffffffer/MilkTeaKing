package com.milkteaking.ec.main.personal;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.ec.main.personal.list.ListBean;

/**
 * @author TanJJ
 * @time 2018/5/31 10:07
 * @des 个人中心的点击监听, 所有个人中心的点击事件都在这里处理, 易于管理
 */

public class PersonalClickListener extends SimpleClickListener {

    private final PersonalFragment mPersonalFragment;

    public PersonalClickListener(PersonalFragment personalFragment) {
        mPersonalFragment = personalFragment;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 跳转到对应的Fragment
        ListBean listBean = (ListBean) adapter.getData().get(position);
        int id = listBean.getId();
        switch (id) {
            case 1:
                startFragment(listBean);
                break;
            case 2:
                startFragment(listBean);
                break;
            default:
                break;
        }


    }

    private void startFragment(ListBean listBean) {
        MilkTeaFragment milkTeaFragment = listBean.getMilkTeaFragment();
        if (mPersonalFragment != null) {
            mPersonalFragment.getParentFragment(0).getSupportDelegate().start(milkTeaFragment);
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
