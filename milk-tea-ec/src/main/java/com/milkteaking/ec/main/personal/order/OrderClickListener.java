package com.milkteaking.ec.main.personal.order;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.milkteaking.ec.main.personal.order.comment.OrderCommentFragment;
import com.milkteaking.ui.recycler.MultipleField;
import com.milkteaking.ui.recycler.MultipleItemBean;

/**
 * @author TanJJ
 * @time 2018/6/1 8:42
 * @des 订单的点击事件
 */

public class OrderClickListener extends SimpleClickListener {
    private final OrderListFragment mOrderListFragment;

    public OrderClickListener(OrderListFragment orderListFragment) {
        mOrderListFragment = orderListFragment;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OrderCommentFragment orderCommentFragment = new OrderCommentFragment();
        // 将缩略图传递到下一个页面
        MultipleItemBean bean = (MultipleItemBean) adapter.getData().get(position);
        String thumb = bean.getFiled(MultipleField.THUMB.name());
        Bundle arg = new Bundle();
        arg.putString("thumb", thumb);
        orderCommentFragment.setArguments(arg);
        // 开启订单评论界面
        mOrderListFragment.getSupportDelegate().start(orderCommentFragment);
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
