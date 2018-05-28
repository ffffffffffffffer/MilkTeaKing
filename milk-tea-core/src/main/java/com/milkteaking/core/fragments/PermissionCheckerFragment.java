package com.milkteaking.core.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.milkteaking.core.ui.camera.MilkTeaCamera;

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
}
