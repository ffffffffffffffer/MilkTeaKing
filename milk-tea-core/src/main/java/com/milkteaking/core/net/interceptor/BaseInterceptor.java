package com.milkteaking.core.net.interceptor;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * @author TanJJ
 * @time 2018/5/7 15:38
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net.interceptor
 * @des 模拟服务器的基类拦截器
 */

public abstract class BaseInterceptor implements Interceptor {


    /**
     * 把chain中的param以集合的形式返回出去
     */
    protected LinkedHashMap<String, String> getUrlParameter(Chain chain) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        HttpUrl url = chain.request().url();
        int querySize = url.querySize();
        for (int i = 0; i < querySize; i++) {
            params.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        return params;
    }

    /**
     * 获取chain中指定key对应的value
     *
     * @param key 指定key
     *
     * @return 返回指定key对应的value
     */
    protected String getUrlParameter(Chain chain, String key) {
        return getUrlParameter(chain).get(key);
    }

    /**
     * 获取chain中的body,以集合返回
     *
     * @return body以集合返回
     */
    protected LinkedHashMap<String, String> getUrlBody(Chain chain) {
        LinkedHashMap<String, String> urlBody = new LinkedHashMap<>();
        FormBody body = (FormBody) chain.request().body();
        int bodySize = body.size();
        for (int i = 0; i < bodySize; i++) {
            urlBody.put(body.encodedName(i), body.encodedValue(i));
        }
        return urlBody;
    }

    /**
     * 获取chain中指定key对应的value
     *
     * @param key 指定key
     *
     * @return 返回指定key对应的value
     */
    protected String getUrlBody(Chain chain, String key) {
        return getUrlBody(chain).get(key);
    }

}
