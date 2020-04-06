package com.ls.exception;

/**
 *  异常类
 * @author Administrator
 * @create 2017/12/3
 * @since 1.0.0
 */
public class LsException extends RuntimeException{

    public LsException() {
        super();
    }

    public LsException(String message) {
        super(message);
    }

    public LsException(String message, Throwable cause) {
        super(message, cause);
    }

    public LsException(Throwable cause) {
        super(cause);
    }

    protected LsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}