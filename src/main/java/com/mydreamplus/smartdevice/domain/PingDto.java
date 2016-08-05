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
     * ZIGBEE设备与PI之间的PING
     */
    private Long zbDevicePiPing;
    /**
     * 云端与ZIGBEE设备之间的PING
     */
    private Long serverZbDevicePing;

    private int linkQuality;

    /**
     * 设备MAC地址
     */
    private String macAddress;

    private Date date;


    public PingDto(Long zbDevicePiPing, Long serverZbDevicePing) {
        this.zbDevicePiPing = zbDevicePiPing;
        this.serverZbDevicePing = serverZbDevicePing;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLinkQuality() {
        return linkQuality;
    }

    public void setLinkQuality(int linkQuality) {
        this.linkQuality = linkQuality;
    }

    public Long getZbDevicePiPing() {
        return zbDevicePiPing;
    }

    public void setZbDevicePiPing(Long zbDevicePiPing) {
        this.zbDevicePiPing = zbDevicePiPing;
    }

    public Long getServerZbDevicePing() {
        return serverZbDevicePing;
    }

    public void setServerZbDevicePing(Long serverZbDevicePing) {
        this.serverZbDevicePing = serverZbDevicePing;
    }
}
