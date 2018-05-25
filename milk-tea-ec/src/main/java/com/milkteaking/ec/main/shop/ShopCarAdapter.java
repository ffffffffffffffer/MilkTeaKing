package com.milkteaking.ec.main.shop;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.ui.image.GlideApp;
import com.milkteaking.core.util.log.MilkTeaLogger;
import com.milkteaking.ec.R;
import com.milkteaking.ec.constant.Constant;
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
    private float mTotalPrice;
    private IShopCarItemListener mShopCarItemListener;

    public ShopCarAdapter(List<MultipleItemBean> data) {
        super(data);
        initItemType();
    }

    public void setShopCarItemListener(IShopCarItemListener shopCarItemListener) {
        mShopCarItemListener = shopCarItemListener;
    }

    public void setSelectAll(boolean select) {
        mSelectAll = select;
        if (mSelectAll) {
            if (mShopCarItemListener != null) {
                float totalPrice = getTotalPrice();
                mShopCarItemListener.onItemPrice(totalPrice);
            }
        } else {
            if (mShopCarItemListener != null) {
                mShopCarItemListener.onRest();
            }
        }
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
                final float price = item.getFiled(ShopCarFields.PRICE.name());
                final int count = item.getFiled(ShopCarFields.COUNT.name());
                // 在左侧勾勾渲染之前改变全选与否状态
                item.setField(ShopCarFields.IS_SELECTED.name(), mSelectAll);
                // 取出是否选中值
                final boolean isSelected = item.getFiled(ShopCarFields.IS_SELECTED.name());
                // 取出控件
                final AppCompatTextView countTextView = helper.getView(R.id.tv_item_shop_cart_count);
                final AppCompatTextView priceTextView = helper.getView(R.id.tv_item_shop_cart_price);
                final IconTextView selectTextView = helper.getView(R.id.icon_item_shop_cart);
                final IconTextView minusTextView = helper.getView(R.id.icon_item_minus);
                final IconTextView plusTextView = helper.getView(R.id.icon_item_plus);

                // 赋值
                helper.setText(R.id.tv_item_shop_cart_title, title);
                helper.setText(R.id.tv_item_shop_cart_desc, desc);
                helper.setText(R.id.tv_item_shop_cart_price, String.valueOf(price));
                countTextView.setText(String.valueOf(count));

                // 算出当前商品的价格
                final float itemCount = price * count;
                priceTextView.setText(String.valueOf(itemCount));
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
                        int count = item.getFiled(ShopCarFields.COUNT.name());
                        float itemCount = price * count;
                        if (currentSelect) {
                            // 相反
                            selectTextView.setTextColor(Color.GRAY);
                            item.setField(ShopCarFields.IS_SELECTED.name(), false);
                            mTotalPrice -= itemCount;
                            if (mShopCarItemListener != null) {
                                mShopCarItemListener.onUnSelect(mTotalPrice);
                                setRest();
                            }
                        } else {
                            selectTextView.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                            item.setField(ShopCarFields.IS_SELECTED.name(), true);
                            // 勾选了就加到item的总价格上
                            mTotalPrice += itemCount;
                            if (mShopCarItemListener != null) {
                                mShopCarItemListener.onSelected(mTotalPrice);
                            }
                        }
                    }
                });

                // 设置添加/删除商品数量按键事件
                minusTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changePriceData(countTextView, price, priceTextView, item, "minus");
                    }
                });

                plusTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changePriceData(countTextView, price, priceTextView, item, "plus");
                    }
                });

                break;
            default:
                break;
        }
    }

    private void changePriceData(final AppCompatTextView countTextView, final float price, final
    AppCompatTextView priceTextView, final MultipleItemBean item, String method) {
        int count = item.getFiled(ShopCarFields.COUNT.name());
        int newCount = 0;
        boolean isPlus = false;
        if (method.equals("minus")) {
            // 对count进行减一
            newCount = count - 1;
            isPlus = false;
        } else if (method.equals("plus")) {
            // 对count进行加一
            newCount = count + 1;
            isPlus = true;
        }
        final int finalNewCount = newCount;
        final boolean finalIsPlus = isPlus;
        // 更新服务器的数据
        RestClient.builder()
                .params("count", newCount)
                .url(Constant.SHOP_COUNT)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        // 更新UI
                        float itemCount = price * finalNewCount;
                        if (finalNewCount > 0) {
                            priceTextView.setText(String.valueOf(itemCount));
                            countTextView.setText(String.valueOf(finalNewCount));
                            item.setField(ShopCarFields.COUNT.name(), finalNewCount);
                            // 如果是加的就调用
                            boolean isSelected = item.getFiled(ShopCarFields.IS_SELECTED.name());
                            if (isSelected) {
                                if (mShopCarItemListener != null) {
                                    if (finalIsPlus) {
                                        mTotalPrice += price;
                                    } else {
                                        mTotalPrice -= price;
                                    }
                                    mShopCarItemListener.onItemPrice(mTotalPrice);
                                }
                            }
                        }
                    }
                })
                .failed(new IFailed() {
                    @Override
                    public void onFailed(Throwable t) {
                        MilkTeaLogger.e(ShopCarAdapter.class.getSimpleName(), t.getMessage());
                    }
                })
                .build()
                .get();
    }

    public void setRest() {
        int size = getData().size();
        boolean isSelected = false;
        for (int i = 0; i < size; i++) {
            MultipleItemBean multipleItemBean = getData().get(i);
            isSelected = multipleItemBean.getFiled(ShopCarFields.IS_SELECTED.name());
            if (isSelected) {
                return;
            }
        }
        if (!isSelected) {
            mShopCarItemListener.onRest();
        }

    }

    public float getTotalPrice() {
        float total = 0;
        if (mTotalPrice == 0.0) {
            int size = getData().size();
            for (int i = 0; i < size; i++) {
                int count = getData().get(i).getFiled(ShopCarFields.COUNT.name());
                float price = getData().get(i).getFiled(ShopCarFields.PRICE.name());
                total += count * price;
            }
        } else {
            total = mTotalPrice;
        }
        return total;
    }
}
