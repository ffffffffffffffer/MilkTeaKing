package com.milkteaking.ui.refresh;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import com.milkteaking.core.app.ConfigType;
import com.milkteaking.core.app.MilkTea;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.util.log.MilkTeaLogger;

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

    // 获取第一页内容,用于分页加载
    public void firstPage(String url) {
        // 请求数据
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {

                    @Override
                    public void onSuccess(String response) {
                        MilkTeaLogger.e("onSuccess", response);
                    }
                })
                .failed(new IFailed() {
                    @Override
                    public void onFailed(Throwable t) {
                        MilkTeaLogger.e("onFailed", t.getMessage());
                    }
                })
                .build()
                .get();
    }
}
