package com.milkteaking.core.net;

import com.milkteaking.core.net.callback.IError;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.IRequest;
import com.milkteaking.core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.RequestBody;

/**
 * @author TanJJ
 * @time 2018/5/5 9:55
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net
 * @des RestFul请求Api
 */

public class RestClient {
    private final String url;
    private final WeakHashMap<String, Object> params;
    private final RequestBody requestBody;
    private final ISuccess mSuccess;
    private final IFailed mFailed;
    private final IError mError;
    private final IRequest mRequest;

    public RestClient(String url, WeakHashMap<String, Object> params, RequestBody requestBody, ISuccess success,
                      IFailed failed, IError error, IRequest request) {
        this.url = url;
        this.params = params;
        this.requestBody = requestBody;
        mSuccess = success;
        mFailed = failed;
        mError = error;
        mRequest = request;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }
}
