package com.milkteaking.ui.banners;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * @author TanJJ
 * @time 2018/5/10 12:28
 * @des 图片holder构建器
 */

public class ImageHolderCreator implements CBViewHolderCreator<ImageHolder> {

    @Override
    public ImageHolder createHolder() {
        return new ImageHolder();
    }
}
