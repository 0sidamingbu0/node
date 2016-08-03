package com.mydreamplus.smartdevice.domain.message;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/19
 * Time: 下午2:37
 * To change this template use File | Settings | File Templates.
 */
public class DeviceMessage extends BaseDeviceMessage {

    private String macAddress;

    private Map<String, Object> data;

    /**
     * 服务器时间
     */
    private long serverTime;

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * Gets mac address.
     *
     * @return the mac address
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Sets mac address.
     *
     * @param macAddress the mac address
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
