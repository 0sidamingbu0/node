package com.mydreamplus.smartdevice.exception;

/**
 * 没有找到设备
 */
public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException() {
        super();
    }

    public DeviceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceNotFoundException(String message) {
        super(message);
    }

    public DeviceNotFoundException(Throwable cause) {
        super(cause);
    }

}
