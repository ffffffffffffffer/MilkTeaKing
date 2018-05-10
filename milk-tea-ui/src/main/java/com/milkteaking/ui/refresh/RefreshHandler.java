package com.milkteaking.ui.refresh;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.milkteaking.core.app.ConfigType;
import com.milkteaking.core.app.MilkTea;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.util.log.MilkTeaLogger;
import com.milkteaking.ui.recycler.DataCovert;
import com.milkteaking.ui.recycler.MultipleRecyclerAdapter;
import com.milkteaking.ui.recycler.PagingBean;

/**
 * @author TanJJ
 * @time 2018/5/9 21:59
 * @des SwipeRefreshLayout的刷新处理器
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private final SwipeRefreshLayout mSwipeRefreshLayout;
    private final RecyclerView mRecyclerView;
    private final DataCovert mDataCovert;
    private final PagingBean mPagingBean;

    private RefreshHandler(
            SwipeRefreshLayout swipeRefreshLayout,
            RecyclerView recyclerView,
            DataCovert dataCovert,
            PagingBean pagingBean) {
        mSwipeRefreshLayout = swipeRefreshLayout;
        mRecyclerView = recyclerView;
        mDataCovert = dataCovert;
        mPagingBean = pagingBean;
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView,
                                        DataCovert dataCovert,
                                        PagingBean pagingBean) {
        return new RefreshHandler(swipeRefreshLayout, recyclerView, dataCovert, pagingBean);
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
                        // 获取json数据的对象
                        JSONObject jsonObject = JSON.parseObject(response);
                        Integer total = jsonObject.getInteger("total");
                        Integer page_size = jsonObject.getInteger("page_size");
                        mPagingBean.setTotal(total);
                        mPagingBean.setPageCount(page_size);
                        // 加载数据
                        MultipleRecyclerAdapter adapter = MultipleRecyclerAdapter.create(mDataCovert
                                .setJson(response));
                        // 设置加载更多监听
                        adapter.setOnLoadMoreListener(RefreshHandler.this, mRecyclerView);
                        mRecyclerView.setAdapter(adapter);
                        // 当前显示页加1
                        mPagingBean.addIndex();
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

    @Override
    public void onLoadMoreRequested() {
        // 加载更多
    }
}
