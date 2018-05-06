package com.milkteaking.core.net;

import android.content.Context;

import com.milkteaking.core.net.callback.IError;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.IRequest;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author TanJJ
 * @time 2018/5/5 10:15
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net.callback
 * @des RestClient的Builder模式
 */

public class RestClientBuilder {

    private String mUrl;
    private WeakHashMap<String, Object> mParams = RestCreator.getParams();
    private RequestBody mRequestBody;
    private ISuccess mSuccess;
    private IFailed mFailed;
    private IError mError;
    private IRequest mRequest;
    private LoaderStyle mLoaderStyle;
    private Context mContext;
    private File mFile;

    public RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public RestClientBuilder params(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public RestClientBuilder params(WeakHashMap<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    /**
     * 解析原始数据为RequestBody对象
     *
     * @param raw json原始数据
     */
    public RestClientBuilder raw(String raw) {
        mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public RestClientBuilder success(ISuccess success) {
        mSuccess = success;
        return this;
    }

    public RestClientBuilder failed(IFailed failed) {
        mFailed = failed;
        return this;
    }

    public RestClientBuilder error(IError error) {
        mError = error;
        return this;
    }

    public RestClientBuilder request(IRequest request) {
        mRequest = request;
        return this;
    }

    public RestClientBuilder loader(Context context, LoaderStyle style) {
        mContext = context;
        mLoaderStyle = style;
        return this;
    }

    public RestClientBuilder loader(Context context) {
        mContext = context;
        mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public RestClientBuilder file(String name) {
        mFile = new File(name);
        return this;
    }

    public RestClientBuilder file(File file) {
        mFile = file;
        return this;
    }


    public RestClient build() {
        return new RestClient(mUrl, mParams, mRequestBody, mSuccess, mFailed, mError, mRequest, mContext,
                mLoaderStyle, mFile);
    }
}
