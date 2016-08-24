package com.mydreamplus.smartdevice.domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/8/23
 * Time: 上午10:09
 * To change this template use File | Settings | File Templates.
 * UI显示日志内容
 */
public class EventLog {

    /**
     * 设备标识
     */
    private String symbol;

    /**
     * 事件发生事件
     */
    private Date date;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * PI MAC地址
     */
    private String piMacAddress;

    private String macAddress;
    /**
     * 信号强度
     */
    private int linkQuality;
    /**
     * 传感器
     */
    private String sensorValue;
    /**
     * 开关状态
     */
    private int state;
    /**
     * 耗时
     */
    private long costTime;
    /**
     * ping 获取设备的ping时间
     */
    private long ping;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getPiMacAddress() {
        return piMacAddress;
    }

    public void setPiMacAddress(String piMacAddress) {
        this.piMacAddress = piMacAddress;
    }

    public int getLinkQuality() {
        return linkQuality;
    }

    public void setLinkQuality(int linkQuality) {
        this.linkQuality = linkQuality;
    }

    public String getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(String sensorValue) {
        this.sensorValue = sensorValue;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    public long getPing() {
        return ping;
    }

    public void setPing(long ping) {
        this.ping = ping;
    }

    @Override
    public String toString() {
        return "EventLog{" +
                "symbol='" + symbol + '\'' +
                ", date=" + date +
                ", eventName='" + eventName + '\'' +
                ", piMacAddress='" + piMacAddress + '\'' +
                ", linkQuality=" + linkQuality +
                ", sensorValue=" + sensorValue +
                ", state=" + state +
                ", costTime=" + costTime +
                ", ping=" + ping +
                '}';
    }
}
