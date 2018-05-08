package com.milkteaking.core.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * @author TanJJ
 * @time 2018/5/8 12:33
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.launcher
 */

public class LauncherScrollHolderCreator implements CBViewHolderCreator<LauncherScrollHolder> {
    @Override
    public LauncherScrollHolder createHolder() {
        return new LauncherScrollHolder();
    }
}
