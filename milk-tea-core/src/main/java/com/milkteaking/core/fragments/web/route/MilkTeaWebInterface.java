package com.milkteaking.core.fragments.web.route;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.milkteaking.core.fragments.web.WebFragment;
import com.milkteaking.core.fragments.web.event.Event;
import com.milkteaking.core.fragments.web.event.EventManager;

/**
 * @author TanJJ
 * @time 2018/5/19 19:45
 * @des Web接口(支持JavaScript时使用的)
 */

public class MilkTeaWebInterface {
    private final WebFragment WebFragment;

    private MilkTeaWebInterface(WebFragment webFragment) {
        WebFragment = webFragment;
    }

    public static MilkTeaWebInterface create(WebFragment webFragment) {
        return new MilkTeaWebInterface(webFragment);
    }

    @JavascriptInterface
    // 必须加入@JavascriptInterface这个注解事件才能响应
    public String event(String params) {
        String action = JSON.parseObject(params).getString("action");
        Event event = EventManager.getInstance().createEvent(action);
        if (event != null) {
            event.setAction(action);
            event.setContext(WebFragment.getContext());
            event.setWebFragment(WebFragment);
            event.setUrl(WebFragment.getUrl());
            return event.execute(params);
        }
        return null;
    }
}
