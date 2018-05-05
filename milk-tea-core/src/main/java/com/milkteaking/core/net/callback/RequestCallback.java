package com.milkteaking.core.net.callback;

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

    public RequestCallback(ISuccess success, IFailed failed, IError error, IRequest request) {
        mSuccess = success;
        mFailed = failed;
        mError = error;
        mRequest = request;
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

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (mFailed != null) {
            mFailed.onFailed(t);
        }
        if (mRequest != null) {
            mRequest.onRequestEnd();
        }
    }
}
