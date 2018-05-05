package com.milkteaking.core.net;

import android.content.Context;

import com.milkteaking.core.net.callback.IError;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.IRequest;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.net.callback.RequestCallback;
import com.milkteaking.core.ui.loader.LoaderStyle;
import com.milkteaking.core.ui.loader.MilkTeaLoader;

import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

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
    private final Context mContext;
    private final LoaderStyle mLoaderStyle;

    public RestClient(String url, WeakHashMap<String, Object> params, RequestBody requestBody, ISuccess success,
                      IFailed failed, IError error, IRequest request, Context context, LoaderStyle style) {
        this.url = url;
        this.params = params;
        this.requestBody = requestBody;
        mSuccess = success;
        mFailed = failed;
        mError = error;
        mRequest = request;
        mContext = context;
        mLoaderStyle = style;
    }

    private void request(HttpMethod method) {
        if (mRequest != null) {
            mRequest.onRequestStart();
        }
        Call<String> call = null;
        RestService restService = RestCreator.getRestService();

        if (mLoaderStyle != null) {
            MilkTeaLoader.showLoader(mContext, mLoaderStyle);
        }

        switch (method) {
            case GET:
                call = restService.get(url, params);
                break;
            case POST:
                call = restService.post(url, params);
                break;
            case PUT:
                call = restService.put(url, params);
                break;
            case DELETE:
                call = restService.delete(url, params);
                break;
            default:
                break;
        }
        if (call != null) {
            call.enqueue(getCallback());
        }
    }

    private Callback<String> getCallback() {
        return new RequestCallback(mSuccess, mFailed, mError, mRequest, mLoaderStyle);
    }

    public void get() {
        request(HttpMethod.GET);
    }

    public void post() {
        request(HttpMethod.POST);
    }

    public void put() {
        request(HttpMethod.PUT);
    }

    public void delete() {
        request(HttpMethod.DELETE);
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }
}
