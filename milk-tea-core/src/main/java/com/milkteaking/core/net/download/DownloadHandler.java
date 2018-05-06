package com.milkteaking.core.net.download;

import android.os.AsyncTask;

import com.milkteaking.core.net.RestCreator;
import com.milkteaking.core.net.callback.IError;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.IRequest;
import com.milkteaking.core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author TanJJ
 * @time 2018/5/6 13:37
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net.download
 * @des 下载助手
 */

public class DownloadHandler {
    private final String url;
    private final WeakHashMap<String, Object> params;
    private final ISuccess mSuccess;
    private final IFailed mFailed;
    private final IError mError;
    private final IRequest mRequest;
    private final String mDownload_dir;
    private final String mExtension;
    private final String mName;

    public DownloadHandler(String url, WeakHashMap<String, Object> params, ISuccess success, IFailed failed, IError
            error, IRequest request, String download_dir, String extension, String name) {
        this.url = url;
        this.params = params;
        mSuccess = success;
        mFailed = failed;
        mError = error;
        mRequest = request;
        mDownload_dir = download_dir;
        mExtension = extension;
        mName = name;
    }

    public void handleDownload() {
        if (mRequest != null) {
            mRequest.onRequestStart();
        }

        // download 请求
        RestCreator.getRestService().download(url, params).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    SaveFileTask fileTask = new SaveFileTask(mSuccess, mRequest);
                    fileTask.executeOnExecutor(
                            AsyncTask.THREAD_POOL_EXECUTOR,
                            mDownload_dir,
                            mExtension,
                            mName,
                            body
                    );
                    // 任务完成后才执行
                    if (fileTask.isCancelled()) {
                        if (mRequest != null) {
                            mRequest.onRequestEnd();
                        }
                    }

                } else {
                    if (mError != null) {
                        mError.onError(response.code(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (mFailed != null) {
                    mFailed.onFailed(t);
                }

            }
        });
    }

}
