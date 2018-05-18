package com.milkteaking.ec.main.index;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.milkteaking.ec.R;
import com.milkteaking.ui.recycler.RgbValue;

/**
 * @author TanJJ
 * @time 2018/5/18 10:22
 * @des 用来监听RecyclerView的事件, ToolBar来接收回调监听(渐变ToolBar)
 */

public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    // 定义变量计算移动距离
    // 顶部距离
    private int mDistanceY;
    // 移动变化速度
    private final int SHOW_SPEED = 3;
    // 变化颜色
    private RgbValue mRgbValue = RgbValue.create(255, 144, 144);


    // 在layout中使用所有定义两个参数的
    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        // 当依赖的是RecyclerView时才去监听事件
        return dependency.getId() == R.id.index_rv;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild,
                                       View target, int nestedScrollAxes) {
        // 处理事件
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View target, int dx, int dy,
                                  int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        // dy:表示RecyclerView的顶部与ToolBar或者CoordinationLayout的顶部距离
        // 一直在移动的是RecyclerView的顶部

        // 记录顶部距离
        mDistanceY += dy;

        // 如果顶部距离不大于ToolBar的高度就按照移动的距离来计算出透明值,对ToolBar进行颜色设置
        // toolBar的bottom等于RecyclerView的顶部
        int bottom = child.getBottom();
        if (mDistanceY > 0 && mDistanceY <= bottom) {
            // 获取透明度缩放比例
            // 注意:  滑动距离 / 高度 后一定要强转为float,不然看不到慢慢变化的效果
            float scale = (float) mDistanceY / bottom;
            // 根据scale计算出透明变化
            float alpha = scale * 255;
            // 重新设置ToolBar的颜色
            child.setBackgroundColor(
                    Color.argb(
                            (int) alpha,
                            mRgbValue.red(),
                            mRgbValue.green(),
                            mRgbValue.blue()
                    )
            );
        } else if (mDistanceY > bottom) {
            //设置背景为指定颜色
            child.setBackgroundColor(
                    Color.rgb(
                            mRgbValue.red(),
                            mRgbValue.green(),
                            mRgbValue.blue()
                    )
            );
        }

    }
}
