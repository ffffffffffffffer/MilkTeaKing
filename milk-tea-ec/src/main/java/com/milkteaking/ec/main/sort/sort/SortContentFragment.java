package com.milkteaking.ec.main.sort.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.ec.R;

/**
 * @author TanJJ
 * @time 2018/5/18 14:50
 * @des 右边内容Fragment
 */

public class SortContentFragment extends MilkTeaFragment {
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
}
