package com.milkteaking.ui.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * @author TanJJ
 * @time 2018/6/17 15:44
 * @des 圆形textView
 */

public class CircleTextView extends AppCompatTextView {

    private PaintFlagsDrawFilter mDrawFilter;
    private Paint mPaint;

    public CircleTextView(Context context) {
        this(context, null);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化画笔
        mPaint = new Paint();
        // 初始化画笔过滤
        // 参数1:清除的flag
        // 参数2:设置的flag
        // ANTI_ALIAS_FLAG ---抗锯齿
        // FILTER_BITMAP_FLAG---双线性
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    public void setCircleBackgroundColor(@ColorInt int color) {
        mPaint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // ------------start---------------
        // 没有加这个居然不显示自定义的圆圈
        // ------------end---------------
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int maxHeight = getMaxHeight();

        // 得出最大值
        int max = Math.max(measuredWidth, maxHeight);
        //测量出一个正方形出来
        setMeasuredDimension(max, max);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 设置画板过滤
        canvas.setDrawFilter(mDrawFilter);
        // 画圆
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.max(getWidth()/2, getHeight() / 2), mPaint);
        // ------------start---------------
        // 没有加这个居然不显示自定义的圆圈,并且要放在最后才有效
        // ------------end---------------
        super.onDraw(canvas);
    }
}
