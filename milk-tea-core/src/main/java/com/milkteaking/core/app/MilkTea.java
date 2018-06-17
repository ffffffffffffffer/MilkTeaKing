package com.milkteaking.core.app;

import android.content.Context;
import android.os.Handler;

/**
 * @author TanJJ
 * @time 2018/5/4 23:35
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.app
 * @des 对全局信息进行存储
 */

public class MilkTea {

    /**
     * 开始对Configurator进行初始化
     *
     * @param context 全局的context
     *
     * @return 返回Configurator
     */
    public static Configurator init(Context context) {
        Configurator.getInstance().withApplicationContext(context);
        return Configurator.getInstance();
    }

    /**
     * 获取全局唯一的context
     *
     * @return 返回全局的context
     */
    public static Context getApplicationContext() {
        return Configurator.getInstance().getConfigurate(ConfigType.APPLICATION_CONTEXT);
    }

    /**
     * 获取key对应的value
     * @param key 配置的enum
     * @return 返回调用者希望的类型
     */
    public static <T> T getConfigurate(Enum<ConfigType> key) {
        return Configurator.getInstance().getConfigurate(key);
    }
    /**
     * 获取全局唯一的handle
     *
     * @return 返回全局的context
     */
    public static Handler getHandler() {
        return Configurator.getInstance().getConfigurate(ConfigType.HANDLER);
    }
}
