package com.milkteaking.ui.refresh;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import com.milkteaking.core.app.ConfigType;
import com.milkteaking.core.app.MilkTea;

/**
 * @author TanJJ
 * @time 2018/5/9 21:59
 * @des SwipeRefreshLayout的刷新处理器
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {
    private final SwipeRefreshLayout mSwipeRefreshLayout;

    public RefreshHandler(SwipeRefreshLayout swipeRefreshLayout) {
        mSwipeRefreshLayout = swipeRefreshLayout;
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        // 显示刷新
        mSwipeRefreshLayout.setRefreshing(true);
        Handler handler = MilkTea.getConfigurate(ConfigType.HANDLER);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 隐藏刷新
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);

    }
}
