package com.milkteaking.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author TanJJ
 * @time 2018/5/18 17:44
 * @des 出现了个莫名的类型转换异常,就将ViewHolder抽取出来
 */

public class MultipleRecyclerViewHolder extends BaseViewHolder {
    public MultipleRecyclerViewHolder(View view) {
        super(view);
    }
}
