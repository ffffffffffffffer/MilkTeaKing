package com.milkteaking.ec.main.personal.settings;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;

import com.blankj.utilcode.util.ToastUtils;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.main.personal.list.ListAdapter;
import com.milkteaking.ec.main.personal.list.ListBean;
import com.milkteaking.ec.main.personal.list.ListItemType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author TanJJ
 * @time 2018/5/31 15:18
 * @des 个人中心中的系统设置Fragment
 */

public class SettingFragment extends MilkTeaFragment {

    @BindView(R2.id.rv_settings)
    RecyclerView mRecyclerView;

    @Override
    public Object getLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        initRecycler();
        initData();
    }

    private void initData() {
        List<ListBean> list = new ArrayList<>();
        ListBean switchJPush = ListBean.builder()
                .setId(1)
                .setText("极光推送")
                .setItemType(ListItemType.ITEM_SWITCH)
                // 很多时候可以模仿这种方法分配方式,将Adapter的方法分配到提供者哪里实现
                .setCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            ToastUtils.showShort("开启推送");
                        } else {
                            ToastUtils.showShort("关闭推送");
                        }
                    }
                })
                .build();

        ListBean companyAbout = ListBean.builder()
                .setId(2)
                .setText("关于")
                .setItemType(ListItemType.ITEM_NORMAL)
                .setMilkTeaFragment(null)
                .build();
        list.add(switchJPush);
        list.add(companyAbout);
        ListAdapter listAdapter = new ListAdapter(list);
        mRecyclerView.setAdapter(listAdapter);
    }

    private void initRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }
}
