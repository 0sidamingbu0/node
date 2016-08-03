package com.mydreamplus.smartdevice.domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午2:11
 * To change this template use File | Settings | File Templates.
 * PING 检查设备的连接延迟情况
 */
public class PingDto {

    /**
     */
    private Long value;
    /**
     * 上报PING的时间
     */
    private Date lastPingTime;


    /**
     * Instantiates a new Ping dto.
     *
     * @param value        the value
     * @param lastPingTime the last ping time
     */
    public PingDto(Long value, Date lastPingTime) {
        this.value = value;
        this.lastPingTime = lastPingTime;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public Long getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(Long value) {
        this.value = value;
    }

    /**
     * Gets last ping time.
     *
     * @return the last ping time
     */
    public Date getLastPingTime() {
        return lastPingTime;
    }

    /**
     * Sets last ping time.
     *
     * @param lastPingTime the last ping time
     */
    public void setLastPingTime(Date lastPingTime) {
        this.lastPingTime = lastPingTime;
    }

}
