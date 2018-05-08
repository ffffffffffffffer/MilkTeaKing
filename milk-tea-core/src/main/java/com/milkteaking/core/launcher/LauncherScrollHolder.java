package com.milkteaking.core.launcher;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * @author TanJJ
 * @time 2018/5/8 12:30
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.launcher
 */

public class LauncherScrollHolder implements Holder<Integer> {

    private AppCompatImageView mImageView;

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        mImageView.setBackgroundResource(data);
    }
}
