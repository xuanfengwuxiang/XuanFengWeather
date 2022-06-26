package com.xuanfeng.xflibrary.http

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by zhujh on 2017/7/19.
 * 描述：retrofit接口请求(不同的业务请求方法)
 */
interface HttpService {
    //GET请求==================
    @GET
    suspend fun getJO(@Url url: String?, @QueryMap params: Map<String?, @JvmSuppressWildcards Any?>?): JsonObject?

    @GET
    suspend fun getS(@Url url: String?, @QueryMap params: Map<String?, String?>?): Observable<String?>?

    @GET
    suspend fun getJA(@Url url: String?, @QueryMap params: Map<String?, String?>?): Observable<JsonArray?>?

    //POST请求===================
    @POST
    @FormUrlEncoded
    suspend fun postJO(@Url url: String?, @FieldMap params: Map<String?, String?>?): JsonObject?

    @POST
    @FormUrlEncoded
    suspend fun postS(@Url url: String?, @FieldMap params: Map<String?, String?>?): Observable<String?>?

    @POST
    @FormUrlEncoded
    suspend fun postJA(@Url url: String?, @FieldMap params: Map<String?, String?>?): Observable<JsonArray?>?

    //通用POST请求(带头信息)
    @POST
    @FormUrlEncoded
    suspend fun postHeaderJO(@Url url: String?, @Header("Runbey-Secinfo") runbeySecinfo: String?, @FieldMap params: Map<String?, String?>?): Observable<JsonObject?>?

    //通用POST请求(json格式上传)
    @POST
    suspend fun postJsonJO(@Url url: String?, @Body jsonBody: RequestBody?): JsonObject?

    //多个文件上传
    @Multipart
    @POST
    suspend fun uploadFiles(@Url url: String?, @PartMap params: Map<String?, RequestBody?>?): JsonObject?

    //单个文件上传
    @Multipart
    @POST
    suspend fun uploadFile(@Url url: String?, @Part part: MultipartBody.Part?): JsonObject?

    //文件下载
    @Streaming
    @GET
    suspend fun downloadFile(@Url url: String?): ResponseBody?
}