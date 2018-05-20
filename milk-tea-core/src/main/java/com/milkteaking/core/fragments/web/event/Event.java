package com.milkteaking.core.fragments.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.milkteaking.core.fragments.web.WebFragment;


/**
 * @author TanJJ
 * @time 2018/5/20 8:51
 */

public abstract class Event implements IEvent {
    private Context mContext;
    private String mUrl;
    private String mAction;
    private WebFragment mWebFragment;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        mAction = action;
    }

    public WebFragment getWebFragment() {
        return mWebFragment;
    }

    public WebView getWebView() {
        return mWebFragment.getWebView();
    }

    public void setWebFragment(WebFragment webFragment) {
        mWebFragment = webFragment;
    }

}
