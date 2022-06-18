package com.site.blog.exception;

/**
 * @author yanni
 * @date time 2022/6/17 15:58
 * @modified By:
 */
public class ServiceException extends RuntimeException{
    private Integer code;

    private String message;

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ServiceException(AbstractBaseException exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.message = exception.getMessage();
    }
}
