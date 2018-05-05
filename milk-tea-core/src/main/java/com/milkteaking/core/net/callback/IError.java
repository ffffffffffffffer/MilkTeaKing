package com.milkteaking.core.net.callback;

/**
 * @author TanJJ
 * @time 2018/5/5 10:11
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net.callback
 * @des 错误回调
 */

public interface IError {
    void onError(int code,String msg);
}
