package com.milkteaking.core.ui.loader;

import android.content.Context;

import com.blankj.utilcode.util.StringUtils;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * @author TanJJ
 * @time 2018/5/5 16:14
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.ui.loader
 * @des loader创建器
 */

public class LoaderCreator {
    // 定义Indicator缓存集合
    private static final WeakHashMap<String, Indicator> INDICATOR_MAP = new WeakHashMap();


    /**
     * 创建AVLoadingIndicatorView对象
     *
     * @param type loading的类型
     *
     * @return 返回AVLoadingIndicatorView
     */
    static AVLoadingIndicatorView creator(Context context, String type) {
        AVLoadingIndicatorView indicatorView = new AVLoadingIndicatorView(context);
        // 当缓存集合中没有才执行
        if (INDICATOR_MAP.get(type) == null) {
            // 获取type对应的indicator对象
            Indicator indicator = getIndicator(type);
            // 储存对象
            INDICATOR_MAP.put(type, indicator);
        }

        // 取出type对应的indicator
        Indicator indicator = INDICATOR_MAP.get(type);
        indicatorView.setIndicator(indicator);
        return indicatorView;
    }

    /**
     * 通过反射的方式获取Indicator对象
     *
     * @param name 需要获取的indicator的名字
     *
     * @return 返回Indicator
     */
    private static Indicator getIndicator(String name) {
        if (name == null || StringUtils.isEmpty(name)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (!name.contains(".")) {
            // com.wang.avi.indicators.PacmanIndicator 拼接的格式
            String packageName = AVLoadingIndicatorView.class.getPackage().getName();
            sb.append(packageName);
            sb.append(".");
            sb.append("indicators");
            sb.append(".");
        }
        sb.append(name);
        // 根据拼接好的类路径反射获取对象
        try {
            Class<?> indicatorClass = Class.forName(sb.toString());
            return (Indicator) indicatorClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
