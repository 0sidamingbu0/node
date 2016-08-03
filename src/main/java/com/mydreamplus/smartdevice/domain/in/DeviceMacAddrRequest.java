package com.mydreamplus.smartdevice.domain.in;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
public class DeviceMacAddrRequest extends BaseRequest {

    /**
     * 设备的mac地址
     */
    private String macAddress;

    private String piMacAddress;

    public String getPiMacAddress() {
        return piMacAddress;
    }

    public void setPiMacAddress(String piMacAddress) {
        this.piMacAddress = piMacAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
