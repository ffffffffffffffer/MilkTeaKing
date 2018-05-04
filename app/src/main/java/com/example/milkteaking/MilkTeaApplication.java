package com.example.milkteaking;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.milkteaking.core.app.MilkTea;
import com.milkteaking.ec.FontEcModule;

/**
 * @author TanJJ
 * @time 2018/5/4 23:32
 * @ProjectName MilkTeaKing
 * @PackageName com.example.milkteaking
 */

public class MilkTeaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化配置
        MilkTea.init(this)
                .withApiHost("http:127.0.0.1")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .configure();
    }
}
