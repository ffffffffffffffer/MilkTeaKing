package com.milkteaking.core.ui.camera;

import android.net.Uri;

import com.milkteaking.core.fragments.PermissionCheckerFragment;
import com.milkteaking.core.util.file.FileUtil;

import java.io.File;


/**
 * @author TanJJ
 * @time 2018/5/28 10:47
 * @des 照相机调用类(封装类)
 */

public class MilkTeaCamera {

    public static Uri createCropFile() {
        String fileNameByTime = FileUtil.getFileNameByTime("IMG", "jpg");
        // 封装路径
        File crop_img = FileUtil.createFile("crop_img", fileNameByTime);
        return Uri.parse(crop_img.getPath());
    }

    public static void start(PermissionCheckerFragment fragment) {
        new CameraHandler(fragment).beginCameraDialog();
    }

}
