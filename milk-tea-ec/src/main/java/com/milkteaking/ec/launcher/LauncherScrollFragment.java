package com.milkteaking.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.milkteaking.core.app.MilkTea;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.launcher.LauncherScrollHolderCreator;
import com.milkteaking.core.launcher.LauncherScrollTag;
import com.milkteaking.core.util.storage.Preference;
import com.milkteaking.ec.R;
import com.milkteaking.ec.sign.AccountManager;
import com.milkteaking.ec.sign.IUserCheck;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TanJJ
 * @time 2018/5/8 12:28
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.ec.launcher
 * @des 启动滚动界面
 */

public class LauncherScrollFragment extends MilkTeaFragment implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner;
    private List<Integer> mList = new ArrayList<>();
    private ILauncherListener mLauncherListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mLauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object getLayout() {
        mConvenientBanner = new ConvenientBanner<Integer>(MilkTea.getApplicationContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        initBanner();
        mConvenientBanner
                .setPages(new LauncherScrollHolderCreator(), mList)
                .setPageIndicator(new int[]{R.drawable.launcher_dot_normal, R.drawable.launcher_dot_fouce})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    private void initBanner() {
        mList.add(R.drawable.launcher_01);
        mList.add(R.drawable.launcher_02);
        mList.add(R.drawable.launcher_03);
        mList.add(R.drawable.launcher_04);
        mList.add(R.drawable.launcher_05);
    }

    @Override
    public void onItemClick(int position) {
        if (position == mList.size() - 1) {
            Preference.setAppFlag(LauncherScrollTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            // 检查用户是否已经登录过,登录过就直接进入主页,否则登录界面
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
}
