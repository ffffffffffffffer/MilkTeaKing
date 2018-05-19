package com.milkteaking.core.fragments.web.client;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.milkteaking.core.fragments.web.WebFragment;
import com.milkteaking.core.fragments.web.route.Router;
import com.milkteaking.core.util.log.MilkTeaLogger;

/**
 * @author TanJJ
 * @time 2018/5/19 22:40
 * @des WebViewClient的实现类
 */

public class WebViewClientImpl extends WebViewClient {
    private final WebFragment mWebFragment;

    public WebViewClientImpl(WebFragment webFragment) {
        mWebFragment = webFragment;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    // 页面跳转时的回调方法,可以通过这个方法决定是通过原生跳转还是浏览器跳转
    // 由于上面的那个方法是新加入的,低版本兼容性不稳,所有就用下面的过时方法
    // return true 当前WebView处理
    // return false 跳转浏览器处理
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        MilkTeaLogger.e(WebViewClientImpl.class.getSimpleName(), url);
        //由原生跳转加载
        return Router.getInstance().handleWebUrl(mWebFragment, url);
    }
}
