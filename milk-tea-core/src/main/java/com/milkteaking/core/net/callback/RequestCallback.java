package com.milkteaking.core.net.callback;

import android.os.Handler;

import com.milkteaking.core.ui.loader.LoaderStyle;
import com.milkteaking.core.ui.loader.MilkTeaLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author TanJJ
 * @time 2018/5/5 13:55
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net.callback
 * @des 异步请求的回调接口实现类
 */

public class RequestCallback implements Callback<String> {
    private final ISuccess mSuccess;
    private final IFailed mFailed;
    private final IError mError;
    private final IRequest mRequest;
    private final LoaderStyle mStyle;
    private static final Handler HANDLER = new Handler();

    public RequestCallback(ISuccess success, IFailed failed, IError error, IRequest request, LoaderStyle style) {
        mSuccess = success;
        mFailed = failed;
        mError = error;
        mRequest = request;
        mStyle = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (mSuccess != null) {
                mSuccess.onSuccess(response.body());
            }
        } else {
            if (mError != null) {
                mError.onError(response.code(), response.message());
            }
        }
        stopLoading();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (mFailed != null) {
            mFailed.onFailed(t);
        }
        if (mRequest != null) {
            mRequest.onRequestEnd();
        }
        stopLoading();
    }

    private void stopLoading() {
        if (mStyle != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MilkTeaLoader.stopLoader();
                }
            }, 2000);
        }
    }
}
