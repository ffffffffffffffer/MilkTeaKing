package com.milkteaking.core.net.callback;

/**
 * @author TanJJ
 * @time 2018/5/5 10:13
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net.callback
 * @des 请求前后回调
 */

public interface IRequest {
    void onRequestStart();

    void onRequestEnd();
}
