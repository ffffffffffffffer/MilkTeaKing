package com.example.milkteaking;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.milkteaking.core.activitys.ProxyActivity;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IError;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.IRequest;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.ui.loader.LoaderStyle;
import com.milkteaking.core.util.log.MilkTeaLogger;

public class MainActivity extends ProxyActivity {

    @Override
    public MilkTeaFragment getRootFragment() {
        // 返回根Fragment
        return new RootFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test();
    }

    private void test() {
        RestClient.builder()
                .url("http://192.168.0.102/bbbb.apk")
                .loader(this, LoaderStyle.PacmanIndicator)
                .extension("apk")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        ToastUtils.showShort(response);
                        MilkTeaLogger.e("onSuccess", response);
                    }
                })
                .failed(new IFailed() {
                    @Override
                    public void onFailed(Throwable t) {
                        ToastUtils.showShort(t.getMessage());
                        MilkTeaLogger.e("onFailed", t.getMessage());
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.showShort("code: " + code + " msg: " + msg);
                        MilkTeaLogger.e("onError", "code: " + code + " msg: " + msg);
                    }
                })
                .request(new IRequest() {
                    @Override
                    public void onRequestStart() {
                        ToastUtils.showShort("请求开始");
                        MilkTeaLogger.e("onRequestStart", "请求开始");
                    }

                    @Override
                    public void onRequestEnd() {
                        ToastUtils.showShort("请求结束");
                        MilkTeaLogger.e("onRequestEnd", "请求结束");
                    }
                })
                .build()
                .download();
    }
}
