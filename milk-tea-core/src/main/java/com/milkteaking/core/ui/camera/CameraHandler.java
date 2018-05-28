package com.milkteaking.core.ui.camera;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.FileUtils;
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
        File file = new File(mPermissionCheckerFragment.getContext().getExternalCacheDir(), photoName);
        // 兼容7.0以上的写法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, file.getPath());
            // 插入图片路径到数据库中,返回插入的路径uri
            // 完整的内容的uri    content://media/external/images/media
            Uri uri = mPermissionCheckerFragment.getContext().getContentResolver().insert(MediaStore.Images.Media
                    .EXTERNAL_CONTENT_URI, contentValues);
            // 需要将uri路径转化为真实路径
            File realFile = FileUtils.getFileByPath(FileUtil.getRealFilePath(mPermissionCheckerFragment.getContext
                    (), uri));
            // 将真实的file转换成uri
            Uri realUri = Uri.fromFile(realFile);
            // 储存这个uri
            CameraImageBean.Instance().setUri(realUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
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
