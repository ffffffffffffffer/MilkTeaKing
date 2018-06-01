package com.milkteaking.ui.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;

import com.milkteaking.ui.R;

/**
 * @author TanJJ
 * @time 2018/6/1 10:26
 * @des 图片自动缩进/后退/点击能拍照和选择本地图片(仿微信自动多图展示)
 */

public class AutoPhotoLayout extends LinearLayoutCompat {

    // 最大图片数量
    private int mMaxCount;
    // 一行最多图片数量
    private int mMaxLineCount;
    // 每个Item的之间的margin
    private int mItemMargin;
    // +号IconTextView的大小
    private float mIconSize;
    // +号图标
    private static final CharSequence PLUS_TEXT = "{fa-plus}";

    public AutoPhotoLayout(Context context) {
        this(context, null);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.camera_flow_layout);
        mMaxCount = typedArray.getInteger(R.styleable.camera_flow_layout_max_count, 3);
        mMaxLineCount = typedArray.getInteger(R.styleable.camera_flow_layout_max_line_count, 2);
        mItemMargin = typedArray.getInteger(R.styleable.camera_flow_layout_item_margin, 0);
        mIconSize = typedArray.getDimension(R.styleable.camera_flow_layout_icon_size, 20);
        typedArray.recycle();
    }
}
