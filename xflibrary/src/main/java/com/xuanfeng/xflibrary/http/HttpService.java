package com.xuanfeng.xflibrary.http;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
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

/**
 * Created by zhujh on 2017/7/19.
 * 描述：retrofit接口请求(不同的业务请求方法)
 */

public interface HttpService {


    //GET请求==================
    @GET
    Observable<JsonObject> getJO(
            @Url String url,
            @QueryMap Map<String, String> params
    );

    @GET
    Observable<String> getS(
            @Url String url,
            @QueryMap Map<String, String> params
    );

    @GET
    Observable<JsonArray> getJA(
            @Url String url,
            @QueryMap Map<String, String> params
    );


    //POST请求===================
    @POST
    @FormUrlEncoded
    Observable<JsonObject> postJO(
            @Url String url,
            @FieldMap Map<String, String> params);


    @POST
    @FormUrlEncoded
    Observable<String> postS(
            @Url String url,
            @FieldMap Map<String, String> params);


    @POST
    @FormUrlEncoded
    Observable<JsonArray> postJA(
            @Url String url,
            @FieldMap Map<String, String> params);



    //通用POST请求(带头信息)
    @POST
    @FormUrlEncoded
    Observable<JsonObject> postHeaderJO(
            @Url String url,
            @Header("Runbey-Secinfo") String runbeySecinfo,
            @FieldMap Map<String, String> params);

    //通用POST请求(json格式上传)
    @POST
    Observable<JsonObject> postJsonJO(
            @Url String url,
            @Body RequestBody jsonBody);


    //多个文件上传
    @Multipart
    @POST
    Observable<JsonObject> uploadFiles(
            @Url String url,
            @PartMap Map<String, RequestBody> params);

    //多个文件上传--附带信息
    @Multipart
    @POST
    @FormUrlEncoded
    Observable<JsonObject> uploadFiles(
            @Url String url,
            @PartMap Map<String, RequestBody> params,
            @FieldMap Map<String, String> infos);

    //文件下载
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);


}
