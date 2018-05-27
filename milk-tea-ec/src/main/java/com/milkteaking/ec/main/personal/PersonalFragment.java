package com.milkteaking.ec.main.personal;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.milkteaking.core.fragments.bottom.BottomItemFragment;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.main.personal.list.ListAdapter;
import com.milkteaking.ec.main.personal.list.ListBean;
import com.milkteaking.ec.main.personal.list.ListItemType;
import com.milkteaking.ec.main.personal.order.OrderListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author TanJJ
 * @time 2018/5/26 23:14
 * @des 个人中心Fragment
 */

public class PersonalFragment extends BottomItemFragment {

    public static final String ORDER_TYPE = "ORDER_TYPE";

    @BindView(R2.id.rv_personal)
    RecyclerView mRecyclerView;
    private Bundle mArgument;

    @OnClick(R2.id.itv_personal_all_order)
    public void clickAllOrder() {
        mArgument.putString(ORDER_TYPE, "all");
        startFragment();
    }

    @OnClick(R2.id.ll_pay)
    public void clickPay() {
        mArgument.putString(ORDER_TYPE, "no_pay");
        startFragment();
    }

    private void startFragment() {
        OrderListFragment orderListFragment = new OrderListFragment();
        orderListFragment.setArguments(mArgument);
        getParentFragment(0).getSupportDelegate().start(orderListFragment);
    }


    @Override
    public Object getLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        initLayoutManager();
        initAdapter();
        mArgument = new Bundle();
    }

    private void initLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initAdapter() {
        // 设置固定数据
        ListBean listBean1 = ListBean.builder()
                .setId(1)
                .setItemType(ListItemType.ITEM_NORMAL)
                .setText("收货地址")
                .setValue("")
                .build();
        ListBean listBean2 = ListBean.builder()
                .setId(1)
                .setItemType(ListItemType.ITEM_NORMAL)
                .setText("系统设置")
                .setValue("")
                .build();
        // 存放到集合中
        ArrayList<ListBean> arrayList = new ArrayList<>();
        arrayList.add(listBean1);
        arrayList.add(listBean2);
        // 创建Adapter
        ListAdapter listAdapter = new ListAdapter(arrayList);
        mRecyclerView.setAdapter(listAdapter);
    }
}
