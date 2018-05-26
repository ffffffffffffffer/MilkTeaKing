package com.milkteaking.ec.main.shop.pay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;

/**
 * @author TanJJ
 * @time 2018/5/26 22:43
 * @des 异步支付处理
 */

public class PayAsyncTask extends AsyncTask<String, Void, String> {

    private final Activity mActivity;
    private final IAlipayResultListener mAlipayResultListener;
    // 响应状态
    // 订单支付成功
    private static final String ALI_PAY_SUCCESS = "9000";
    // 订单处理中
    private static final String ALI_PAY_PAYING = "8000";
    // 订单支付失败
    private static final String ALI_PAY_FAIL = "4000";
    // 用户支付取消
    private static final String ALI_PAY_CANCEL = "6001";
    // 支付网络错误
    private static final String ALI_PAY_CONNECT_ERROR = "6002";


    public PayAsyncTask(Activity activity, IAlipayResultListener alipayResultListener) {
        mActivity = activity;
        mAlipayResultListener = alipayResultListener;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        PayResult result = new PayResult(s);
        String resultInfo = result.getResult();
        String resultStatus = result.getResultStatus();

        switch (resultStatus) {
            case ALI_PAY_PAYING:
                mAlipayResultListener.onPaying();
                break;
            case ALI_PAY_SUCCESS:
                mAlipayResultListener.onPaySuccess();
                break;
            case ALI_PAY_FAIL:
                mAlipayResultListener.onPayFail();
                break;
            case ALI_PAY_CANCEL:
                mAlipayResultListener.onPayCancel();
                break;
            case ALI_PAY_CONNECT_ERROR:
                mAlipayResultListener.onPayConnectError();
                break;
        }

    }

    @Override
    protected String doInBackground(String... params) {
        // 支付签名信息
        String paySign = params[0];
        PayTask task = new PayTask(mActivity);
        return task.pay(paySign, true);
    }
}
