package com.milkteaking.core.net.callback;

/**
 * @author TanJJ
 * @time 2018/5/5 10:10
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net.callback
 * @des 失败回调
 */

public interface IFailed {
    void onFailed(Throwable t);
}
