package com.milkteaking.ui.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.ui.R;

import java.util.ArrayList;
import java.util.List;

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
    // 储存一行的View控件
    private List<View> lineViews;
    // 图片属性
    private LayoutParams mParams;
    // 控制每个子只能初始化一次属性
    private boolean isOnceInitMeasure;
    private boolean isOnceInitLayout;
    // 储存行高
    private static final List<Integer> LINE_HEIGHTS = new ArrayList<>();
    // 储存所有的View控件
    private static final List<List<View>> ALL_VIEWS = new ArrayList<>();
    private IconTextView mIconTextView;
    private MilkTeaFragment mMilkTeaFragment;
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

    public void setMilkTeaFragment(MilkTeaFragment milkTeaFragment) {
        mMilkTeaFragment = milkTeaFragment;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 初始化加号iconTextView
        initIconText();
    }

    private void initIconText() {
        mIconTextView = new IconTextView(getContext());
        mIconTextView.setText(PLUS_TEXT);
        mIconTextView.setGravity(Gravity.CENTER);
        mIconTextView.setTextColor(Color.GRAY);
        mIconTextView.setTextSize(mIconSize);
        mIconTextView.setBackgroundResource(R.drawable.border_text);
        mIconTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示拍照模式
                mMilkTeaFragment.startCameraWithCheck();
            }
        });
        // 添加到布局中
        addView(mIconTextView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取规格大小和模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 如果在使用autoPhotoLayout时使用了wrap_content布局就通过以下方式来测量
        int width = 0;
        int height = 0;
        // 行宽
        int lineWidth = 0;
        int lineHeight = 0;

        // 获取子View
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            // 让子View自己去测量大小
            // 相当于是AutoPhotoLayout的父类LinearLayoutCompat在调用measureChild这个方法让我自己测量一样的道理
            // 然后就调用方法来获取子View测量好的大小来测量最后的大小
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            // 获取测量后的子View的宽高
            MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
            int viewWidth = view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int viewHeight = view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            // 判断行宽 + 子View的宽度是否大于父容器的宽度
            if ((lineWidth + viewWidth) > (widthSize - getPaddingLeft() - getPaddingRight())) {
                // 需要换行
                // 叠加高度
                height += lineHeight;
                // 行宽与父layout的宽度对比,取最大值
                width = Math.max(viewWidth, widthSize);
                // 重置高度
                lineHeight = viewHeight;
                // 重置宽度
                lineWidth = viewWidth;
            } else {
                // 一直叠加宽度,直到大于父容器宽度
                lineWidth += viewWidth;
                // 得到最大的高度
                lineHeight = Math.max(viewHeight, heightSize);
            }

            // 如果是最后一个,就
            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        // 一定要调用setMeasuredDimension来存储测量得到的宽度和高度值，
        // 如果没有这么去做会触发异常IllegalStateException
        setMeasuredDimension(
                // 如果父控件声明为绝对值,就直接使用它指定的值
                widthMode == MeasureSpec.EXACTLY ? widthSize : width + getPaddingLeft() + getPaddingRight(),
                heightMode == MeasureSpec.EXACTLY ? heightSize : height + getPaddingTop() + getPaddingBottom()
        );
        // 设置一行中每个图片的宽度
        int imageSideLen = widthSize / mMaxLineCount;
        // 只能初始化一次,否则就会出现内存泄漏
        if (!isOnceInitMeasure) {
            mParams = new LayoutParams(imageSideLen, imageSideLen);
            isOnceInitMeasure = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // 清理集合数据
        ALL_VIEWS.clear();
        LINE_HEIGHTS.clear();

        if (!isOnceInitLayout) {
            lineViews = new ArrayList<>();
            isOnceInitLayout = true;
        }
        // 记录行宽高
        int lineWidth = 0;
        int lineHeight = 0;
        // 获取当前宽度
        int width = getWidth();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            // 得到测量后的子view的layoutParams
            MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
            // 获取子view占据的宽/高
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();

            // 判断是否需要换行
            if ((measuredWidth + lineWidth + layoutParams.leftMargin + layoutParams.rightMargin) > (width +
                    getPaddingLeft() + getPaddingRight())) {
                // 叠加高度
                lineHeight += measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin;
                // 重置宽度
                lineWidth = 0;
                // 添加进所有View的集合中
                ALL_VIEWS.add(lineViews);
                // 记录当前一行的View
                LINE_HEIGHTS.add(lineHeight);
                // 重置view集合
                lineViews.clear();
            }
            // 获取行宽度
            lineWidth += measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin;
            // 获取行高度
            lineHeight = Math.max(lineHeight, lineHeight + layoutParams.topMargin + layoutParams.bottomMargin);
            lineViews.add(view);
        }
        // 处理最后一行
        // 不管是否是最后一个都添加进集合中
        LINE_HEIGHTS.add(lineHeight);
        ALL_VIEWS.add(lineViews);

        // 获取第一个View的位置
        int left = getPaddingLeft();
        int top = getPaddingTop();
        // 处理ALL_VIEWS集合中的每个View的位置摆放
        int size = ALL_VIEWS.size();
        for (int i = 0; i < size; i++) {
            // 取出一行的集合
            lineViews = ALL_VIEWS.get(i);
            // 取出高度集合中对应索引的值
            lineHeight = LINE_HEIGHTS.get(i);
            // 遍历当前索引对应的行的集合
            int lineSize = lineViews.size();
            for (int m = 0; m < lineSize; m++) {
                // 取出行里的View
                View view = lineViews.get(m);
                // 判断View的状态
                if (view.getVisibility() == View.GONE) {
                    // 如果它是隐藏的就跳过当前循环
                    continue;
                }
                // 获取子View的参数
                MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
                // 设置子View的边距
                int lc = left + layoutParams.leftMargin;
                int tc = top + layoutParams.topMargin;
                int rc = view.getMeasuredWidth() + mItemMargin;
                int bc = lc + view.getMeasuredHeight();
                // 为子view进行位置摆放
                view.layout(lc, tc, rc, bc);
                // 当计算完了child.layout后,把最后的宽度和边缘作为下一个view的left的开始
                left = view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
            // 把当前行的layout的getPaddingLeft赋给left
            // 叠加宽度
            left = getPaddingLeft();
            // 叠加高度
            top += lineHeight;
        }
        // 根据onMeasure里测量后的图片大小来设置IconTextView的大小
        mIconTextView.setLayoutParams(mParams);
        // 需要放开,当一行的View都计算了位置后,再重新创建另一个集合来存储下一行的View
        isOnceInitLayout = false;
    }
}