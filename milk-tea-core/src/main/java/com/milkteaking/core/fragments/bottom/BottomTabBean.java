package com.milkteaking.core.fragments.bottom;

/**
 * @author TanJJ
 * @time 2018/5/9 13:26
 * @des 底部Tab的实体类
 */

public class BottomTabBean {
    private final CharSequence mIcon;
    private final CharSequence mTabText;

    public BottomTabBean(CharSequence icon, CharSequence tabText) {
        mIcon = icon;
        mTabText = tabText;
    }

    public CharSequence getIcon() {
        return mIcon;
    }

    public CharSequence getTabText() {
        return mTabText;
    }
}
