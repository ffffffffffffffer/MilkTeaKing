package com.milkteaking.core.net;

import java.util.Observer;
import java.util.WeakHashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author TanJJ
 * @time 2018/5/5 9:25
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.net
 * @des RestFul Api请求接口
 */

public interface RestService {
    @GET
    Call<String> get(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap WeakHashMap<String, Observer> params);

    @POST
    Call<String> postRaw(@Url String url, @Body WeakHashMap<String, Object> params);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    @PUT
    Call<String> putRaw(@Url String url, @Body WeakHashMap<String, Object> params);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap WeakHashMap<String, Object> param);

    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part body);

    @Streaming
    @GET
    Call<RequestBody> download(@Url String url, @QueryMap WeakHashMap<String, Object> params);
}
