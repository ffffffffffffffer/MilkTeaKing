package com.milkteaking.core.ui.camera;

import android.net.Uri;

/**
 * @author TanJJ
 * @time 2018/5/28 10:36
 * @des 相机Bean
 */

public class CameraImageBean {

    private Uri mUri;

    private CameraImageBean() {
    }

    private static CameraImageBean mImageBean = new CameraImageBean();

    public static CameraImageBean Instance() {
        return mImageBean;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
    }
}
