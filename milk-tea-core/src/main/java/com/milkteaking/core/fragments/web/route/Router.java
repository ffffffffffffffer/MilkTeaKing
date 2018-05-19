package com.milkteaking.core.fragments.web.route;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.fragments.web.WebFragment;
import com.milkteaking.core.fragments.web.WebFragmentImpl;

/**
 * @author TanJJ
 * @time 2018/5/19 20:25
 * @des 路由器
 */

public class Router {
    private Router() {
    }

    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    public boolean handleWebUrl(WebFragment webFragment, String url) {
        if (url.contains("tel:")) {
            callPhone(webFragment, url);
            return true;
        }
        //获取WebDelegate的父类,可以根据这个判断当前的Url是以什么形式加载的
        MilkTeaFragment parentFragment = webFragment.getParentFragment(0);
        WebFragmentImpl webFragmentImpl = WebFragmentImpl.create(url);
        if (parentFragment == null) {
            // 如果LatteDelegate为空,就说明当前这个url并不是通过点击界面上的发现的Tab按钮进入的,
            // 那就通过WebDelegate来加载webDelegate
            webFragment.start(webFragmentImpl);
        } else {
            //第一次加载Url到WebView时才会使用LatteDelegate来加载的
            parentFragment.start(webFragmentImpl);
        }
        return true;
    }

    public void loadPage(WebFragment webFragment, String url) {
        loadPage(webFragment.getWebView(), url);
    }

    private void loadPage(WebView webView, String url) {
        // 如果Url是包含Http/Https/file开头的协议就调用loadWebPage
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            // 没有就调用加载本地页面的方法
            loadLocalPage(webView, url);
        }

    }

    private void loadLocalPage(WebView webView, String url) {
        // 具体可以参考:URLUtil方法里的ASSET_BASE变量
        // 在Url前面加了file:///android_asset/就能从assets目录里找
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new RuntimeException("webView is null!");
        }
    }


    private void callPhone(WebFragment webFragment, String url) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(url));
        ContextCompat.startActivity(webFragment.getContext(), intent, null);
    }
}
