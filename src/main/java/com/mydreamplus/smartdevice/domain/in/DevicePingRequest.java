package com.mydreamplus.smartdevice.domain.in;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
public class DevicePingRequest extends BaseDeviceRequest {

    /**
     * 设备的PING值
     */
    private Long ping;

    private String macAddress;
    /**
     * x
     * ping的时间
     */
    private Long pingTime;

    /**
     * 服务器Ping时间
     */
    private Long serverTimeStamp;

    public Long getServerTimeStamp() {
        return serverTimeStamp;
    }

    public void setServerTimeStamp(Long serverTimeStamp) {
        this.serverTimeStamp = serverTimeStamp;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * Gets ping.
     *
     * @return the ping
     */
    public Long getPing() {
        return ping;
    }

    /**
     * Sets ping.
     *
     * @param ping the ping
     */
    public void setPing(Long ping) {
        this.ping = ping;
    }

    /**
     * Gets ping time.
     *
     * @return the ping time
     */
    public Long getPingTime() {
        return pingTime;
    }

    /**
     * Sets ping time.
     *
     * @param pingTime the ping time
     */
    public void setPingTime(Long pingTime) {
        this.pingTime = pingTime;
    }
}
