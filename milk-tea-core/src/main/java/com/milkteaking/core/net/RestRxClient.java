package com.milkteaking.core.net;

import android.content.Context;

import com.milkteaking.core.net.callback.IRequest;
import com.milkteaking.core.ui.loader.LoaderStyle;
import com.milkteaking.core.ui.loader.MilkTeaLoader;

import java.io.File;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author TanJJ
 * @time 2018/5/7 19:52
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net
 * @des RxJava和Retrofit整合使用
 */

public class RestRxClient {
    private final String mUrl;
    private final WeakHashMap<String, Object> mParams;
    private final RequestBody mRequestBody;
    private final LoaderStyle mLoaderStyle;
    private final File mFile;
    private final Context mContext;

    public RestRxClient(
            String url,
            WeakHashMap<String, Object> params,
            RequestBody requestBody,
            LoaderStyle loaderStyle,
            File file,
            Context context) {
        mUrl = url;
        mParams = params;
        mRequestBody = requestBody;
        mLoaderStyle = loaderStyle;
        mFile = file;
        mContext = context;
    }

    private Observable<String> request(HttpMethod httpMethod) {
        if (mLoaderStyle != null) {
            MilkTeaLoader.showLoader(mContext, mLoaderStyle);
        }

        Observable<String> observable = null;

        RestRxService restRxService = RestCreator.getRestRxService();

        switch (httpMethod) {
            case GET:
                observable = restRxService.get(mUrl, mParams);
                break;
            case POST:
                observable = restRxService.post(mUrl, mParams);
                break;
            case POST_RAW:
                observable = restRxService.postRaw(mUrl, mRequestBody);
                break;
            case PUT:
                observable = restRxService.put(mUrl, mParams);
                break;
            case PUT_RAW:
                observable = restRxService.putRaw(mUrl, mRequestBody);
                break;
            case DELETE:
                observable = restRxService.delete(mUrl, mParams);
                break;
            case UPLOAD:
                RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), mFile);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", mFile.getName(), requestBody);
                observable = restRxService.upload(mUrl, part);
                break;
            default:
                break;
        }
        return observable;
    }

    public Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public Observable<String> post() {
        if (mRequestBody == null) {
            return request(HttpMethod.POST);
        } else {
            if (mParams != null) {
                throw new RuntimeException("Params must be null.");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public Observable<String> put() {
        if (mRequestBody == null) {
            return request(HttpMethod.PUT);
        } else {
            if (mParams != null) {
                throw new RuntimeException("Params must be null.");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public Observable<String> upload() {
        if (mRequestBody != null) {
            return request(HttpMethod.UPLOAD);
        } else {
            throw new RuntimeException("RequestBody can't null.");
        }
    }

    public Observable<ResponseBody> download() {
        return RestCreator.getRestRxService().download(mUrl, mParams);
    }

    public static RestRxClientBuilder build() {
        return new RestRxClientBuilder();
    }
}
