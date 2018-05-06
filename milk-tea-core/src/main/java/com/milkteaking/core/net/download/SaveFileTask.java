package com.milkteaking.core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.milkteaking.core.app.MilkTea;
import com.milkteaking.core.net.callback.IRequest;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * @author TanJJ
 * @time 2018/5/6 13:43
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net.download
 * @des 文件下载保存的异步处理
 */

public class SaveFileTask extends AsyncTask<Object, Void, File> {
    private final ISuccess mSuccess;
    private final IRequest mRequest;

    public SaveFileTask(ISuccess success, IRequest request) {
        mSuccess = success;
        mRequest = request;
    }

    @Override
    protected File doInBackground(Object... params) {
        // 取出数据
        String download_dir = (String) params[0];
        String extension = (String) params[1];
        String name = (String) params[2];
        ResponseBody responseBody = (ResponseBody) params[3];
        // 获取数据流
        InputStream inputStream = responseBody.byteStream();

        // 设置默认的后缀名
        if (download_dir == null || download_dir.equals("")) {
            extension = "";
        }

        if (name == null) {
            // 创建下载目录/文件名,IO读写后,返回下载路径
            return FileUtil.writeToDisk(inputStream, download_dir, extension.toUpperCase(), extension);
        } else {
            // 创建下载目录/文件名,IO读写后,返回下载路径
            return FileUtil.writeToDisk(inputStream, download_dir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        if (mSuccess != null) {
            mSuccess.onSuccess(file.getPath());
        }
        if (mRequest != null) {
            mRequest.onRequestEnd();
        }

        // 自动安装
        autoInstall(file);
    }

    private void autoInstall(File file) {
        if (file != null) {
            if (FileUtil.getExtension(file.getPath()).equals(".apk")) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                MilkTea.getApplicationContext().startActivity(intent);
            }
        }
    }


}
