package com.milkteaking.core.fragments.web.client;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.milkteaking.core.app.ConfigType;
import com.milkteaking.core.app.MilkTea;
import com.milkteaking.core.fragments.web.IPageLoadListener;
import com.milkteaking.core.fragments.web.WebFragment;
import com.milkteaking.core.fragments.web.route.Router;
import com.milkteaking.core.util.log.MilkTeaLogger;
import com.milkteaking.core.util.storage.Preference;

/**
 * @author TanJJ
 * @time 2018/5/19 22:40
 * @des WebViewClient的实现类
 */

public class WebViewClientImpl extends WebViewClient {
    private final WebFragment mWebFragment;
    private IPageLoadListener mPageLoadListener;

    public void setPageLoadListener(IPageLoadListener pageLoadListener) {
        mPageLoadListener = pageLoadListener;
    }

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

    //页面开始加载回调
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mPageLoadListener != null) {
            mPageLoadListener.onLoadStart();
        }
    }

    //页面加载完回调
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mPageLoadListener != null) {
            mPageLoadListener.onLoadEnd();
        }
        // 同步cookie
        syncCookie();
    }

    private void syncCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        String webHost = MilkTea.getConfigurate(ConfigType.WEB_HOST);
        if (webHost != null) {
            if (cookieManager.hasCookies()) {
                String cookie = cookieManager.getCookie(webHost);
                if (cookie != null && TextUtils.isEmpty(webHost)) {
                    // 储存起来
                    Preference.addCustomAppProfile("cookie", cookie);
                }
            }
        }
    }
}
