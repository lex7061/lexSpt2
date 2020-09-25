package com.lex.comm.exception;

/**
 * 描述：自定义异常（服务异常）
 * 创建人：lxx
 * 创建时间：2020-09-17 13:34
 * 更新人：
 * 更新时间：
 */
public class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
