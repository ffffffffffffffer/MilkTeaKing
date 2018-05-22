package com.milkteaking.ec.main.shop;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.milkteaking.core.ui.image.GlideApp;
import com.milkteaking.ec.R;
import com.milkteaking.ui.recycler.ItemType;
import com.milkteaking.ui.recycler.MultipleItemBean;
import com.milkteaking.ui.recycler.MultipleRecyclerAdapter;
import com.milkteaking.ui.recycler.MultipleRecyclerViewHolder;

import java.util.List;

/**
 * @author TanJJ
 * @time 2018/5/22 15:23
 * @des 购物车适配器
 */

public class ShopCarAdapter extends MultipleRecyclerAdapter {

    private boolean mSelectAll;

    public ShopCarAdapter(List<MultipleItemBean> data) {
        super(data);
        initItemType();
    }

    public void setSelectAll(boolean select) {
        mSelectAll = select;
    }

    private void initItemType() {
        addItemType(ItemType.SHOP_CAR_LIST, R.layout.item_shop_car);
    }

    @Override
    protected void convert(MultipleRecyclerViewHolder helper, final MultipleItemBean item) {
        super.convert(helper, item);
        // 判断是哪一个样式
        switch (item.getItemType()) {
            case ItemType.SHOP_CAR_LIST:
                // 取出数据
                AppCompatImageView thumbImageView = helper.getView(R.id.image_item_shop_cart);
                String title = item.getFiled(ShopCarFields.TITLE.name());
                String desc = item.getFiled(ShopCarFields.DESC.name());
                String thumb = item.getFiled(ShopCarFields.THUMB.name());
                float price = item.getFiled(ShopCarFields.PRICE.name());
                int count = item.getFiled(ShopCarFields.COUNT.name());
                // 在左侧勾勾渲染之前改变全选与否状态
                item.setField(ShopCarFields.IS_SELECTED.name(), mSelectAll);
                // 取出是否选中值
                boolean isSelected = item.getFiled(ShopCarFields.IS_SELECTED.name());
                // 取出控件
                AppCompatTextView countTextView = helper.getView(R.id.tv_item_shop_cart_count);
                final IconTextView selectTextView = helper.getView(R.id.icon_item_shop_cart);

                // 赋值
                helper.setText(R.id.tv_item_shop_cart_title, title);
                helper.setText(R.id.tv_item_shop_cart_desc, desc);
                helper.setText(R.id.tv_item_shop_cart_price, String.valueOf(price));
                countTextView.setText(String.valueOf(count));
                //  加载图片
                GlideApp.with(mContext)
                        .load(thumb)
                        .into(thumbImageView);
                // 设置默认的选中情况
                if (isSelected) {
                    selectTextView.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                } else {
                    selectTextView.setTextColor(Color.GRAY);
                }
                // 设置勾勾的点击事件
                selectTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean currentSelect = item.getFiled(ShopCarFields.IS_SELECTED.name());
                        if (currentSelect) {
                            // 相反
                            selectTextView.setTextColor(Color.GRAY);
                            item.setField(ShopCarFields.IS_SELECTED.name(), false);
                        } else {
                            selectTextView.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                            item.setField(ShopCarFields.IS_SELECTED.name(), true);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
