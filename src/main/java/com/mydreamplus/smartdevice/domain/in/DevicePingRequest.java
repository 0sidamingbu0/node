package com.mydreamplus.smartdevice.domain.in;

import java.util.Date;

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
    private int ping;

    /**
     * ping的时间
     */
    private Date pingTime;

    /**
     * Gets ping.
     *
     * @return the ping
     */
    public int getPing() {
        return ping;
    }

    /**
     * Sets ping.
     *
     * @param ping the ping
     */
    public void setPing(int ping) {
        this.ping = ping;
    }

    /**
     * Gets ping time.
     *
     * @return the ping time
     */
    public Date getPingTime() {
        return pingTime;
    }

    /**
     * Sets ping time.
     *
     * @param pingTime the ping time
     */
    public void setPingTime(Date pingTime) {
        this.pingTime = pingTime;
    }
}
