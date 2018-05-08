package com.example.milkteaking;

import android.os.Bundle;
import android.view.View;

import com.milkteaking.core.fragments.MilkTeaFragment;

/**
 * @author TanJJ
 * @time 2018/5/5 1:08
 * @ProjectName MilkTeaKing
 * @PackageName com.example.milkteaking
 * @des 根Fragment
 */

public class RootFragment extends MilkTeaFragment {
    @Override
    public Object getLayout() {
        return R.layout.fragment_root;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {

    }
}
