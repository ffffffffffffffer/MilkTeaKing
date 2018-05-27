package com.milkteaking.ec.main.personal.profile;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.constant.Constant;
import com.milkteaking.ec.main.personal.list.ListAdapter;
import com.milkteaking.ec.main.personal.list.ListBean;
import com.milkteaking.ec.main.personal.list.ListItemType;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author TanJJ
 * @time 2018/5/27 12:25
 * @des 用户信息Fragment
 */

public class UserProfileFragment extends MilkTeaFragment {
    @BindView(R2.id.rv_user_profile)
    RecyclerView mRecyclerView;

    @Override
    public Object getLayout() {
        return R.layout.fragment_user_profile;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        initRecycler();
        initData();
    }

    private void initData() {
        ArrayList<ListBean> arrayList = new ArrayList<>();
        ListBean avatarBean = ListBean.builder()
                .setItemType(ListItemType.ITEM_AVATAR)
                .setId(1)
                .setImageUrl(Constant.PERSONAL_USER_PROFILE_AVATAR)
                .build();
        ListBean nameBean = ListBean.builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("姓名")
                .setValue("Louis Sonny")
                .build();
        ListBean genderBean = ListBean.builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("性别")
                .setValue("未设置性别")
                .build();
        ListBean birthdayBean = ListBean.builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setText("生日")
                .setValue("未设置生日")
                .build();
        arrayList.add(avatarBean);
        arrayList.add(nameBean);
        arrayList.add(genderBean);
        arrayList.add(birthdayBean);
        ListAdapter adapter = new ListAdapter(arrayList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new UserProfileClickListener(this));
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
