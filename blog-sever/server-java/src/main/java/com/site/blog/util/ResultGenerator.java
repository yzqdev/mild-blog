package com.site.blog.util;

import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.Result;


public class ResultGenerator {

    private ResultGenerator() {
    }

    public static <T> Result<T> getResultByHttp(HttpStatusEnum constants, String msg, T data) {
        Result<T> result = new Result<>();
        result.setResultCode(constants.getStatus());
        result.setMessage(msg);
        result.setData(data);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }

    public static <T> Result<T> getResultByHttp(HttpStatusEnum constants, T data) {
        Result<T> result = new Result<>();
        result.setResultCode(constants.getStatus());
        result.setMessage(constants.getContent());
        result.setData(data);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }

    public static <T> Result<T> getResultByHttp(HttpStatusEnum constants, boolean success, T data) {
        Result<T> result = new Result<>();
        result.setResultCode(constants.getStatus());
        result.setMessage(constants.getContent());
        result.setData(data);
        result.setSuccess(success);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }

    /**
     * 自定义提示消息
     *
     * @param constants Http枚举
     * @param msg       提示消息
     */
    public static Result<String> getResultByMsg(HttpStatusEnum constants, String msg) {
        Result<String> result = new Result<>();
        result.setResultCode(constants.getStatus());
        result.setMessage(msg);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }


    public static Result<String> getResultByHttp(HttpStatusEnum constants) {
        Result<String> result = new Result<>();
        result.setResultCode(constants.getStatus());
        result.setMessage(constants.getContent());
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }
}
