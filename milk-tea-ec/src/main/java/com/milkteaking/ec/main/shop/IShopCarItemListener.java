package com.milkteaking.ec.main.shop;

/**
 * @author TanJJ
 * @time 2018/5/24 20:07
 * @des 购物车item接口
 */

public interface IShopCarItemListener {

    void onItemPrice(float totalPrice);

    void onSelected(float itemCount);

    void onUnSelect(float itemCount);

    void onRest();

}
