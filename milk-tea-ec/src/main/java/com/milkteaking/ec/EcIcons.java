package com.milkteaking.ec;

import com.joanzapata.iconify.Icon;

/**
 * @author TanJJ
 * @time 2018/5/4 14:53
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.ec
 * @des 将字体库导入(模仿FontAwesomeIcons)
 */

public enum  EcIcons implements Icon {
    icon_zhifubao('\ue608'),
    icon_saoyisao('\ue62e');

    char character;
    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
