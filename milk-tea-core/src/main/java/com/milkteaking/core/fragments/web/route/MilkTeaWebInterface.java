package com.milkteaking.core.fragments.web.route;

import com.alibaba.fastjson.JSON;
import com.milkteaking.core.fragments.web.WebFragment;

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

    public static MilkTeaWebInterface craete(WebFragment webFragment) {
        return new MilkTeaWebInterface(webFragment);
    }

    public String event(String params) {
        String action = JSON.parseObject(params).getString("action");
        return null;
    }
}
