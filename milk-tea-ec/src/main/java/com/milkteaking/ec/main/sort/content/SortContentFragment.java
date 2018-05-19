package com.milkteaking.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.util.log.MilkTeaLogger;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.constant.Constant;
import com.milkteaking.ec.main.sort.content.bean.SectionBean;

import java.util.LinkedList;

import butterknife.BindView;

/**
 * @author TanJJ
 * @time 2018/5/18 14:50
 * @des 右边内容Fragment
 */

public class SortContentFragment extends MilkTeaFragment {

    @BindView(R2.id.rv_list_content)
    RecyclerView mRecyclerView;

    private static final String ARG_CONTENT_ID = "arg_content_id";
    private int mSelectId = -1;

    public static SortContentFragment create(int selectId) {
        Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, selectId);
        SortContentFragment sortContentFragment = new SortContentFragment();
        sortContentFragment.setArguments(args);
        return sortContentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取出传入的bundle
        Bundle arguments = getArguments();
        if (arguments != null) {
            mSelectId = arguments.getInt(ARG_CONTENT_ID);
        }

    }

    @Override
    public Object getLayout() {
        return R.layout.fragment_sort_content;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRecycler();
        // 请求当前currentId对应的数据
        RestClient.builder()
                .url(Constant.SORT_CONTENT_LIST.concat("?contentId=").concat(String.valueOf(mSelectId)))
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LinkedList<SectionBean> convert = new SortContentDataConvert().convert(response);
                        SortContentRecyclerAdapter adapter = new SortContentRecyclerAdapter(R.layout
                                .item_section_content, R.layout.section_header, convert);
                        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                ToastUtils.showShort("点击了 " + position);
                            }
                        });
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .failed(new IFailed() {
                    @Override
                    public void onFailed(Throwable t) {
                        MilkTeaLogger.e(SortContentFragment.class.getSimpleName(), t.getMessage());
                    }
                })
                .build()
                .get();
    }

    private void initRecycler() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager
                .VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
