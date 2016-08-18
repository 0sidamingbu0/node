package com.mydreamplus.smartdevice.domain.message;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/19
 * Time: 下午2:37
 * To change this template use File | Settings | File Templates.
 */
public class DeviceMessage extends BaseDeviceMessage {

    private String macAddress;

    private String config;
    /**
     * 服务 时间
     */
    private long serverTime;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
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
