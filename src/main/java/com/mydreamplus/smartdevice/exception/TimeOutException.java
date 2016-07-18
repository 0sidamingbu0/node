package com.mydreamplus.smartdevice.exception;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 * 连接超时异常
 */
public class TimeOutException extends RuntimeException {

    public TimeOutException(String message) {
        super(message);
    }
}
