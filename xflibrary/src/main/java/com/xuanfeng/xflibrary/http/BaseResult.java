package com.xuanfeng.xflibrary.http;

/**
 * http响应模版
 */
public class BaseResult<T> {

    private boolean isOk;//业务是否成功
    private T data;//数据
    private String errorMsg;//错误信息
    private String errorCode;//错误code

    public boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(boolean isOk) {
        this.isOk = isOk;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
