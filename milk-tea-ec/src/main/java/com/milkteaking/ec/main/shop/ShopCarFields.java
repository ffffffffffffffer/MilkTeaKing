package com.milkteaking.ec.main.shop;

/**
 * @author TanJJ
 * @time 2018/5/22 15:20
 * @des 购物车的字段集
 */
/*
    {
        "code":0,
        "message":"OK",
        "data":[
            {
                "title":"商品标题1",
                "desc":"商品的详细描述1",
                "id":1,
                "price":1.00,
                "count":2,
                "thumb":"http://192.168.0.104/latte/miimage/036bed3457b5a1096db58aed1f2ba091.jpg"
            },
            {
                "title":"商品标题2",
                "desc":"商品的详细描述2",
                "id":3,
                "price":2.00,
                "count":3,
                "thumb":"http://192.168.0.104/latte/miimage/4900b70b3ffd893d6c130f0b741fa6f1.jpg"
            }
        ]
    }
 */
public enum ShopCarFields {
    ID,
    TITLE,
    DESC,
    PRICE,
    THUMB,
    COUNT,
    IS_SELECTED,
    POSITION
}
