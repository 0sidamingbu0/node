package com.mydreamplus.smartdevice.exception;

/**
 * 没找到PI,注册失败
 */
public class PINotFoundException extends RuntimeException {
    public PINotFoundException() {
        super();
    }

    public PINotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PINotFoundException(String message) {
        super(message);
    }

    public PINotFoundException(Throwable cause) {
        super(cause);
    }

}
