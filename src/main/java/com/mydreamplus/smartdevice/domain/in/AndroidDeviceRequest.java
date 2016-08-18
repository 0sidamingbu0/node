package com.mydreamplus.smartdevice.domain.in;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 * 设备请求基础类
 */
public class AndroidDeviceRequest extends BaseRequest {

    private String macAddress;

    /**
     * Android设备类型: ANDROIDTV 、Coffee
     */
    private String deviceType;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
