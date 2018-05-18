package com.milkteaking.core.fragments;

/**
 * @author TanJJ
 * @time 2018/5/5 0:34
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.fragments
 * @des 正式使用的Fragment
 */

public abstract class MilkTeaFragment extends PermissionCheckerFragment {

    /**
     * 提供方法让子类获取父Fragment
     * @param a 多余的,只不过是希望用getParentFragment这个方法名,所以重载
     * @return 限制只能返回MilkTeaFragment自己或子类
     */
    @SuppressWarnings("unchecked")
    public <T extends MilkTeaFragment> T getParentFragment(int a) {
        return (T) getParentFragment();
    }
}
