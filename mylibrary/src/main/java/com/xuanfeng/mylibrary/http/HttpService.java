package com.xuanfeng.mylibrary.http;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by zhujh on 2017/7/19.
 * 描述：retrofit接口请求(不同的业务请求方法)
 */

public interface HttpService {


    //通用GET请求
    @GET
    Observable<JsonObject> callByGet(
            @Url String url,
            @QueryMap LinkedHashMap<String, String> params
    );

    //通用GET请求,返回String
    @GET
    Observable<String> callByGetReturnString(
            @Url String url,
            @QueryMap LinkedHashMap<String, String> params
    );

    //通用GET请求,返回JsonArray
    @GET
    Observable<JsonArray> callByGetReturnJsonArray(
            @Url String url,
            @QueryMap LinkedHashMap<String, String> params
    );


    //通用POST请求
    @POST
    @FormUrlEncoded
    Observable<JsonObject> callByPost(
            @Url String url,
            @FieldMap LinkedHashMap<String, String> params);

    //通用POST请求(带头信息)
    @POST
    @FormUrlEncoded
    Observable<JsonObject> callByPostWithHeader(
            @Url String url,
            @Header("Runbey-Secinfo") String runbeySecinfo,
            @FieldMap LinkedHashMap<String, String> params);

    //通用POST请求(json格式上传)
    @POST
    Observable<JsonObject> callByPostUseJson(
            @Url String url,
            @Body RequestBody jsonBody);


    //多个文件上传
    @Multipart
    @POST
    Observable<JsonObject> uploadFiles(
            @Url String url,
            @PartMap Map<String, RequestBody> params);

    //文件下载
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);


}
