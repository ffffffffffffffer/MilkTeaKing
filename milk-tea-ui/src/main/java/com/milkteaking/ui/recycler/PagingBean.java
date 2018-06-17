package com.milkteaking.ui.recycler;

/**
 * @author TanJJ
 * @time 2018/5/10 16:16
 * @des 分页bean
 */

/*
    学习以下思想
      Web端也是这样做的
        -- 总共多少条数据
        -- 一页显示多少条
        -- 当前已经显示了几条数据
        -- 当前是第几页
        -- 加载延迟
 */
public class PagingBean {
    // 总共多少数据
    private int mTotal;
    // 一页显示几条数据
    private int mPageCount;
    // 当前已经显示了几条数据
    private int mCurrentCount;
    // 当前是第几页
    private int mCurrentPageIndex;
    // 加载延迟
    private int mLoadDelay;

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        mTotal = total;
    }

    public int getPageCount() {
        return mPageCount;
    }

    public void setPageCount(int pageCount) {
        mPageCount = pageCount;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public void setCurrentCount(int currentCount) {
        mCurrentCount = currentCount;
    }

    public int getLoadDelay() {
        return mLoadDelay;
    }

    public void setLoadDelay(int loadDelay) {
        mLoadDelay = loadDelay;
    }

    public int getCurrentPageIndex() {
        return mCurrentPageIndex;
    }

    /**
     *  用于加载更多时调用了
     */
    public void addIndex() {
        mCurrentPageIndex++;
    }
}
