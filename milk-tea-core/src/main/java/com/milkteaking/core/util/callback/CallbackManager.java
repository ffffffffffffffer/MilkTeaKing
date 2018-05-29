package com.milkteaking.core.util.callback;

import android.util.SparseArray;

/**
 * @author TanJJ
 * @time 2018/5/28 14:13
 * @des 全局唯一个回调管理, 可以通过这个管理器来实现多线程间通信/类与类的通信
 */

public class CallbackManager {

    private static final SparseArray<IGlobalCallback> CALLBACKS = new SparseArray<>();


    private static class Holder {
        private static final CallbackManager INSTANCE = new CallbackManager();
    }

    private CallbackManager() {
    }

    public static CallbackManager getInstance() {
        return Holder.INSTANCE;
    }


    public void addCallBack(int key, IGlobalCallback value) {
        CALLBACKS.put(key, value);
    }

    public IGlobalCallback getCallback(int key) {
        return CALLBACKS.get(key);
    }

}
