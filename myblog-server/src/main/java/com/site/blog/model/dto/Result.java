package com.site.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果集
 * @author Linn-cn
 * @date 2020/12/7
 */
@Data
@AllArgsConstructor

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer resultCode;
    private String message;
    private T data;
    private Boolean success;

    public Result() {
    }

    public Result(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultCode=" + resultCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", success=" + success +
                '}';
    }



}
