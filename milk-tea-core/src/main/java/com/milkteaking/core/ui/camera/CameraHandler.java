package com.milkteaking.core.ui.camera;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.milkteaking.core.R;
import com.milkteaking.core.fragments.PermissionCheckerFragment;
import com.milkteaking.core.util.file.FileUtil;

import java.io.File;

/**
 * @author TanJJ
 * @time 2018/5/27 16:26
 * @des Camera处理器
 */

public class CameraHandler implements View.OnClickListener {

    private final AlertDialog mAlertDialog;
    private final PermissionCheckerFragment mPermissionCheckerFragment;

    public CameraHandler(PermissionCheckerFragment permissionCheckerFragment) {
        mAlertDialog = new AlertDialog.Builder(permissionCheckerFragment.getContext()).create();
        mPermissionCheckerFragment = permissionCheckerFragment;
    }

    public void beginCameraDialog() {
        mAlertDialog.show();
        Window window = mAlertDialog.getWindow();
        if (window != null) {
            // 设置自定义layout
            window.setContentView(R.layout.dialog_camera_panel);
            // 设置enter/exit动画
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            // 设置位置
            window.setGravity(Gravity.BOTTOM);
            // 设置背景
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // 设置属性
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            attributes.dimAmount = 0.5f;
            window.setAttributes(attributes);
            // 设置点击事件
            window.findViewById(R.id.photo_dialog_btn_take).setOnClickListener(this);
            window.findViewById(R.id.photo_dialog_btn_native).setOnClickListener(this);
            window.findViewById(R.id.photo_dialog_btn_cancel).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.photo_dialog_btn_take) {
            takePhoto();
            mAlertDialog.cancel();
        } else if (v.getId() == R.id.photo_dialog_btn_native) {
            pickPhoto();
            mAlertDialog.cancel();
        } else if (v.getId() == R.id.photo_dialog_btn_cancel) {
            mAlertDialog.cancel();
        }
    }

    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        mPermissionCheckerFragment.startActivityForResult(Intent.createChooser(intent, "选择图片"), RequestCodes
                .PICK_PHOTO);
    }

    private void takePhoto() {
        // 返回这种文件名 IMG_20180426_212108.jpg
        String photoName = getPhotoName();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(mPermissionCheckerFragment.getActivity().getExternalCacheDir(), photoName);


        // 兼容7.0以上的写法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 调用FileProvider的getUriForFile()方法将File象转换成一个封装过的Uri对象
            // 从Android7.0系统开始，直接使用本地真实路径的Uri被认为是不安全的，会抛出 一个 FileUriExposedException 异常
            Uri imageUri = FileProvider.getUriForFile(mPermissionCheckerFragment.getContext(), "com.milkteaking.core" +
                    ".fileprovider", file);
            // 储存这个uri
            CameraImageBean.Instance().setUri(imageUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            Uri uri = Uri.fromFile(file);
            CameraImageBean.Instance().setUri(uri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mPermissionCheckerFragment.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);
    }

    private String getPhotoName() {
        return FileUtil.getFileNameByTime("IMG", "jpg");
    }
}
