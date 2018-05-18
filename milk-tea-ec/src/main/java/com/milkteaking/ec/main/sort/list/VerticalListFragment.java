package com.milkteaking.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.util.log.MilkTeaLogger;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.constant.Constant;
import com.milkteaking.ec.main.sort.SortFragment;
import com.milkteaking.ui.recycler.MultipleItemBean;

import java.util.LinkedList;

import butterknife.BindView;

/**
 * @author TanJJ
 * @time 2018/5/18 14:49
 * @des 垂直列表Fragment
 */

public class VerticalListFragment extends MilkTeaFragment {
    @BindView(R2.id.rv_menu_list)
    RecyclerView mRecyclerView;


    @Override
    public Object getLayout() {
        return R.layout.fragment_vertical_list;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRecycler();
        initData();
    }

    private void initData() {
        RestClient.builder()
                .url(Constant.SORT_MENU_LIST)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LinkedList<MultipleItemBean> convert = new VerticalListDataConvert().setJson(response)
                                .convert();
                        // 获取父类,以多态的形式用SortFragment展示
                        SortFragment sortFragment = getParentFragment(0);
                        VerticalRecyclerAdapter adapter = new VerticalRecyclerAdapter(convert, sortFragment);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .failed(new IFailed() {
                    @Override
                    public void onFailed(Throwable t) {
                        MilkTeaLogger.e("VerticalListFragment", t.getMessage());
                    }
                })
                .build()
                .get();
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);

        // 屏蔽动画
        mRecyclerView.setItemAnimator(null);
    }
}
