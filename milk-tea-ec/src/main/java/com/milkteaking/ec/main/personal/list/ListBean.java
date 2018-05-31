package com.milkteaking.ec.main.personal.list;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.milkteaking.core.fragments.MilkTeaFragment;

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
    private final MilkTeaFragment mMilkTeaFragment;
    private final CompoundButton.OnCheckedChangeListener mCheckedChangeListener;

    private ListBean(int id, int itemType, String text, String value, String imageUrl, MilkTeaFragment
            milkTeaFragment, CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        mId = id;
        mItemType = itemType;
        mText = text;
        mValue = value;
        mImageUrl = imageUrl;
        mMilkTeaFragment = milkTeaFragment;
        mCheckedChangeListener = checkedChangeListener;
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

    public MilkTeaFragment getMilkTeaFragment() {
        return mMilkTeaFragment;
    }

    public CompoundButton.OnCheckedChangeListener getCheckedChangeListener() {
        return mCheckedChangeListener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static class Builder {
        private int mId;
        private int mItemType;
        private String mText;
        private String mValue;
        private String mImageUrl;
        private MilkTeaFragment mMilkTeaFragment;
        private CompoundButton.OnCheckedChangeListener mCheckedChangeListener;

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

        public Builder setMilkTeaFragment(MilkTeaFragment milkTeaFragment) {
            mMilkTeaFragment = milkTeaFragment;
            return this;
        }

        public Builder setCheckedChangeListener(CompoundButton.OnCheckedChangeListener checkedChangeListener) {
            mCheckedChangeListener = checkedChangeListener;
            return this;
        }

        public ListBean build() {
            return new ListBean(mId, mItemType, mText, mValue, mImageUrl, mMilkTeaFragment, mCheckedChangeListener);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
