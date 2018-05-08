package com.milkteaking.ec.launcher;

import android.os.Bundle;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.milkteaking.core.app.MilkTea;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.launcher.LauncherScrollHolderCreator;
import com.milkteaking.ec.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TanJJ
 * @time 2018/5/8 12:28
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.ec.launcher
 * @des 启动滚动界面
 */

public class LauncherScrollFragment extends MilkTeaFragment implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner;
    private List<Integer> mList = new ArrayList<>();

    @Override
    public Object getLayout() {
        mConvenientBanner = new ConvenientBanner<Integer>(MilkTea.getApplicationContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        initBanner();
        mConvenientBanner
                .setPages(new LauncherScrollHolderCreator(), mList)
                .setPageIndicator(new int[]{R.drawable.launcher_dot_normal, R.drawable.launcher_dot_fouce})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    private void initBanner() {
        mList.add(R.drawable.launcher_01);
        mList.add(R.drawable.launcher_02);
        mList.add(R.drawable.launcher_03);
        mList.add(R.drawable.launcher_04);
        mList.add(R.drawable.launcher_05);
    }

    @Override
    public void onItemClick(int position) {

    }
}
