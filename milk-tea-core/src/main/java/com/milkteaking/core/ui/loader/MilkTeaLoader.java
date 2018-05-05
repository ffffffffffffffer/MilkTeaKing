package com.milkteaking.core.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.milkteaking.core.R;
import com.milkteaking.core.util.dimen.DimenUtil;

import java.util.ArrayList;

/**
 * @author TanJJ
 * @time 2018/5/5 16:42
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.ui.loader
 * @des 提供给外部使用的Loader
 */

public class MilkTeaLoader {
    private static final int SCALE_SIZE = 8;
    private static final int OFFSET_SIZE = 10;
    private static final String DEFAULT_STYLE = LoaderStyle.PacmanIndicator.name();
    // 定义dialog储存集合
    private static final ArrayList<AppCompatDialog> DIALOGS = new ArrayList<>();

    /**
     * 显示loading
     *
     * @param type 显示的样式
     */
    static void showLoader(Context context, String type) {
        // R.style.dialog是很重要的,没有传入的话只显示一条直线
        AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        dialog.setContentView(LoaderCreator.creator(context, type));
        // 获取屏幕宽高
        int screenWidth = DimenUtil.getScreenWidth();
        int screenHeight = DimenUtil.getScreenHeight();

        // 获取dialog的布局参数
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = screenWidth / SCALE_SIZE;
            attributes.height = screenHeight / SCALE_SIZE;
            attributes.height = attributes.height + screenHeight / OFFSET_SIZE;
            attributes.gravity = Gravity.CENTER;
        }

        // 添加dialog进集合
        DIALOGS.add(dialog);
        dialog.show();
    }

    public static void showLoader(Context context, Enum<LoaderStyle> type) {
        showLoader(context, type.name());
    }

    public static void showLoader(Context context) {
        showLoader(context, DEFAULT_STYLE);
    }

    public static void stopLoader() {
        // 关闭所有的dialog
        for (AppCompatDialog dialog : DIALOGS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }

}
