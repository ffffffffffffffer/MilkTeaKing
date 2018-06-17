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
import com.milkteaking.ui.recycler.MultipleItemBean;
import com.milkteaking.ui.recycler.MultipleRecyclerAdapter;
import com.milkteaking.ui.recycler.PagingBean;

import java.util.LinkedList;

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
    private MultipleRecyclerAdapter mAdapter;

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
                        mAdapter = MultipleRecyclerAdapter.create(mDataCovert
                                .setJson(response));
                        // 设置加载更多监听
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, mRecyclerView);
                        mRecyclerView.setAdapter(mAdapter);
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
        page("http://192.168.43.156/latte_index.json?index=");
    }

    private void page(final String path) {
        // 获取页面的信息
        int currentCount = mPagingBean.getCurrentCount();
        int pageCount = mPagingBean.getPageCount();
        int total = mPagingBean.getTotal();
        final int currentPageIndex = mPagingBean.getCurrentPageIndex();

        // 判断是否需要加载页面
        if (mAdapter.getData().size() > pageCount || currentCount >= total) {
            mAdapter.loadMoreEnd(true);
        } else {
            // 加载新的一页
            MilkTea.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestClient.builder()
                            .url(path + currentPageIndex)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    // 数据转换
                                    LinkedList<MultipleItemBean> convert = mDataCovert.setJson(response).convert();
                                    // 填充数据
                                    mAdapter.setNewData(convert);
                                    // 累加数量
                                    mPagingBean.setCurrentCount(mAdapter.getData().size());
                                    // 更新完毕
                                    mAdapter.loadMoreComplete();
                                    // 更新数据
                                    mPagingBean.addIndex();
                                }
                            })
                            .failed(new IFailed() {
                                @Override
                                public void onFailed(Throwable t) {
                                    MilkTeaLogger.e("Failed", "加载更多失败");
                                }
                            })
                            .build()
                            .get();
                }
            }, 1000);
        }


    }
}
