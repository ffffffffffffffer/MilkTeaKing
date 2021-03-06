package com.example.milkteaking;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.example.milkteaking.event.TestEvent;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.milkteaking.core.app.MilkTea;
import com.milkteaking.core.net.interceptor.DebugInterceptor;
import com.milkteaking.core.net.rx.AddCookieInterceptor;
import com.milkteaking.core.util.callback.CallbackManager;
import com.milkteaking.core.util.callback.CallbackType;
import com.milkteaking.core.util.callback.IGlobalCallback;
import com.milkteaking.ec.FontEcModule;
import com.milkteaking.ec.database.DataBaseManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import cn.jpush.android.api.JPushInterface;

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
                .withInterceptor(new DebugInterceptor("index11111", R.raw.test))
                .withJavaScript("milkTea")
                .withWebViewEvent("test", new TestEvent())
                //添加Cookie同步
                .withWegHost("www.baidu.com")
                .withInterceptor(new AddCookieInterceptor())
                .configure();
        // 初始化AndroidUtilCode常用工具库
        Utils.init(this);
        // 初始化logger库
        initLogger();
        // 初始化Stetho()
        initStetho();
        // 初始化数据库
        initDataBase();
        // 极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        // 依赖倒置
        pushCallback();
    }

    private void pushCallback() {
        // 依赖倒置,不同Module间通信
        // 有三种方式: 反射/接口/消息机制
        // 由于在Application中对推送设置开关比较合理,涉及到一个问题,就是不同Module之间的方法调用,用了全局的回调方法来实现
        CallbackManager.getInstance().addCallBack(CallbackType.PUSH_OPEN, new IGlobalCallback() {
            @Override
            public void executeCallback(Object args) {
                if (JPushInterface.isPushStopped(getApplicationContext())) {
                    // 关闭了就开启
                    JPushInterface.setDebugMode(true);
                    JPushInterface.init(MilkTeaApplication.this);
                }
            }
        });
        CallbackManager.getInstance().addCallBack(CallbackType.PUSH_CLOSE, new IGlobalCallback() {
            @Override
            public void executeCallback(Object args) {
                if (!JPushInterface.isPushStopped(getApplicationContext())) {
                    //  开启就关闭
                    JPushInterface.stopPush(getApplicationContext());
                }
            }
        });
    }

    private void initDataBase() {
        DataBaseManager.getInstance().init(this);
    }

    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
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
