package com.mydreamplus.smartdevice.exception;

/**
 * for HTTP 400 errors
 */
public final class DataInvalidException extends RuntimeException {
    public DataInvalidException() {
        super();
    }

    public DataInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataInvalidException(String message) {
        super(message);
    }

    public DataInvalidException(Throwable cause) {
        super(cause);
    }
}