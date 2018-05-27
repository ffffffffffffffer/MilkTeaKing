package com.milkteaking.ec.main.personal.list;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.milkteaking.core.ui.image.GlideApp;
import com.milkteaking.ec.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author TanJJ
 * @time 2018/5/26 23:31
 * @des 个人中心的适配器
 */

public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ListAdapter(List<ListBean> data) {
        super(data);
        initItemType();
    }

    private void initItemType() {
        addItemType(ListItemType.ITEM_NORMAL, R.layout.arrow_item_layout);
        addItemType(ListItemType.ITEM_AVATAR, R.layout.arrow_item_avatar);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        switch (item.getItemType()) {
            case ListItemType.ITEM_NORMAL:
                helper.setText(R.id.tv_arrow_text, item.getText());
                helper.setText(R.id.tv_arrow_value, item.getValue());
                break;
            case ListItemType.ITEM_AVATAR:
                String imageUrl = item.getImageUrl();
                CircleImageView avatarImageView = helper.getView(R.id.img_arrow_avatar);
                GlideApp.with(mContext)
                        .load(imageUrl)
                        .into(avatarImageView);
                break;
            default:
                break;
        }

    }
}
