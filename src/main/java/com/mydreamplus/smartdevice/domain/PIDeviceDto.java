package com.mydreamplus.smartdevice.domain;

import com.mydreamplus.smartdevice.domain.in.BaseDeviceRequest;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:49
 * To change this template use File | Settings | File Templates.
 */
public class PIDeviceDto extends BaseDeviceRequest {
    /**
     * PI的名称
     */
    private String name;

    private String ipAddress;

    private String hostName;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

}
