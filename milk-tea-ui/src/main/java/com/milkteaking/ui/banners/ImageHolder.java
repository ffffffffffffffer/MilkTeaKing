package com.milkteaking.ui.banners;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.milkteaking.core.ui.image.GlideApp;

/**
 * @author TanJJ
 * @time 2018/5/10 14:19
 */

public class ImageHolder implements Holder<String> {

    private AppCompatImageView mImageView;

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        // glide 加载图片
        GlideApp.with(context)
                .load(data)
                .apply(com.milkteaking.core.glide.GlideOptions.OPTIONS)
                .into(mImageView);
    }
}
