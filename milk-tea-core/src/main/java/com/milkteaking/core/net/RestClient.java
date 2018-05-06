package com.milkteaking.core.net;

import android.content.Context;

import com.milkteaking.core.net.callback.IError;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.IRequest;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.net.callback.RequestCallback;
import com.milkteaking.core.net.download.DownloadHandler;
import com.milkteaking.core.ui.loader.LoaderStyle;
import com.milkteaking.core.ui.loader.MilkTeaLoader;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private final File mFile;
    private final String mDownload_dir;
    private final String mExtension;
    private final String mName;

    public RestClient(String url, WeakHashMap<String, Object> params, RequestBody requestBody, ISuccess success,
                      IFailed failed, IError error, IRequest request, Context context, LoaderStyle style, File file,
                      String download_dir,
                      String extension,
                      String name
    ) {
        this.url = url;
        this.params = params;
        this.requestBody = requestBody;
        mSuccess = success;
        mFailed = failed;
        mError = error;
        mRequest = request;
        mContext = context;
        mLoaderStyle = style;
        mFile = file;
        mDownload_dir = download_dir;
        mExtension = extension;
        mName = name;
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
            case POST_RAW:
                call = restService.postRaw(url, params);
                break;
            case PUT:
                call = restService.put(url, params);
                break;
            case PUT_RAW:
                call = restService.putRaw(url, params);
                break;
            case DELETE:
                call = restService.delete(url, params);
                break;
            case UPLOAD:
                RequestBody body = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), mFile);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", mFile.getName(), body);
                call = restService.upload(url, part);
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
        if (requestBody == null) {
            request(HttpMethod.POST);
        } else {
            if (!params.isEmpty()) {
                throw new RuntimeException("Params must be null.");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public void put() {
        if (requestBody == null) {
            request(HttpMethod.PUT);
        } else {
            if (!params.isEmpty()) {
                throw new RuntimeException("Params must be null.");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public void delete() {
        request(HttpMethod.DELETE);
    }

    public void download() {
        new DownloadHandler(
                url,
                params,
                mSuccess,
                mFailed,
                mError,
                mRequest,
                mDownload_dir,
                mExtension,
                mName)
                .handleDownload();
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }
}
