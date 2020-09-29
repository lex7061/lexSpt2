package com.lex.comm.exception;

/**
 * 描述：自定义异常，在数据存在验证问题或其他问题时抛出
 * 创建人：lex
 * 创建时间：2020-09-29 14:43
 * 更新人：
 * 更新时间：
 */
public class DataException extends RuntimeException {
    public DataException() {
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(Throwable cause) {
        super(cause);
    }

    public DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
