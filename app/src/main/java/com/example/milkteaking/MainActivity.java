package com.example.milkteaking;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.blankj.utilcode.util.ToastUtils;
import com.milkteaking.core.activitys.ProxyActivity;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.RestCreator;
import com.milkteaking.core.net.RestRxClient;
import com.milkteaking.core.net.callback.IError;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.IRequest;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.ui.loader.LoaderStyle;
import com.milkteaking.core.ui.loader.MilkTeaLoader;
import com.milkteaking.core.util.log.MilkTeaLogger;
import com.milkteaking.ec.sign.SignInFragment;

import java.util.WeakHashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends ProxyActivity {

    @Override
    public MilkTeaFragment getRootFragment() {
        // 返回根Fragment
        return new SignInFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
//        test();
//        testRestRxClient();
//        testRestRxCreate();
    }

    private void test() {
        RestClient.builder()
                .url("http://127.0.0.1/index")
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
                .get();
    }

    // TODO: 2018/5/7 测试Retrofit和RxJava结合使用
    private void testRestRxClient() {
        RestRxClient.build()
                .url("http://192.168.0.102/latte_index.json")
                .loaderStyle(this)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        MilkTeaLogger.e("onNext", s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MilkTeaLogger.e("onError", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        MilkTeaLogger.e("onComplete", "完成了");
                        ToastUtils.showShort("完成了");
                        MilkTeaLoader.stopLoader();
                    }
                });
    }

    // TODO: 2018/5/7 测试RxRestCreate
    private void testRestRxCreate() {
        final String url = "http://192.168.0.102/latte_index.json";
        WeakHashMap<String, Object> params = new WeakHashMap<>();
        RestCreator.getRestRxService().get(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        MilkTeaLogger.e("onNext", s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MilkTeaLogger.e("onError", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        MilkTeaLogger.e("onComplete", "完成了");
                        ToastUtils.showShort("完成了");
                        MilkTeaLoader.stopLoader();
                    }
                });
    }
}
