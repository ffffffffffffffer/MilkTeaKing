package com.milkteaking.core.net;

import android.content.Context;

import com.milkteaking.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author TanJJ
 * @time 2018/5/7 19:53
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net
 */

public class RestRxClientBuilder {
    private String mUrl;
    private WeakHashMap<String, Object> mParams = RestCreator.getParams();
    private RequestBody mRequestBody;
    private LoaderStyle mLoaderStyle;
    private File mFile;
    private Context mContext;

    public RestRxClientBuilder url(String url) {
        mUrl = url;
        return this;
    }

    public RestRxClientBuilder raw(String json) {
        mRequestBody = RequestBody.create(MediaType.parse("application/json"), json);
        return this;
    }

    public RestRxClientBuilder loaderStyle(Context context, LoaderStyle loaderStyle) {
        mContext = context;
        mLoaderStyle = loaderStyle;
        return this;
    }

    public RestRxClientBuilder loaderStyle(Context mContext) {
        return loaderStyle(mContext, LoaderStyle.BallBeatIndicator);
    }

    public RestRxClientBuilder file(File file) {
        mFile = file;
        return this;
    }

    public RestRxClientBuilder file(String name) {
        mFile = new File(name);
        return this;
    }


    public RestRxClient build() {
        return new RestRxClient(
                mUrl,
                mParams,
                mRequestBody,
                mLoaderStyle,
                mFile,
                mContext
        );
    }
}
