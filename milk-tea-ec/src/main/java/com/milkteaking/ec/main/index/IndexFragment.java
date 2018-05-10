package com.milkteaking.ec.main.index;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.milkteaking.core.fragments.bottom.BottomItemFragment;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.constant.Constant;
import com.milkteaking.ui.recycler.PagingBean;
import com.milkteaking.ui.refresh.RefreshHandler;

import butterknife.BindView;

/**
 * @author TanJJ
 * @time 2018/5/9 17:15
 * @des 首页Fragment
 */

public class IndexFragment extends BottomItemFragment {

    @BindView(R2.id.index_srl)
    SwipeRefreshLayout mSrlIndex;

    @BindView(R2.id.index_rv)
    RecyclerView mRvIndex;

    @BindView(R2.id.index_toolbar)
    Toolbar mToolbarIndex;

    @BindView(R2.id.index_itv_sao_yi_sao)
    IconTextView mItvSaoYiSaoIndex;

    @BindView(R2.id.index_et_search)
    AppCompatEditText mEtSearchIndex;

    @BindView(R2.id.index_itv_bull_horn)
    IconTextView mItvBullHornIndex;
    private RefreshHandler mRefreshHandler;

    @Override
    public Object getLayout() {
        return R.layout.fragment_index;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        mRefreshHandler = RefreshHandler.create(mSrlIndex, mRvIndex, new IndexDataConvert(), new PagingBean());
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefresh();
        initRecycler();
        mRefreshHandler.firstPage(Constant.INDEX);
    }

    private void initRecycler() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRvIndex.setLayoutManager(manager);
    }

    private void initRefresh() {
        mSrlIndex.setColorSchemeColors(
                Color.RED,
                Color.GREEN,
                Color.BLUE
        );
        mSrlIndex.setProgressViewOffset(true, 120, 200);
    }
}
