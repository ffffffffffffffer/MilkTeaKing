package com.milkteaking.ui.banners;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.milkteaking.ui.R;
import java.util.ArrayList;

/**
 * @author TanJJ
 * @time 2018/5/10 14:23
 */

public class BannersCreator {

    public static void setDefault(ConvenientBanner banner, ArrayList<String> data, OnItemClickListener listener) {
        banner.setPages(new ImageHolderCreator(), data)
                .setPageIndicator(new int[]{R.drawable.banner_dot_normal, R.drawable.banner_dot_fouce})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(listener)
                .startTurning(3000)// 3秒切换图片
                .setCanLoop(true);
    }
}
