package com.milkteaking.ec.main.personal.settings;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.ec.main.personal.list.ListBean;

/**
 * @author TanJJ
 * @time 2018/5/31 16:47
 * @des 系统设置的点击事件
 */

public class SettingClickListener extends SimpleClickListener {
    private final SettingFragment mSettingFragment;

    public SettingClickListener(SettingFragment settingFragment) {
        mSettingFragment = settingFragment;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ListBean listBean = (ListBean) adapter.getData().get(position);
        int id = listBean.getId();
        switch (id) {
            case 1:
                // 开关推送
                break;
            case 2:
                // 跳转到关于界面
                MilkTeaFragment milkTeaFragment = listBean.getMilkTeaFragment();
                if (milkTeaFragment != null) {
                    mSettingFragment.getSupportDelegate().start(milkTeaFragment);
                }
                break;
            default:
                break;
        }
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
