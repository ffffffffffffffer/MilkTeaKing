package com.milkteaking.core.util.callback;

/**
 * @author TanJJ
 * @time 2018/5/28 14:16
 * @des 全局回调接口
 */

public interface IGlobalCallback<T> {

    void executeCallback(T args);
}
