package com.milkteaking.ui.recycler;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

/**
 * @author TanJJ
 * @time 2018/5/10 19:30
 */

public class DividerLookupImpl implements DividerItemDecoration.DividerLookup {
    private final int COLOR;
    private final int SIZE;

    public DividerLookupImpl(int color, int size) {
        this.COLOR = color;
        this.SIZE = size;
    }

    @Override
    public Divider getVerticalDivider(int position) {
        return new Divider.Builder()
                .color(COLOR)
                .size(SIZE)
                .build();
    }

    @Override
    public Divider getHorizontalDivider(int position) {
        return new Divider.Builder()
                .color(COLOR)
                .size(SIZE)
                .build();
    }
}
