package com.milkteaking.ec.main.sort.content;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.milkteaking.core.ui.image.GlideApp;
import com.milkteaking.ec.R;
import com.milkteaking.ec.main.sort.content.bean.SectionBean;
import com.milkteaking.ec.main.sort.content.bean.SectionContentItemBean;

import java.util.List;

/**
 * @author TanJJ
 * @time 2018/5/19 9:48
 * @des 右边Fragment的适配器
 */

public class SortContentRecyclerAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SortContentRecyclerAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        helper.setText(R.id.tv_section_header, item.header);
        helper.setVisible(R.id.tv_section_more, item.isMore());
        // 设置更多的点击事件
        helper.addOnClickListener(R.id.tv_section_more);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionBean item) {
        // 获取要显示的控件
        AppCompatImageView imageView = helper.getView(R.id.iv_section_content);
        AppCompatTextView content = helper.getView(R.id.tv_section_content);
        // 增加点击事件
        helper.addOnClickListener(R.id.iv_section_content);
        // 获取content的数据
        SectionContentItemBean itemBean = item.t;
        // 设置内容
        content.setText(itemBean.getGoods_name());
        // 显示图片
        GlideApp.with(mContext)
                .load(itemBean.getGoods_thumb())
                .into(imageView);
    }
}
