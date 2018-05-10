package com.milkteaking.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * @author TanJJ
 * @time 2018/5/10 9:36
 * @des 多Item实现Bean
 */

public class MultipleItemBean implements MultiItemEntity {
    private ReferenceQueue<LinkedHashMap<String, Object>> mQueue = new ReferenceQueue<>();
    private LinkedHashMap<String, Object> fields = new LinkedHashMap<>();
    // ReferenceQueue与SoftHashMap实现软引用缓存
    // 第一个参数是指定的泛型类类型
    // 第二个参数是引用缓存队列
    // 当FIELDS_REFERENCE里的MULTI_FILED对象被GC的同时，会把该对象的包装类即LinkedHashMap放入到ReferenceQueue里面
    private SoftReference<LinkedHashMap<String, Object>> mSoftReference = new SoftReference<>(fields, mQueue);

    public MultipleItemBean(LinkedHashMap<String, Object> fields) {
        mSoftReference.get().putAll(fields);
    }

    @SuppressWarnings("unchecked")
    public <T> T getFiled(Object key) {
        return (T) mSoftReference.get().get(key);
    }

    public LinkedHashMap<String, Object> getFields() {
        return mSoftReference.get();
    }

    public void setField(String key, Object value) {
        mSoftReference.get().put(key, value);
    }

    @Override
    public int getItemType() {
        return (int) mSoftReference.get().get(MultipleField.ITEM_TYPE.name());
    }

    public static MultipleItemBeanBuilder builder() {
        return new MultipleItemBeanBuilder();
    }
}
