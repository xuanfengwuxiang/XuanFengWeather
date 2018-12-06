package com.xuanfeng.mylibrary.http;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by zhujh on 2017/7/19.
 * 描述：retrofit接口请求(不同的业务请求方法)
 */

public interface HttpService {


    /**
     * 通用GET请求
     *
     * @param url
     * @param params
     * @return
     */
    @GET
    Observable<JsonObject> callByGet(
            @Url String url,
            @QueryMap LinkedHashMap<String, String> params
    );

    /**
     * 通用GET请求
     *
     * @param url
     * @param params
     * @return
     */
    @GET
    Observable<String> callByGetReturnString(
            @Url String url,
            @QueryMap LinkedHashMap<String, String> params
    );

    /**
     * 通用GET请求
     *
     * @param url
     * @param params
     * @return
     */
    @GET
    Observable<JsonArray> callByGetReturnJsonArray(
            @Url String url,
            @QueryMap LinkedHashMap<String, String> params
    );


    /**
     * 通用POST请求
     */
    @POST
    @FormUrlEncoded
    Observable<JsonObject> callByPost(
            @Url String url,
            @FieldMap LinkedHashMap<String, String> params);

    /**
     * 通用POST请求(带头信息)
     */
    @POST
    @FormUrlEncoded
    Observable<JsonObject> callByPostWithHeader(
            @Url String url,
            @Header("Runbey-Secinfo") String runbeySecinfo,
            @FieldMap LinkedHashMap<String, String> params);


    /**
     * 多个文件上传
     *
     * @param url
     * @param params
     * @return
     */
    @Multipart
    @POST
    Observable<JsonObject> uploadFiles(
            @Url String url,
            @PartMap Map<String, RequestBody> params);

}
