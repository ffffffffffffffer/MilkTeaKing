package com.milkteaking.ec.main.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.milkteaking.ui.recycler.DataCovert;
import com.milkteaking.ui.recycler.ItemType;
import com.milkteaking.ui.recycler.MultipleField;
import com.milkteaking.ui.recycler.MultipleItemBean;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author TanJJ
 * @time 2018/5/10 10:04
 * @des 首页的数据转换类
 */

public class IndexDataConvert extends DataCovert {
    /*
        {
            "code": 0,
            "data": [
                {
                    "banners": [
                        "http://127.0.0.1/index.jpg",
                        "",
                        ""
                    ],
                    "goodsId": 0,
                    "spanSize": 4
                },
                {
                    "goodsId": 1,
                    "imageUrl": "http://127.0.0.1/12345.jpg",
                    "spanSize": 4,
                    "text": "测试描述1"
                }
            ],
            "message": "OK",
            "page_size": 6,
            "total": 100
        }
    */
    @Override
    public LinkedList<MultipleItemBean> convert() {
        JSONArray data = JSON.parseObject(getJson()).getJSONArray("data");
        int size = data.size();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = data.getJSONObject(i);
            Integer goodsId = jsonObject.getInteger("goodsId");
            Integer spanSize = jsonObject.getInteger("spanSize");
            String text = jsonObject.getString("text");
            String imageUrl = jsonObject.getString("imageUrl");
            JSONArray banners = jsonObject.getJSONArray("banners");
            ArrayList<String> bannerImages = new ArrayList<>();

            int type = 0;

            if (text != null && imageUrl == null) {
                type = ItemType.TEXT;
            } else if (imageUrl != null && text == null) {
                type = ItemType.IMAGE;
            } else if (imageUrl != null && text != null) {
                type = ItemType.TEXT_IMAGE;
            } else if (banners != null && banners.size() > 0) {
                type = ItemType.BANNER;
            }
            // 遍历banners
            int bannerSize = banners.size();
            for (int m = 0; m < bannerSize; m++) {
                String url = banners.getString(m);
                bannerImages.add(url);
            }

            // 添加数据
            MultipleItemBean bean = MultipleItemBean.builder()
                    .setField(MultipleField.TEXT.name(), text)
                    .setField(MultipleField.IMAGE_URL.name(), imageUrl)
                    .setField(MultipleField.BANNER.name(), bannerImages)
                    .setField(MultipleField.SPAN_SIZE.name(), spanSize)
                    .build();

            BEANS.add(bean);
            return BEANS;
        }
        return null;
    }
}
