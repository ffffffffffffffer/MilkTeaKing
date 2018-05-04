package com.milkteaking.ec;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * @author TanJJ
 * @time 2018/5/4 22:51
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.ec
 * @des 模仿FontAwesomeModule
 */

public class FontEcModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return EcIcons.values();
    }
}
