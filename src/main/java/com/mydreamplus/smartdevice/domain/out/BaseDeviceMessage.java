package com.mydreamplus.smartdevice.domain.out;

import com.mydreamplus.smartdevice.domain.MessageTypeEnum;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
public class BaseDeviceMessage {

    /**
     * PI的MAC地址
     */
    private String PIAddress;

    /**
     * 请求调用的Action
     */
    private String action;

    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String message;

    /**
     * 消息类型
     */
    private MessageTypeEnum messageType;

    /**
     * 消息的参数内容
     */
    private Object data;

    /**
     * Gets data.
     *
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Gets pi address.
     *
     * @return the pi address
     */
    public String getPIAddress() {
        return PIAddress;
    }

    /**
     * Sets pi address.
     *
     * @param PIAddress the pi address
     */
    public void setPIAddress(String PIAddress) {
        this.PIAddress = PIAddress;
    }

    /**
     * Gets action.
     *
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets action.
     *
     * @param action the action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets message type.
     *
     * @return the message type
     */
    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    /**
     * Sets message type.
     *
     * @param messageType the message type
     */
    public void setMessageType(MessageTypeEnum messageType) {
        this.messageType = messageType;
    }
}
