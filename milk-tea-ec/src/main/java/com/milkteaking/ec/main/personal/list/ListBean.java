package com.milkteaking.ec.main.personal.list;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author TanJJ
 * @time 2018/5/26 23:23
 * @des 个人中心列表的bean
 */

public class ListBean implements MultiItemEntity {

    private final int mId;
    private final int mItemType;
    private final String mText;
    private final String mValue;
    private final String mImageUrl;

    private ListBean(int id, int itemType, String text, String value, String imageUrl) {
        mId = id;
        mItemType = itemType;
        mText = text;
        mValue = value;
        mImageUrl = imageUrl;
    }

    public int getId() {
        return mId;
    }

    public String getText() {
        return mText;
    }

    public String getValue() {
        return mValue;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public static class Builder {
        private int mId;
        private int mItemType;
        private String mText;
        private String mValue;
        private String mImageUrl;

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public Builder setItemType(int itemType) {
            mItemType = itemType;
            return this;
        }

        public Builder setText(String text) {
            mText = text;
            return this;
        }

        public Builder setValue(String value) {
            mValue = value;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            mImageUrl = imageUrl;
            return this;
        }

        public ListBean build() {
            return new ListBean(mId, mItemType, mText, mValue, mImageUrl);
        }
    }

    @Override
    public int getItemType() {
        return ListItemType.ITEM_NORMAL;
    }
}
