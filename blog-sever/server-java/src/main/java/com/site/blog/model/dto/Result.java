package com.site.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
@AllArgsConstructor

public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer resultCode;
    private String message;
    private T data;
    private Boolean success;
    private long timestamp;

    public Result() {
    }

    public Result(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }


}
