package com.milkteaking.core.ui.scanner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * @author TanJJ
 * @time 2018/6/3 10:12
 */

public class FindView extends ViewFinderView {
    public FindView(Context context) {
        this(context, null);
    }

    public FindView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mSquareViewFinder = true;
        // 设置边框颜色
        mBorderPaint.setColor(Color.YELLOW);
        mLaserPaint.setColor(Color.GREEN);
    }
}
