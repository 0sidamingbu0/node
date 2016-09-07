package com.mydreamplus.smartdevice.domain.message;

import com.mydreamplus.smartdevice.domain.MessageTypeEnum;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
public class BaseDeviceMessage {

    /**
     * PI的MAC    Android MacAddress
     */
    private String piAddress;

    /**
     * 请求调用的Action
     */
    private String action;

    /**
     * 信息
     */
    private String message;


    private Map<String, Object> data;

    /**
     * 设备的时间戳
     */
    private long serverTime;
    /**
     * 消息类
     */
    private MessageTypeEnum messageType;

    public BaseDeviceMessage() {
        this.setServerTime(System.currentTimeMillis());
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getPiAddress() {
        return piAddress;
    }

    public void setPiAddress(String piAddress) {
        this.piAddress = piAddress;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeEnum messageType) {
        this.messageType = messageType;
    }
}
