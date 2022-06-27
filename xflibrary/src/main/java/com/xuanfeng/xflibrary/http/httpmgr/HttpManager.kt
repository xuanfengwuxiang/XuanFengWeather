package com.xuanfeng.xflibrary.http.httpmgr

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.xuanfeng.xflibrary.http.HttpLoader
import com.xuanfeng.xflibrary.http.HttpResponse
import com.xuanfeng.xflibrary.utils.FileUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by xuanfengwuxiang on 2018/8/20.
 * 网络请求外部调用类
 */
class HttpManager {

    companion object {
        val instance: HttpManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpManager()
        }
    }

    //get请求
    fun getJO(url: String, params: Map<String?, Any?>?, httpResponse: HttpResponse<JsonObject>.() -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = HttpResponse<JsonObject>().apply(httpResponse)
            try {
                val jsonObject = HttpLoader.getInstance().service.getJO(url, params)
                response.mOnSuccess?.invoke(jsonObject)
            } catch (e: Exception) {
                Log.e("HttpManager", e.toString())
                response.mOnError?.invoke(e.toString())
            }
            response.mOnComplete?.invoke()
        }
    }

    //post请求
    fun postJO(url: String?, params: Map<String?, String?>?, httpResponse: HttpResponse<JsonObject>.() -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = HttpResponse<JsonObject>().apply(httpResponse)
            try {
                val jsonObject = HttpLoader.getInstance().service.postJO(url, params)
                response.mOnSuccess?.invoke(jsonObject)
            } catch (e: Exception) {
                Log.e("HttpManager", e.toString())
                response.mOnError?.invoke(e.toString())
            }
            response.mOnComplete?.invoke()
        }
    }

    //post请求，入参使用json
    fun <T> postJson(url: String?, t: T, httpResponse: HttpResponse<JsonObject>.() -> Unit) {

        CoroutineScope(Dispatchers.Main).launch {
            val response = HttpResponse<JsonObject>().apply(httpResponse)
            try {
                val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Gson().toJson(t))
                val jsonObject = HttpLoader.getInstance().service.postJsonJO(url, body)
                response.mOnSuccess?.invoke(jsonObject)
            } catch (e: Exception) {
                Log.e("HttpManager", e.toString())
                response.mOnError?.invoke(e.toString())
            }
            response.mOnComplete?.invoke()
        }
    }

    //图片上传
    fun uploadImages(url: String?, filePaths: List<String?>?, httpResponse: HttpResponse<JsonObject>.() -> Unit) {
        uploadFiles(url, filePaths, null, "image/jpg", httpResponse)
    }

    //图片上传--附带信息
    fun uploadImages(url: String?, filePaths: List<String?>?, extra: Map<String?, String?>?, httpResponse: HttpResponse<JsonObject>.() -> Unit) {
        uploadFiles(url, filePaths, extra, "image/jpg", httpResponse)
    }

    //文件上传
    fun uploadFiles(url: String?, filePaths: List<String?>?, httpResponse: HttpResponse<JsonObject>.() -> Unit) {
        uploadFiles(url, filePaths, null, "multipart/form-data", httpResponse)
    }

    //文件上传
    fun uploadFiles(url: String?, filePaths: List<String?>?, extra: Map<String?, String?>?, contentType: String?, httpResponse: HttpResponse<JsonObject>.() -> Unit) {
        if (filePaths == null || filePaths.isEmpty()) {
            return
        }
        val params = LinkedHashMap<String?, RequestBody?>()
        for (path in filePaths) {
            if (!TextUtils.isEmpty(path)) {
                val file = File(path)
                if (file.exists()) {
                    val requestFile = RequestBody.create(MediaType.parse(contentType), file)
                    params["file\"; filename=\"" + file.name + ""] = requestFile
                }
            }
        }
        if (extra != null && !extra.isEmpty()) {
            for (entry in extra.entries) {
                if (entry != null) {
                    val key = if (entry.key != null) entry.key else ""
                    val value = if (entry.value != null) entry.value else ""
                    params[key] = RequestBody.create(MediaType.parse("text/plain"), value)
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            val response = HttpResponse<JsonObject>().apply(httpResponse)
            try {
                val jsonObject = HttpLoader.getInstance().service.uploadFiles(url, params)
                response.mOnSuccess?.invoke(jsonObject)
            } catch (e: Exception) {
                Log.e("HttpManager", e.toString())
                response.mOnError?.invoke(e.toString())
            }
            response.mOnComplete?.invoke()
        }
    }

    //上传单个图片
    fun uploadImage(url: String?, filePath: String?, httpResponse: HttpResponse<JsonObject>.() -> Unit) {
        uploadFile(url, filePath, "multipart/form-data", "image", httpResponse)
    }

    // 上传单个文件
    fun uploadFile(url: String?, filePath: String?, contentType: String?, formDataName: String?, httpResponse: HttpResponse<JsonObject>.() -> Unit) {

        CoroutineScope(Dispatchers.Main).launch {
            val response = HttpResponse<JsonObject>().apply(httpResponse)

            try {

                val requestFile = RequestBody.create(MediaType.parse(contentType), File(filePath))
                val body = MultipartBody.Part.createFormData(formDataName, File(filePath).name, requestFile)

                val jsonObject = HttpLoader.getInstance().service.uploadFile(url, body)
                response.mOnSuccess?.invoke(jsonObject)
            } catch (e: Exception) {
                Log.e("HttpManager", e.toString())
                response.mOnError?.invoke(e.toString())
            }
            response.mOnComplete?.invoke()
        }

    }

    //文件下载
    fun downloadFile(url: String?, savePath: String?, httpResponse: HttpResponse<String>.() -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = HttpResponse<String>().apply(httpResponse)
            try {
                val responseBody = HttpLoader.getInstance().service.downloadFile(url)
                val result = FileUtil.writeResponseBodyToDisk(responseBody, savePath)
                if (result) {
                    response.mOnSuccess?.invoke("success")

                } else {
                    response.mOnError?.invoke("write to disk error")
                }
            } catch (e: Exception) {
                Log.e("HttpManager", e.toString())
                response.mOnError?.invoke(e.toString())
            }
            response.mOnComplete?.invoke()

        }
    }

}