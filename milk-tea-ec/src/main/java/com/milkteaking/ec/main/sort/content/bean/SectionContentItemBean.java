package com.milkteaking.ec.main.sort.content.bean;

/**
 * @author TanJJ
 * @time 2018/5/19 9:25
 */
/*
    "data":[
            {
                "id":1,
                "section":"标题1",
                "goods":[
                    {
                        "goods_id":3,
                        "goods_thumb":"http://192.168.0.101/latte/miimage/8f1e1ba785cb57a1131861e5b57ab685.jpg",
                        "goods_name":"品牌1"
                    },
                    {
                        "goods_id":4,
                        "goods_thumb":"http://192.168.0.101/latte/miimage/4c525dd220d1901f7556c02af214bcdc.jpg",
                        "goods_name":"品牌2"
                    },
                    {
                        "goods_id":5,
                        "goods_thumb":"http://192.168.0.101/latte/miimage/8f1e1ba785cb57a1131861e5b57ab685.jpg",
                        "goods_name":"品牌3"
                    },
                    {
                        "goods_id":6,
                        "goods_thumb":"http://192.168.0.101/latte/miimage/4c525dd220d1901f7556c02af214bcdc.jpg",
                        "goods_name":"品牌4"
                    }
                ]
            },
 */
public class SectionContentItemBean {
    private int mGoods_id;
    private String mGoods_thumb;
    private String mGoods_name;

    public int getGoods_id() {
        return mGoods_id;
    }

    public void setGoods_id(int goods_id) {
        mGoods_id = goods_id;
    }

    public String getGoods_thumb() {
        return mGoods_thumb;
    }

    public void setGoods_thumb(String goods_thumb) {
        mGoods_thumb = goods_thumb;
    }

    public String getGoods_name() {
        return mGoods_name;
    }

    public void setGoods_name(String goods_name) {
        mGoods_name = goods_name;
    }
}
