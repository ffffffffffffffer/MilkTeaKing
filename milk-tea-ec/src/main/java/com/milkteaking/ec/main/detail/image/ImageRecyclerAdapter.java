package com.milkteaking.ec.main.detail.image;

import android.support.v7.widget.AppCompatImageView;

import com.milkteaking.core.glide.GlideOptions;
import com.milkteaking.core.ui.image.GlideApp;
import com.milkteaking.ec.R;
import com.milkteaking.ui.recycler.ItemType;
import com.milkteaking.ui.recycler.MultipleField;
import com.milkteaking.ui.recycler.MultipleItemBean;
import com.milkteaking.ui.recycler.MultipleRecyclerAdapter;
import com.milkteaking.ui.recycler.MultipleRecyclerViewHolder;

import java.util.List;

/**
 * @author TanJJ
 * @time 2018/6/17 20:27
 * @des 商品详情图片的适配器
 */

public class ImageRecyclerAdapter extends MultipleRecyclerAdapter {
    public ImageRecyclerAdapter(List<MultipleItemBean> data) {
        super(data);
        addItemType(ItemType.SINGLE_BIG_IMAGE, R.layout.item_image);
    }

    @Override
    protected void convert(MultipleRecyclerViewHolder helper, MultipleItemBean item) {
        super.convert(helper, item);
        switch (item.getItemType()) {
            case ItemType.SINGLE_BIG_IMAGE:
                AppCompatImageView imageView = helper.getView(R.id.image_rv_item);
                String imageUrl = item.getFiled(MultipleField.IMAGE_URL.name());
                GlideApp.with(mContext)
                        .load(imageUrl)
                        .apply(GlideOptions.OPTIONS)
                        .into(imageView);
                break;
            default:
                break;
        }
    }
}
