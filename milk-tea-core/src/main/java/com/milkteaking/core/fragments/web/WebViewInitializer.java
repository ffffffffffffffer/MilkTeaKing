package com.milkteaking.core.fragments.web;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author TanJJ
 * @time 2018/5/19 20:00
 * @des WebView初始化实现类
 */

public class WebViewInitializer {
    public static WebView createWebView(WebView webView) {
        // [Api-19] 才能用这个方法,并且类名调用
        // 启用此标志以方便调试WebViews中的web布局和JavaScript代码
        webView.setWebContentsDebuggingEnabled(true);
        // 不能横向滑动
        webView.setHorizontalScrollBarEnabled(true);
        // 不能纵向滑动
        webView.setVerticalScrollBarEnabled(true);
        // 允许截图
        webView.setDrawingCacheEnabled(true);
        // 屏蔽长按事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        // 初始化WebSettings
        WebSettings settings = webView.getSettings();
        // 在用户代理里面加入自己独有的信息,可以由此判断是否是我们想要的
        String userAgentString = settings.getUserAgentString();
        settings.setUserAgentString(userAgentString + "milkTea");
        // 隐藏缩放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        // 禁止缩放
        settings.setSupportZoom(false);
        // 文件权限,允许访问哪些文件
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        // 缓存相关
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        return webView;
    }
}
