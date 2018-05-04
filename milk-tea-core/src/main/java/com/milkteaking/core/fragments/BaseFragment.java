package com.milkteaking.core.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * @author TanJJ
 * @time 2018/5/5 0:25
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.fragments
 * @des 基类Fragment,提供方法让子类实现
 */

public abstract class BaseFragment extends SwipeBackFragment{
    private Unbinder mBind;

    /**
     * 让实现的子类提供要加载的布局View或布局layout的id
     *
     * @return 要加载的布局View或布局layout的id
     */
    public abstract Object getLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = null;
        if (getLayout() instanceof Integer) {
            // 加载布局
            rootView = inflater.inflate((Integer) getLayout(), container, false);
        } else if (getLayout() instanceof View) {
            rootView = (View) getLayout();
        } else if (getLayout() == null) {
            // 没有设置就报错提醒
            throw new RuntimeException(this.getClass().getName() + " getLayout() is null,please check it!");
        }
        // 绑定ButterKnife
        if (rootView != null) {
            mBind = ButterKnife.bind(this, rootView);
        }
        // 需要支持SwipeBack则这里必须调用toSwipeBackFragment(view);
        return attachToSwipeBack(rootView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind!=null){
            mBind.unbind();
        }
    }
}
