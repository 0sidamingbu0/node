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


    public PingDto(int value) {
        this.value = value;
    }

    public PingDto(int value, Date lastPingTime) {
        this.value = value;
        this.lastPingTime = lastPingTime;
    }

    /**
     * 当前PING的值 ,将延迟情况映射到1-100之间
     */
    private int value;

    /**
     * 上次PING的时间
     */
    private Date lastPingTime;

    /**
     * 下次PING的时间
     */
    private Date nextPingTime;

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(int value) {
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

    /**
     * Gets next ping time.
     *
     * @return the next ping time
     */
    public Date getNextPingTime() {
        return nextPingTime;
    }

    /**
     * Sets next ping time.
     *
     * @param nextPingTime the next ping time
     */
    public void setNextPingTime(Date nextPingTime) {
        this.nextPingTime = nextPingTime;
    }
}
