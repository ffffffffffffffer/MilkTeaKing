package com.milkteaking.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.launcher.LauncherScrollTag;
import com.milkteaking.core.util.storage.Preference;
import com.milkteaking.core.util.timer.BaseTimerTask;
import com.milkteaking.core.util.timer.ITimerListener;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.sign.AccountManager;
import com.milkteaking.ec.sign.IUserCheck;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author TanJJ
 * @time 2018/5/8 9:17
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.ec.launcher
 * @des 启动界面
 */

public class LauncherFragment extends MilkTeaFragment implements ITimerListener {


    private ILauncherListener mLauncherListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mLauncherListener = (ILauncherListener) activity;
        }
    }

    // 倒计时的时间
    private int count = 5;

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvLauncherTimer;
    private Timer mTimer;

    @OnClick(R2.id.tv_launcher_timer)
    public void clickTvLauncherTimer() {
        // 点击进入主界面
        checkIsShowScroll();
    }

    private void checkIsShowScroll() {
        boolean flag = Preference.getAppFlag(LauncherScrollTag.HAS_FIRST_LAUNCHER_APP.name());
        if (!flag) {
            // 启动滚动界面
            startWithPop(new LauncherScrollFragment());
        } else {
            // 检查用户是否登录过
            ToastUtils.showShort("检查是否已经登录过");
            AccountManager.isCheck(new IUserCheck() {
                @Override
                public void onSignIn() {
                    if (mLauncherListener != null) {
                        mLauncherListener.onLauncherFinish(LauncherTag.SIGN);
                    }
                }

                @Override
                public void onNoSignIn() {
                    if (mLauncherListener != null) {
                        mLauncherListener.onLauncherFinish(LauncherTag.NO_SIGH);
                    }
                }
            });

        }
    }

    @Override
    public Object getLayout() {
        return R.layout.fragment_launcher;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        initTimer();
    }

    private void initTimer() {
        BaseTimerTask baseTimer = new BaseTimerTask(this);
        mTimer = new Timer();
        // 一秒执行一次
        mTimer.schedule(baseTimer, 0, 1000);
    }


    @Override
    public void onTime() {
        // 主线程中更新UI
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvLauncherTimer != null) {
                    mTvLauncherTimer.setText(MessageFormat.format("跳过\n{0}s", count));
                    count--;
                    if (count < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                        }
                        checkIsShowScroll();
                    }
                }
            }
        });
    }
}
