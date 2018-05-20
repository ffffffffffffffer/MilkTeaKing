package com.milkteaking.core.fragments.web.event;

import android.webkit.WebView;

import com.blankj.utilcode.util.ToastUtils;

/**
 * @author TanJJ
 * @time 2018/5/20 8:53
 * @des Event实现类
 */

public class UndefindEvent extends Event {
    @Override
    public String execute(final String params) {
        ToastUtils.showShort(UndefindEvent.class.getSimpleName() + "-------" + params);
        if (params.contains("test")) {
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    // Android调用js代码
                    webView.evaluateJavascript("nativeCall()", null);
                }
            });
        }
        return null;
    }
}
