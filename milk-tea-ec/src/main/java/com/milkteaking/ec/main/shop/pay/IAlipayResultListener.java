package com.milkteaking.ec.main.shop.pay;

/**
 * @author TanJJ
 * @time 2018/5/26 22:14
 * @des 支付宝结果接口
 */

public interface IAlipayResultListener {

    void onPaySuccess();

    void onPayFail();

    void onPaying();

    void onPayConnectError();

}
