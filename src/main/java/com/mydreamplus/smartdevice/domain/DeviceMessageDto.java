package com.mydreamplus.smartdevice.domain;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:32
 * To change this template use File | Settings | File Templates.
 * 下发给PI的消息,这里通过webSocket服务与PI通信
 */
public class DeviceMessageDto {

    /**
     * 指定下发到的PI
     */
    private PIDeviceDto pi;

    /**
     * 请求API接口
     */
    private String api;

    /**
     * 请求的参数
     */
    private Object data;

    /**
     * 消息的类型
     */
    private MessageTypeEnum messageType;


}
