package com.milkteaking.core.net.rx;

import com.milkteaking.core.util.storage.Preference;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author TanJJ
 * @time 2018/5/20 11:00
 * @des 添加Cookie的拦截器
 */

public class AddCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        // 响应式编程每个方法前面的那个方法的返回值都会影响下一个的泛型参数
        Observable.just(Preference.getCustomAppProfile("cookie"))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String cookie) throws Exception {
                        // 给原生Api附带上WebView拦截下来的Cookie
                        builder.addHeader("cookie", cookie);
                    }
                });

        return chain.proceed(builder.build());
    }
}
