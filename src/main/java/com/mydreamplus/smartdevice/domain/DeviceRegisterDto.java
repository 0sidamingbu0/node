package com.mydreamplus.smartdevice.domain;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:50
 * To change this template use File | Settings | File Templates.
 */
public class DeviceRegisterDto {

    /**
     * 设备的唯一标识,由NODE解析生成(mac地址)
     * mac + 短地址 + 资源序号
     */
    private String symbol;

    /**
     * 设备所在的PI设备, PI的MAC地址
     */
    private String PIID;

    /**
     * 设备的类型
     */
    private String deviceType;

    /**
     * 设备的MAC地址
     */
    private String macAddress;

    /**
     * 短地址
     */
    private int shortAddress;

    /**
     * 资源序号
     */
    private int index;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPIID() {
        return PIID;
    }

    public void setPIID(String PIID) {
        this.PIID = PIID;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(int shortAddress) {
        this.shortAddress = shortAddress;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
