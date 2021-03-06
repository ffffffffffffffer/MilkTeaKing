package com.milkteaking.core.fragments.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.milkteaking.core.app.ConfigType;
import com.milkteaking.core.app.MilkTea;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.fragments.web.route.MilkTeaWebInterface;
import com.milkteaking.core.fragments.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author TanJJ
 * @time 2018/5/19 19:18
 * @des 发现页面要加载的WebFragment
 */

public abstract class WebFragment extends MilkTeaFragment {

    private WebView mWebView;
    private MilkTeaFragment mMilkTeaFragment;
    private static final ReferenceQueue<WebView> WEB_VIEW_REFERENCE_QUEUE = new ReferenceQueue<>();
    private String mUrl;
    // 只有初始化完后才是true,其余都是false
    private boolean isWebViewAvailable;

    public WebFragment() {
    }


    public abstract IWebViewInitializer setInitializer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUrl = arguments.getString(RouteKeys.URI.name());
        }
        initWebView();
    }

    @SuppressLint("AddJavascriptInterface")
    private void initWebView() {
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        } else {
            IWebViewInitializer iWebViewInitializer = setInitializer();
            if (iWebViewInitializer != null) {
                // 初始化WebView,并且添加入弱引用里
                // 直接new WebView出来可以避免内存溢出
                // 创建一个弱引用集合储存WebView
                WeakReference<WebView> webViewWeakReference = new WeakReference<WebView>(new WebView(getContext()),
                        WEB_VIEW_REFERENCE_QUEUE);
                // 获取新创建的WebView对象
                WebView webView = webViewWeakReference.get();
                // 通过initWebView返回一个初始化完后的WebView
                mWebView = iWebViewInitializer.initWebView(webView);
                // 设置Client
                mWebView.setWebViewClient(iWebViewInitializer.initWebViewClient());
                mWebView.setWebChromeClient(iWebViewInitializer.initWebChromeClient());
                // 当原生与Web的JavaScript交互时会从LatteWebInterface找定义了注解@JavascriptInterface的类
                // 然后根据自己定义的 latte.方法名() 来交互
                // 支持javaScript
                String name = MilkTea.getConfigurate(ConfigType.JAVASCRIPT_INTERFACE);
                mWebView.addJavascriptInterface(MilkTeaWebInterface.create(this), name);
                isWebViewAvailable = true;
            } else {
                throw new RuntimeException("IWebViewInitializer is null!");
            }
        }
    }

    public void setTopFragment(MilkTeaFragment fragment) {
        mMilkTeaFragment = fragment;
    }

    public MilkTeaFragment getTopFragment() {
        return mMilkTeaFragment;
    }

    public WebView getWebView() {
        if (mWebView != null) {
            return isWebViewAvailable ? mWebView : null;
        } else {
            throw new RuntimeException("WebView is null!");
        }
    }

    public String getUrl() {
        if (mUrl != null) {
            return mUrl;
        } else {
            throw new RuntimeException("Url is null!");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isWebViewAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }
}
