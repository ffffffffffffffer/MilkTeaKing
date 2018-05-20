package com.example.milkteaking.event;

import android.webkit.WebView;

import com.blankj.utilcode.util.ToastUtils;
import com.milkteaking.core.fragments.web.event.Event;

/**
 * @author TanJJ
 * @time 2018/5/20 8:53
 * @des Event实现类
 */

public class TestEvent extends Event {
    @Override
    public String execute(final String params) {
        ToastUtils.showShort(params);
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
