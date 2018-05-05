package com.milkteaking.core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.milkteaking.core.app.MilkTea;

/**
 * @author TanJJ
 * @time 2018/4/3 14:34
 * @ProjectName FestEC
 * @PackageName com.diabin.lattecore.util
 * @des 尺寸工具类
 */

public class DimenUtil {

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
   public static int getScreenWidth() {
        Resources resources = MilkTea.getApplicationContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getScreenHeight() {
        Resources resources = MilkTea.getApplicationContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

}
