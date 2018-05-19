package com.milkteaking.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.milkteaking.core.fragments.bottom.BottomItemFragment;
import com.milkteaking.ec.R;
import com.milkteaking.ec.main.sort.list.VerticalListFragment;
import com.milkteaking.ec.main.sort.content.SortContentFragment;

/**
 * @author TanJJ
 * @time 2018/5/9 17:32
 * @des 分类Fragment
 */

public class SortFragment extends BottomItemFragment {
    @Override
    public Object getLayout() {
        return R.layout.fragment_sort;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        // 懒加载,只希望在点击分类的时候才去加载
        VerticalListFragment verticalListFragment = new VerticalListFragment();
        // 添加到delegate_sort布局中的左边的ContentFrameLayout
        loadRootFragment(R.id.vertical_list_container, verticalListFragment);
        // 当点击左边列表的数据时切换右边的Fragment
        // 设置右边Fragment默认显示分类一
        SortContentFragment sortContentFragment = SortContentFragment.create(1);
        loadRootFragment(R.id.sort_content_container, sortContentFragment);

    }
}
