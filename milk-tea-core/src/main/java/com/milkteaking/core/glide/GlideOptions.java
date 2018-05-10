package com.milkteaking.core.glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author TanJJ
 * @time 2018/5/10 14:18
 */

public class GlideOptions {
    public static final RequestOptions OPTIONS = new RequestOptions()
            .centerCrop()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL);
}
