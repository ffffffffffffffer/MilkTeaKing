package com.milkteaking.core.util.timer;

import java.util.TimerTask;

/**
 * @author TanJJ
 * @time 2018/5/8 9:40
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.util.timer
 * @des 倒计时类
 */

public class BaseTimerTask extends TimerTask {
    private final ITimerListener mListener;

    public BaseTimerTask(ITimerListener listener) {
        mListener = listener;
    }

    @Override
    public void run() {
        mListener.onTime();
    }
}
