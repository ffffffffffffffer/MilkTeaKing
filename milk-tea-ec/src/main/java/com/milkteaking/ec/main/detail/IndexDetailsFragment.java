package com.milkteaking.ec.main.detail;

import android.os.Bundle;
import android.view.View;

import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.ec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author TanJJ
 * @time 2018/5/18 12:16
 * @des 商品详情Fragment
 */

public class IndexDetailsFragment extends MilkTeaFragment {

    public static IndexDetailsFragment create() {
        return new IndexDetailsFragment();
    }

    @Override
    public Object getLayout() {
        return R.layout.fragment_index_details;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
