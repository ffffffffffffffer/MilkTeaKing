package com.milkteaking.ec.main.shop.pay;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.ec.R;

/**
 * @author TanJJ
 * @time 2018/5/26 9:26
 * @des 快速支付
 */

public class FastPay implements View.OnClickListener {

    private AlertDialog mAlertDialog;
    private Activity mActivity;
    private IAlipayResultListener mAlipayResultListener;
    private int mOrderId;

    private FastPay(MilkTeaFragment fragment) {
        mActivity = fragment.getProxyActivity();
        mAlertDialog = new AlertDialog.Builder(fragment.getContext()).create();
    }

    public static FastPay create(MilkTeaFragment fragment) {
        return new FastPay(fragment);
    }

    public FastPay setAlipayResultListener(IAlipayResultListener alipayResultListener) {
        mAlipayResultListener = alipayResultListener;
        return this;
    }

    public FastPay setOrderId(int orderId) {
        mOrderId = orderId;
        return this;
    }

    public void beginPayDialog() {
        mAlertDialog.show();
        Window window = mAlertDialog.getWindow();
        if (window != null) {
            // 设置自定义layout
            window.setContentView(R.layout.dialog_pay_panel);
            // 设置enter/exit动画
            window.setWindowAnimations(R.style.anim_panel_up_to_below);
            // 设置位置
            window.setGravity(Gravity.BOTTOM);
            // 设置背景
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams attributes = window.getAttributes();
            // 设置属性
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(attributes);
            // 设置点击事件
            window.findViewById(R.id.btn_dialog_pay_alipay).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_weixin).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_dialog_pay_alipay) {
            // 支付宝支付
            alipay(mOrderId);
            mAlertDialog.cancel();
        } else if (i == R.id.btn_dialog_pay_weixin) {
            mAlertDialog.cancel();
        } else if (i == R.id.btn_dialog_pay_cancel) {
            mAlertDialog.cancel();
        }
    }

    private void alipay(int orderId) {
        String singUrl = "http://app.api.zanzuanshi.com/api/v1/alipay/appSign?id=" + orderId;
        // 获取签名字符串
        RestClient.builder()
                .url(singUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        String paySign = JSON.parseObject(response).getString("result");
                        // 必须是异步的调用客户端支付接口
                        PayAsyncTask payAsyncTask = new PayAsyncTask(mActivity, mAlipayResultListener);
                        // 用线程池执行
                        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paySign);

                    }
                })
                .failed(new IFailed() {
                    @Override
                    public void onFailed(Throwable t) {

                    }
                })
                .build()
                .post();
    }
}
