package com.milkteaking.ec.main.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.milkteaking.core.fragments.bottom.BottomItemFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.constant.Constant;
import com.milkteaking.ec.main.personal.PersonalFragment;
import com.milkteaking.ui.recycler.MultipleItemBean;

import java.util.LinkedList;

import butterknife.BindView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author TanJJ
 * @time 2018/5/27 9:38
 * @des 订单列表Fragment
 */

public class OrderListFragment extends BottomItemFragment {
    // 全部订单/待付款..等等它们显示的内容整体上是一致的,所有可以只使用一直显示方法,
    // 再用type来区分点击事件就能实现各种内容的显式
    private String mOrderType;

    @BindView(R2.id.rv_order_list)
    RecyclerView mRecyclerView;

    @Override
    public Object getLayout() {
        return R.layout.fragment_order;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mOrderType = arguments.getString(PersonalFragment.ORDER_TYPE);
        }
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        initRecycler();
        requestData();

    }

    private void requestData() {
        RestClient.builder()
                .params("type", mOrderType)// 可以让服务器根据上传的type来返回不同的数据
                .url(Constant.PERSONAL_ALL_ORDER)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LinkedList<MultipleItemBean> convert = new OrderListDataConvert().setJson(response).convert();
                        OrderListAdapter adapter = new OrderListAdapter(convert);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .failed(new IFailed() {
                    @Override
                    public void onFailed(Throwable t) {

                    }
                })
                .build()
                .get();
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    // 设置过度动画
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
