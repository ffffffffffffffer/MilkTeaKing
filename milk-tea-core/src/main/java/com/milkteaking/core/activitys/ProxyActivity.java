package com.milkteaking.core.activitys;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;

import com.milkteaking.core.R;
import com.milkteaking.core.fragments.MilkTeaFragment;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author TanJJ
 * @time 2018/5/5 0:17
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.activitys
 * @des BaseActivity,提供方法让子类实现
 */

public abstract class ProxyActivity extends SupportActivity{
    /**
     * 返回需要加载的根布局
     *
     * @return 根Fragment
     */
    public abstract MilkTeaFragment getRootFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState) {
        // 定义显示的view
        ContentFrameLayout contentFrameLayout = new ContentFrameLayout(this);
        contentFrameLayout.setId(R.id.fragment_container);
        // 设置显示的View
        setContentView(contentFrameLayout);
        if (savedInstanceState == null) {
            // 证明是第一次加载
            // 用上面设置的view来加载根Fragment
            loadRootFragment(R.id.fragment_container, getRootFragment());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 帮助系统清理垃圾
        System.gc();
        System.runFinalization();
    }
}
