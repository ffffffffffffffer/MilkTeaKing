package com.milkteaking.ec.main.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.milkteaking.core.fragments.bottom.BottomItemFragment;
import com.milkteaking.core.fragments.web.WebFragmentImpl;
import com.milkteaking.ec.R;
import com.milkteaking.ec.constant.Constant;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author TanJJ
 * @time 2018/5/19 19:01
 * @des 发现Fragment
 */

public class DiscoverFragment extends BottomItemFragment {
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
        loadRootFragment(R.id.web_discovery_container, webFragment);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
