package com.milkteaking.ui.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.joanzapata.iconify.widget.IconTextView;
import com.milkteaking.ui.R;

import java.util.ArrayList;

/**
 * @author TanJJ
 * @time 2018/6/1 8:56
 * @des 评论的星星
 */

public class StarLayout extends LinearLayoutCompat implements View.OnClickListener {
    // 空心星
    private static final CharSequence ICON_UN_SELECT = "{fa-star-o}";
    // 实心星
    private static final CharSequence ICON_SELECT = "{fa-star}";
    // 总星星数
    private static final int STAR_TOTAL_COUNT = 5;
    // 用来储存星星的集合
    private static final ArrayList<IconTextView> STARS = new ArrayList<>();


    public StarLayout(Context context) {
        this(context, null);
    }

    public StarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化星星
        initStar();
    }

    private void initStar() {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            IconTextView iconTextView = new IconTextView(getContext());
            iconTextView.setGravity(Gravity.CENTER);
            iconTextView.setText(ICON_UN_SELECT);
            // 设置属性
            LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams
                    .MATCH_PARENT);
            layoutParams.weight = 1;
            iconTextView.setLayoutParams(layoutParams);
            // 设置tag
            iconTextView.setTag(R.id.star_count, i);
            iconTextView.setTag(R.id.star_is_select, false);
            // 设置点击事件
            iconTextView.setOnClickListener(this);
            // 添加星星到集合中
            STARS.add(iconTextView);
            // 添加到容器里
            addView(iconTextView);
        }
    }

    @Override
    public void onClick(View v) {
        IconTextView iconTextView = (IconTextView) v;
        boolean isSelect = (boolean) iconTextView.getTag(R.id.star_is_select);
        int count = (int) iconTextView.getTag(R.id.star_count);
        if (isSelect) {
            unSelectStar(count);
        } else {
            selectStar(count);
        }
    }

    private void selectStar(int count) {
        for (int i = 0; i <= count; i++) {
            boolean isSelect = (boolean) STARS.get(i).getTag(R.id.star_is_select);
            if (!isSelect) {
                IconTextView iconTextView = STARS.get(i);
                iconTextView.setText(ICON_SELECT);
                iconTextView.setTextColor(Color.RED);
                iconTextView.setTag(R.id.star_is_select, true);
            }
        }
    }

    private void unSelectStar(int count) {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            if (i > count) {
                boolean isSelect = (boolean) STARS.get(i).getTag(R.id.star_is_select);
                if (isSelect) {
                    IconTextView iconTextView = STARS.get(i);
                    iconTextView.setText(ICON_UN_SELECT);
                    iconTextView.setTextColor(Color.GRAY);
                    iconTextView.setTag(R.id.star_is_select, false);
                }
            }
        }
    }

    public int getSelectedStarCount() {
        int count = 0;
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            boolean isSelect = (boolean) STARS.get(i).getTag(R.id.star_is_select);
            if (isSelect) {
                count++;
            }
        }
        return count;
    }
}
