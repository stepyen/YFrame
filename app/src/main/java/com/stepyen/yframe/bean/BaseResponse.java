package com.stepyen.yframe.bean;

import com.stepyen.yframe.api.Api;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    public T data;
    public int errorCode;
    public String errorMsg;


    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return errorCode == Api.CODE_SUCCEED;
    }
}
