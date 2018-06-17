package com.milkteaking.ec.main.index.search;

import com.milkteaking.ec.R;
import com.milkteaking.ui.recycler.MultipleField;
import com.milkteaking.ui.recycler.MultipleItemBean;
import com.milkteaking.ui.recycler.MultipleRecyclerAdapter;
import com.milkteaking.ui.recycler.MultipleRecyclerViewHolder;

import java.util.List;

/**
 * @author TanJJ
 * @time 2018/6/17 14:01
 * @des 搜索适配器
 */

public class SearchAdapter extends MultipleRecyclerAdapter {
    public SearchAdapter(List<MultipleItemBean> data) {
        super(data);
        addItemType(SearchItemType.search_type, R.layout.item_search);
    }

    @Override
    protected void convert(MultipleRecyclerViewHolder helper, MultipleItemBean item) {
        switch (item.getItemType()) {
            case SearchItemType.search_type:
                String historyText = item.getFiled(MultipleField.TEXT.name());
                helper.setText(R.id.tv_search_item, historyText);
                break;
            default:
                break;
        }
    }
}
