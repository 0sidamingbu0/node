package com.mydreamplus.smartdevice.domain.in;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/25
 * Time: 下午5:08
 * To change this template use File | Settings | File Templates.
 */
public class PIRegisterRequest extends BaseDeviceRequest {

    private String piIpAddress;

    /**
     * 主机名
     */
    private String hostName;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPiIpAddress() {
        return piIpAddress;
    }

    public void setPiIpAddress(String piIpAddress) {
        this.piIpAddress = piIpAddress;
    }
}
