package com.milkteaking.ec.main.sort.content.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author TanJJ
 * @time 2018/5/19 9:28
 * @des 右边Fragment的bean, 将它分成两部分, header一部分, content另一部分
 */

public class SectionBean extends SectionEntity<SectionContentItemBean> {
    private int mId;
    private boolean isMore;

    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionBean(SectionContentItemBean sectionContentItemBean) {
        super(sectionContentItemBean);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }
}
