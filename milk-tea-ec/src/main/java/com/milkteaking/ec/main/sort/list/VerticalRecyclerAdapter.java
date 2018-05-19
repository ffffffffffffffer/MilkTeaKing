package com.milkteaking.ec.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.milkteaking.ec.R;
import com.milkteaking.ec.main.sort.SortFragment;
import com.milkteaking.ec.main.sort.content.SortContentFragment;
import com.milkteaking.ui.recycler.ItemType;
import com.milkteaking.ui.recycler.MultipleField;
import com.milkteaking.ui.recycler.MultipleItemBean;
import com.milkteaking.ui.recycler.MultipleRecyclerAdapter;
import com.milkteaking.ui.recycler.MultipleRecyclerViewHolder;

import java.util.List;

/**
 * @author TanJJ
 * @time 2018/5/18 15:44
 * @des 垂直Fragment的适配器
 */

public class VerticalRecyclerAdapter extends MultipleRecyclerAdapter {
    private final SortFragment mSortFragment;
    private int mPrePosition;
    private int mCurrentId = -1;

    public VerticalRecyclerAdapter(List<MultipleItemBean> data, SortFragment sortFragment) {
        super(data);
        mSortFragment = sortFragment;
        init();
    }

    private void init() {
        addItemType(ItemType.SORT_MENU_LIST, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(final MultipleRecyclerViewHolder helper, MultipleItemBean item) {
        super.convert(helper, item);
        switch (item.getItemType()) {
            case ItemType.SORT_MENU_LIST:
                // 获取要显示的文本
                String name = item.getFiled(MultipleField.NAME.name());
                // 获取是否点击的tag
                boolean isClick = item.getFiled(MultipleField.TAG.name());
                // 获取整个layout的itemView
                View itemView = helper.itemView;
                // 获取要显示文本的View
                AppCompatTextView textView = helper.getView(R.id.tv_vertical_item_name);
                // 获取指示view
                View viewLine = helper.getView(R.id.view_line);

                //设置itemView的点击事件
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 获取当前的position
                        int currentPosition = helper.getAdapterPosition();

                        if (mPrePosition != currentPosition) {
                            // 将当前的tag设置为true
                            getData().get(currentPosition).setField(MultipleField.TAG.name(), true);

                            // 刷新RecyclerViewAdapter,让它可以重新的调用covert方法
                            notifyItemChanged(currentPosition);

                            // 将前一个置为false
                            getData().get(mPrePosition).setField(MultipleField.TAG.name(), false);
                            // 把前一个的View置为未选择状态
                            notifyItemChanged(mPrePosition);
                            mPrePosition = currentPosition;
                            mCurrentId = getData().get(currentPosition).getFiled(MultipleField.ID.name());

                            // 显示content内容
                            showContent(mCurrentId);
                        }

                    }
                });

                textView.setText(name);

                if (!isClick) {
                    if (viewLine.getVisibility() == View.VISIBLE) {
                        viewLine.setVisibility(View.INVISIBLE);
                    }
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));

                } else {
                    if (viewLine.getVisibility() == View.INVISIBLE) {
                        viewLine.setVisibility(View.VISIBLE);
                        viewLine.setBackgroundColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    }
                    viewLine.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    // 要设置Color.WHITE才能看到效果
                    itemView.setBackgroundColor(Color.WHITE);
                }
                break;
            default:
                break;
        }
    }

    private void showContent(int currentId) {
        // 获取content对象
        final SortContentFragment contentFragment = SortContentFragment.create(currentId);
        switchFragment(contentFragment);
    }

    private void switchFragment(SortContentFragment contentFragment) {
        SortContentFragment childFragment = mSortFragment.findChildFragment(SortContentFragment.class);
        // 通过从父Delegate获取到的ContentDelegate来调用替换Fragment方法来切换传入的contentDelegate
        // 就是自己以替换的方法重新加载自己,根据传入的id作为主要的刷新因素,起到刷新效果.
        // 自己加载自己,不加入回退栈
        childFragment.replaceFragment(contentFragment, false);
    }
}
