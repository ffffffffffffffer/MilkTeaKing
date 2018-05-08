package com.milkteaking.core.net;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.milkteaking.core.app.ConfigType;
import com.milkteaking.core.app.MilkTea;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.milkteaking.core.app.MilkTea.getConfigurate;

/**
 * @author TanJJ
 * @time 2018/5/5 9:42
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net
 * @des RestService的构建(使用OkHttp和Retrofit结合使用, 通过Retrofit来创建RestService对象)
 */

public class RestCreator {

    private static class OkHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                // 添加stetho的网络拦截器
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        private static OkHttpClient.Builder addInterceptor() {
            ArrayList<Interceptor> interceptors = MilkTea.getConfigurate(ConfigType.INTERCEPTOR);

            if (interceptors != null) {
                for (Interceptor interceptor : interceptors) {
                    if (interceptor != null) {
                        BUILDER.addInterceptor(interceptor);
                    }
                }
            }
            return BUILDER;
        }

    }

    private static class RetrofitHolder {
        private static final String HOST_URL = getConfigurate(ConfigType.API_HOST);
        private static final Retrofit RETROFIT = new Retrofit.Builder()
                .baseUrl(HOST_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                // 添加拦截器,增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static class RestServiceHolder {
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT.create(RestService.class);
    }

    /**
     * 获取RestService对象
     *
     * @return 返回RestService对象
     */
    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    /**
     * 定义统一的集合对请求信息进行存储
     */
    private static class ParamsHolder {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

    private static class RxRestService {
        private static final RestRxService RX_REST_SERVICE = RetrofitHolder.RETROFIT.create(RestRxService.class);
    }

    /**
     * 获取RestRxService对象
     *
     * @return 返回RestService对象
     */
    public static RestRxService getRestRxService() {
        return RxRestService.RX_REST_SERVICE;
    }
}
