package com.milkteaking.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.milkteaking.ec.main.sort.content.bean.SectionBean;
import com.milkteaking.ec.main.sort.content.bean.SectionContentItemBean;

import java.util.LinkedList;

/**
 * @author TanJJ
 * @time 2018/5/19 9:37
 * @des 右边Fragment的数据转换器
 */

public class SortContentDataConvert {

    public LinkedList<SectionBean> convert(String response) {
        LinkedList<SectionBean> linkedList = new LinkedList<>();

        JSONArray dataArray = JSON.parseObject(response).getJSONArray("data");
        int dataSize = dataArray.size();

        for (int i = 0; i < dataSize; i++) {
            JSONObject jsonObject = dataArray.getJSONObject(i);
            Integer id = jsonObject.getInteger("id");
            String section = jsonObject.getString("section");
            // boolean isHeader, String header
            // isHeader:是否为头部?
            // header:头部显示什么数据.
            // 添加title
            SectionBean sectionBean = new SectionBean(true, section);
            sectionBean.setId(id);
            sectionBean.setMore(true);
            linkedList.add(sectionBean);

            JSONArray goodsArray = jsonObject.getJSONArray("goods");
            int goodSize = goodsArray.size();
            for (int m = 0; m < goodSize; m++) {
                JSONObject jsonObject1 = goodsArray.getJSONObject(m);
                int goods_id = jsonObject1.getInteger("goods_id");
                String goods_thumb = jsonObject1.getString("goods_thumb");
                String goods_name = jsonObject1.getString("goods_name");

                SectionContentItemBean itemBean = new SectionContentItemBean();
                itemBean.setGoods_id(goods_id);
                itemBean.setGoods_thumb(goods_thumb);
                itemBean.setGoods_name(goods_name);

                linkedList.add(new SectionBean(itemBean));
            }
        }
        return linkedList;
    }
}
