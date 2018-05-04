package com.milkteaking.core.app;

import android.content.Context;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.WeakHashMap;

/**
 * @author TanJJ
 * @time 2018/5/4 22:57
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.app
 * @des 专门用来存储和获取配置信息的
 */

public class Configurator {
    // 定义储存配置信息的集合
    // 有多种类型的配置信息,就用Object
    private WeakHashMap<String, Object> CONFIGS = new WeakHashMap<>();
    // 储存文字图片
    private ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    // 使用单例的方式调用
    private Configurator() {
        // 将配置完成信息默认置为false
        CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    private static class Holder {
        private static final Configurator CONFIGURATOR = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.CONFIGURATOR;
    }

    public void configure() {
        // 初始化icon
        initIcon();
        // 将配置完成信息置为true
        CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    private void initIcon() {
        // 如果集合中有东西才执行
        if (ICONS.size() > 0) {
            // 取出第一个元素
            // 这样做的原因是不用多次调用Iconify.with
            Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            // 遍历集合
            int size = ICONS.size();
            for (int i = 0; i < size; i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    /**
     * 需要取出配置时都要先检查一下配置完成了没有
     */
    private void checkConfiguration() {
        boolean isReady = (boolean) CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("configuration is not ready,call configure() please.");
        }
    }

    /**
     * 取出配置信息
     *
     * @param key 要取出配置的key(限制传入的一定时ConfigType)
     * @param <T> 返回传入的key对应的value(通过泛型的形式规定,可以减少调用时的强制转换)
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getConfigurate(Enum<ConfigType> key) {
        // 配置完成后才能取出
        checkConfiguration();
        return (T) CONFIGS.get(key.name());
    }

    /**
     * 获取配置集合
     *
     * @return 返回配置集合
     */
    public WeakHashMap<String, Object> getConfigurations() {
        return CONFIGS;
    }

    public Configurator withApiHost(String host) {
        CONFIGS.put(ConfigType.API_HOST.name(), host);
        return this;
    }

    public Configurator withIcon(IconFontDescriptor icon) {
        ICONS.add(icon);
        return this;
    }

    Configurator withApplicationContext(Context context) {
        CONFIGS.put(ConfigType.APPLICATION_CONTEXT.name(), context);
        return this;
    }


}
