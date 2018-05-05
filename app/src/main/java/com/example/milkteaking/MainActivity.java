package com.example.milkteaking;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.milkteaking.core.activitys.ProxyActivity;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IError;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.IRequest;
import com.milkteaking.core.net.callback.ISuccess;

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
                .url("http://127.0.0.1")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                    }
                })
                .failed(new IFailed() {
                    @Override
                    public void onFailed(Throwable t) {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .request(new IRequest() {
                    @Override
                    public void onRequestStart() {

                    }

                    @Override
                    public void onRequestEnd() {

                    }
                })
                .build();
    }
}
