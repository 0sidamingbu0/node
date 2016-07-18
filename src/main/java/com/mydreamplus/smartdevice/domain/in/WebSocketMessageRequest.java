package com.mydreamplus.smartdevice.domain.in;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 * 请求WebSocket服务 消息体
 */
public class WebSocketMessageRequest {

    /**
     * 设备的MAC地址
     */
    private String deviceId;

    private DeviceMessageRequest message;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceMessageRequest getMessage() {
        return message;
    }

    public void setMessage(DeviceMessageRequest message) {
        this.message = message;
    }
}
