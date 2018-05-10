package com.milkteaking.ui.recycler;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.milkteaking.core.ui.image.GlideApp;
import com.milkteaking.ui.R;
import com.milkteaking.ui.banners.BannersCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TanJJ
 * @time 2018/5/10 14:32
 * @des 多Item的RecyclerView的适配器
 */
// 因为BaseMultiItemQuickAdapter这个是别人封装好的,本来的RecyclerView.Adapter只需要ViewHolder
public class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemBean, MultipleRecyclerAdapter
        .ViewHolder> implements BaseQuickAdapter.SpanSizeLookup {

    class ViewHolder extends BaseViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    public MultipleRecyclerAdapter(List<MultipleItemBean> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemBean> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataCovert covert) {
        return new MultipleRecyclerAdapter(covert.convert());
    }

    private void init() {
        // 将用到的item全部加载进来
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_text_image);
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner);

        // 设置宽度监听
        setSpanSizeLookup(this);
        // 开启加载动画
        openLoadAnimation();
        // 多次执行动画
        isFirstOnly(false);
    }


    @Override
    protected void convert(ViewHolder helper, MultipleItemBean item) {
        // 解析数据
        int itemType = item.getItemType();
        switch (itemType) {
            case ItemType.TEXT:
                String text = item.getFiled(MultipleField.TEXT);
                // 设置text
                helper.setText(R.id.text_single, text);
                break;
            case ItemType.IMAGE:
                String image = item.getFiled(MultipleField.IMAGE_URL);
                AppCompatImageView imageView = helper.getView(R.id.img_single);
                // 加载图片
                GlideApp.with(mContext)
                        .load(image)
                        .apply(com.milkteaking.core.glide.GlideOptions.OPTIONS)
                        .into(imageView);
                break;
            case ItemType.TEXT_IMAGE:
                text = item.getFiled(MultipleField.TEXT);
                // 设置text
                helper.setText(R.id.tv_multiple, text);
                image = item.getFiled(MultipleField.IMAGE_URL);
                imageView = helper.getView(R.id.img_multiple);
                // 加载图片
                GlideApp.with(mContext)
                        .load(image)
                        .apply(com.milkteaking.core.glide.GlideOptions.OPTIONS)
                        .into(imageView);
                break;
            case ItemType.BANNER:
                ArrayList<String> banners = item.getFiled(MultipleField.BANNER);
                ConvenientBanner<String> convenientBanners = helper.getView(R.id.banner_recycler);
                // 设置ConvenientBanner
                BannersCreator.setDefault(convenientBanners, banners, new com.bigkoo.convenientbanner.listener
                        .OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // 点击banner后的处理
                        ToastUtils.showShort("点击: " + position);
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getFiled(MultipleField.SPAN_SIZE);
    }

}
