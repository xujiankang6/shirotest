package com.jiankang.exception;

/**
 * Created by Zhang on 2017/6/20.
 */
public class TriggerOperationException extends RuntimeException {

    public TriggerOperationException() {
    }

    public TriggerOperationException(String message) {
        super(message);
    }

    public TriggerOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TriggerOperationException(Throwable cause) {
        super(cause);
    }

    public TriggerOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
