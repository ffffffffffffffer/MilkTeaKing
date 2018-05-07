package com.milkteaking.core.net.interceptor;

import android.support.annotation.RawRes;

import com.milkteaking.core.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author TanJJ
 * @time 2018/5/7 15:54
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net.interceptor
 * @des 调式时用的拦截器
 */

public class DebugInterceptor extends BaseInterceptor {
    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debug_url, int debug_raw_id) {
        DEBUG_URL = debug_url;
        DEBUG_RAW_ID = debug_raw_id;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }

    /*
        获取测试的Response
     */
    private Response debugResponse(Chain chain, @RawRes int debug_raw_id) {
        // 获取rawId对应的原始数据
        String rawFileData = FileUtil.getRawFile(debug_raw_id);
        // 获取responseBody
        return getResponse(chain, rawFileData);
    }

    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }
}
