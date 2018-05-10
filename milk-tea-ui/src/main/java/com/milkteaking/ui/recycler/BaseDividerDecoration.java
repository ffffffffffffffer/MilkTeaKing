package com.milkteaking.ui.recycler;

import com.choices.divider.DividerItemDecoration;

/**
 * @author TanJJ
 * @time 2018/5/10 19:34
 */

public class BaseDividerDecoration extends DividerItemDecoration {
    public BaseDividerDecoration(int color, int size) {
        setDividerLookup(new DividerLookupImpl(color, size));
    }

    public static BaseDividerDecoration create(int color, int size) {
        return new BaseDividerDecoration(color, size);
    }
}
