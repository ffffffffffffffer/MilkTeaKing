package com.milkteaking.ec.main.personal.order;

import android.support.v7.widget.AppCompatImageView;

import com.milkteaking.core.glide.GlideOptions;
import com.milkteaking.core.ui.image.GlideApp;
import com.milkteaking.ec.R;
import com.milkteaking.ui.recycler.MultipleField;
import com.milkteaking.ui.recycler.MultipleItemBean;
import com.milkteaking.ui.recycler.MultipleRecyclerAdapter;
import com.milkteaking.ui.recycler.MultipleRecyclerViewHolder;

import java.util.List;

/**
 * @author TanJJ
 * @time 2018/5/27 10:03
 * @des 订单列表适配器
 */

public class OrderListAdapter extends MultipleRecyclerAdapter {
    public OrderListAdapter(List<MultipleItemBean> data) {
        super(data);
        initLayout();
    }

    private void initLayout() {
        addItemType(OrderListItemType.ORDER_LIST_ITEM_TYPE, R.layout.item_order_list);
    }

    @Override
    protected void convert(MultipleRecyclerViewHolder helper, MultipleItemBean item) {
        super.convert(helper, item);
        switch (item.getItemType()) {
            case OrderListItemType.ORDER_LIST_ITEM_TYPE:
                // 获取数据
                String title = item.getFiled(MultipleField.TITLE.name());
                String thumb = item.getFiled(MultipleField.THUMB.name());
                String time = item.getFiled(OrderListField.TIME.name());
                float price = item.getFiled(OrderListField.PRICE.name());
                // 设置数据
                helper.setText(R.id.tv_order_list_title, title);
                helper.setText(R.id.tv_order_list_time, time);
                helper.setText(R.id.tv_order_list_price, String.valueOf(price));
                AppCompatImageView imageViewThumb = helper.getView(R.id.image_order_list);
                // 加载图片
                GlideApp.with(mContext)
                        .load(thumb)
                        .apply(GlideOptions.OPTIONS)
                        .into(imageViewThumb);
                break;
            default:
                break;
        }
    }
}
