package com.mydreamplus.smartdevice.domain;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:33
 * To change this template use File | Settings | File Templates.
 * 云端下发消息类型
 */
public enum  MessageTypeEnum {

    /**
     * 删除设备、获取设备状态、下发策略、获取PING值、获取传感器值, 反馈
     */
    DELETE, GET_INFO, SEND_POLICY, GET_PING, GET_VALUE, FEEDBACK, COMMAND, CONFIG
}
