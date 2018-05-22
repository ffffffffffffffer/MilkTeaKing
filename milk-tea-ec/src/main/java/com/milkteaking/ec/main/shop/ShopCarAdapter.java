package com.milkteaking.ec.main.shop;

import android.support.v7.widget.AppCompatImageView;

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
    public ShopCarAdapter(List<MultipleItemBean> data) {
        super(data);
        initItemType();
    }

    private void initItemType() {
        addItemType(ItemType.SHOP_CAR_LIST, R.layout.item_shop_car);
    }

    @Override
    protected void convert(MultipleRecyclerViewHolder helper, MultipleItemBean item) {
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
                // 赋值
                helper.setText(R.id.tv_item_shop_cart_title, title);
                helper.setText(R.id.tv_item_shop_cart_desc, desc);
                helper.setText(R.id.tv_item_shop_cart_price, String.valueOf(price));
                //加载图片
                GlideApp.with(mContext)
                        .load(thumb)
                        .into(thumbImageView);
                break;
            default:
                break;
        }
    }
}
