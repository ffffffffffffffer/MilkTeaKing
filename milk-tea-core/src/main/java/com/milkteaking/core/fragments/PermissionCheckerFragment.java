package com.milkteaking.core.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.milkteaking.core.ui.camera.CameraImageBean;
import com.milkteaking.core.ui.camera.MilkTeaCamera;
import com.milkteaking.core.ui.camera.RequestCodes;
import com.milkteaking.core.util.callback.CallbackManager;
import com.milkteaking.core.util.callback.CallbackType;
import com.milkteaking.core.util.callback.IGlobalCallback;
import com.milkteaking.core.util.log.MilkTeaLogger;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author TanJJ
 * @time 2018/5/5 0:32
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.fragments
 * @des 6.0的权限处理
 */
@RuntimePermissions
public abstract class PermissionCheckerFragment extends BaseFragment {

    // 不是直接调用的方法
    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showCamera() {
        MilkTeaCamera.start(this);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForCamera(final PermissionRequest request) {
        showRationaleDialog(request);
    }

    private void showRationaleDialog(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setMessage("权限管理")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .show();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForCamera() {
        ToastUtils.showShort("不允许拍照!");
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForCamera() {
        ToastUtils.showShort(" 永远拒绝权限!");
    }

    // 这个才是提供给外界调用的方法
    public void startCameraWithCheck() {
        PermissionCheckerFragmentPermissionsDispatcher.showCameraWithCheck(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK || requestCode == UCrop.REQUEST_CROP) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    Uri uri = CameraImageBean.Instance().getUri();
                    // [BUG]7.0以上的时候UCrop不能读取Uri成功,因为7.0以上的图片路径不能是本地真实路径的Uri
                    // 裁剪图片
                    // 将裁剪的图片覆盖原来的图片
                    // 图片的Uri格式
                    // content://com.android.providers.media.documents/document/image%3A1413
                    // file:///storage/emulated/0/Android/data/com.diabin.festec
                    // .example/cache/IMG_20180427_122032.jpg
                    // UCrop.of(uri, uri)
                    //       .withMaxResultSize(400, 400)
                    //       .start(getActivity(), this);

                    // 跳过了裁剪,直接显示原始图片
                    @SuppressWarnings("unchecked")
                    IGlobalCallback<Uri> callback2 = CallbackManager.getInstance().getCallback(CallbackType.NO_CROP);
                    if (callback2 != null) {
                        callback2.executeCallback(uri);
                    }
                    break;
                case RequestCodes.PICK_PHOTO:
                    // 获取相册中选中的图片的uri路径
                    uri = data.getData();
                    // 创建裁剪后存放的图片位置
                    Uri cropFile = MilkTeaCamera.createCropFile();
                    UCrop.of(uri, cropFile)
                            .withMaxResultSize(400, 400)
                            .start(getContext(), this);
                    break;
                case RequestCodes.CROP_PHOTO:
                    Uri output = null;
                    // 处理裁剪后的图片
                    // 通过uCrop来读取intent中的图片uri
                    if (data != null) {
                        output = UCrop.getOutput(data);
                    }
                    @SuppressWarnings("unchecked")
                    IGlobalCallback<Uri> callback = CallbackManager.getInstance().getCallback(CallbackType.NO_CROP);
                    if (callback != null) {
                        callback.executeCallback(output);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    ToastUtils.showShort("裁剪出错了!");
                    break;
            }
        }
    }
}
