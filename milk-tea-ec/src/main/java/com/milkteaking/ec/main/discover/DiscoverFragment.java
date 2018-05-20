package com.milkteaking.ec.main.discover;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.milkteaking.core.app.ConfigType;
import com.milkteaking.core.app.MilkTea;
import com.milkteaking.core.fragments.bottom.BottomItemFragment;
import com.milkteaking.core.fragments.web.IPageLoadListener;
import com.milkteaking.core.fragments.web.WebFragmentImpl;
import com.milkteaking.core.ui.loader.MilkTeaLoader;
import com.milkteaking.ec.R;
import com.milkteaking.ec.constant.Constant;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author TanJJ
 * @time 2018/5/19 19:01
 * @des 发现Fragment
 */

public class DiscoverFragment extends BottomItemFragment implements IPageLoadListener {

    private Handler mHandler = MilkTea.getConfigurate(ConfigType.HANDLER);

    @Override
    public Object getLayout() {
        return R.layout.fragment_discover;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 加载WebFragmentImpl
        WebFragmentImpl webFragment = WebFragmentImpl.create(Constant.DISCOVERY_LOCAL);
        webFragment.setTopFragment(this.getParentFragment(0));
        webFragment.setPageLoadListener(this);
        loadRootFragment(R.id.web_discovery_container, webFragment);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onLoadStart() {
        // 加载进度
        MilkTeaLoader.showLoader(getContext());
    }

    @Override
    public void onLoadEnd() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //一秒后再停止
                MilkTeaLoader.stopLoader();
            }
        }, 1000);
    }
}
