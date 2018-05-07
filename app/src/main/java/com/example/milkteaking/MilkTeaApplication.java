package com.example.milkteaking;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.milkteaking.core.app.MilkTea;
import com.milkteaking.core.net.interceptor.DebugInterceptor;
import com.milkteaking.ec.FontEcModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

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
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .configure();
        // 初始化AndroidUtilCode常用工具库
        Utils.init(this);
        // 初始化logger库
        initLogger();
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(4)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("MilkTeaKing custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
