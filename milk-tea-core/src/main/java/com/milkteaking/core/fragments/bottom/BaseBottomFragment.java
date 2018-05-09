package com.milkteaking.core.fragments.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.milkteaking.core.R;
import com.milkteaking.core.R2;
import com.milkteaking.core.app.MilkTea;
import com.milkteaking.core.fragments.MilkTeaFragment;

import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author TanJJ
 * @time 2018/5/9 13:40
 */

public abstract class BaseBottomFragment extends MilkTeaFragment implements View.OnClickListener {
    private final WeakHashMap<BottomTabBean, BottomItemFragment> ITEMS = new WeakHashMap<>();
    private final ArrayList<BottomTabBean> BOTTOM_BEEN = new ArrayList<>();
    private final ArrayList<BottomItemFragment> ITEM_FRAGMENTS = new ArrayList<>();
    private int mIndexFragment;
    private int mCurrentFragment;
    private int mClickColor = Color.parseColor("#95ff4444");

    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar;


    public abstract WeakHashMap<BottomTabBean, BottomItemFragment> getItems(ItemBuilder builder);

    public abstract int setIndexFragment();

    public abstract int setColor();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIndexFragment = setIndexFragment();

        if (setColor() != 0) {
            mClickColor = setColor();
        }

        WeakHashMap<BottomTabBean, BottomItemFragment> items = getItems(ItemBuilder.build());
        // 将获取到的map储存起来
        ITEMS.putAll(items);
        // 遍历集合
        for (Map.Entry<BottomTabBean, BottomItemFragment> entry : items.entrySet()) {
            BOTTOM_BEEN.add(entry.getKey());
            ITEM_FRAGMENTS.add(entry.getValue());
        }
    }

    @Override
    public Object getLayout() {
        return R.layout.fragment_bottom;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        // 不要破坏框架的原子性(不要对框架进行修改而是在框架的基础上修改),否则就是框架设计不好.
        int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            // 加载底部布局
            LayoutInflater.from(MilkTea.getApplicationContext()).inflate(R.layout
                    .bottom_item_icon_text_layout, mBottomBar, true);
            RelativeLayout rootView = (RelativeLayout) mBottomBar.getChildAt(i);
            // 给当前的RelativeLayout设置tag
            rootView.setTag(i);
            rootView.setOnClickListener(this);

            BottomTabBean bottomTabBean = BOTTOM_BEEN.get(i);
            IconTextView bottom_icon_tv = (IconTextView) rootView.getChildAt(0);
            AppCompatTextView bottom_tv = (AppCompatTextView) rootView.getChildAt(1);
            bottom_icon_tv.setText(bottomTabBean.getIcon());
            bottom_tv.setText(bottomTabBean.getTabText());

            if (i == mIndexFragment) {
                bottom_icon_tv.setTextColor(mClickColor);
                bottom_tv.setTextColor(mClickColor);
            }
            // 将item_delegate根据自己的长度转换成一个数组
            // 为什么是SupportFragment数组呢?因为每一个Delegate都是SupportFragment的子类
            SupportFragment[] supportFragments = ITEM_FRAGMENTS.toArray(new SupportFragment[size]);
            // 加载多个fragment到bottom_bar_delegate_container中
            loadMultipleRootFragment(R.id.bottom_bar, mIndexFragment, supportFragments);
        }
    }

    @Override
    public void onClick(View v) {
        // 将当前点击的item里的iconTextView和TextView设置成点击的颜色
        int tag = (int) v.getTag();
        // 先重置所有的item颜色,再单独为点击的那个item设置颜色
        resetColor();
        // 因为是通过RelativeLayout设置点击事件的,v本质上是RelativeLayout
        RelativeLayout relativeLayout = (RelativeLayout) v;
        IconTextView bottom_icon_tv = (IconTextView) relativeLayout.getChildAt(0);
        AppCompatTextView bottom_tv = (AppCompatTextView) relativeLayout.getChildAt(1);
        // 设置点击后的颜色
        bottom_icon_tv.setTextColor(mClickColor);
        bottom_tv.setTextColor(mClickColor);
        // 根据点击的tab改变fragment的显式
        showHideFragment(ITEM_FRAGMENTS.get(tag), ITEM_FRAGMENTS.get(mCurrentFragment));
        // 改变当前fragment的值
        mCurrentFragment = tag;
    }

    /*
        重置颜色,设置默认颜色
     */
    private void resetColor() {
        int childCount = mBottomBar.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RelativeLayout rootView = (RelativeLayout) mBottomBar.getChildAt(i);
            IconTextView bottom_icon_tv = (IconTextView) rootView.getChildAt(0);
            AppCompatTextView bottom_tv = (AppCompatTextView) rootView.getChildAt(1);
            // 设置默认颜色
            bottom_icon_tv.setTextColor(Color.GRAY);
            bottom_tv.setTextColor(Color.GRAY);
        }
    }

}
