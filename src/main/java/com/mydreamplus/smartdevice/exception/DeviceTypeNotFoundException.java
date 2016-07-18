package com.mydreamplus.smartdevice.exception;

/**
 * 没找到PI,注册失败
 */
public class DeviceTypeNotFoundException extends RuntimeException {
    public DeviceTypeNotFoundException() {
        super();
    }

    public DeviceTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceTypeNotFoundException(String message) {
        super(message);
    }

    public DeviceTypeNotFoundException(Throwable cause) {
        super(cause);
    }

}
