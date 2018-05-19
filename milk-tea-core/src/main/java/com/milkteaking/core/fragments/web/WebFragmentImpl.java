package com.milkteaking.core.fragments.web;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.milkteaking.core.fragments.web.route.RouteKeys;
import com.milkteaking.core.fragments.web.route.Router;

/**
 * @author TanJJ
 * @time 2018/5/19 19:59
 * @des WebFragment实现类
 */

public class WebFragmentImpl extends WebFragment implements IWebViewInitializer {
    @Override
    public Object getLayout() {
        return getWebView();
    }

    public static WebFragmentImpl create(String url) {
        // 自举
        Bundle argument = new Bundle();
        argument.putString(RouteKeys.URI.name(), url);
        // 创建自身
        WebFragmentImpl webFragment = new WebFragmentImpl();
        webFragment.setArguments(argument);
        return webFragment;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        // 加载WebView页面
        if (getUrl() != null) {
            // 用原生的方式模拟Web跳转
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return null;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return WebViewInitializer.createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        return null;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return null;
    }
}
