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
     * 资源序号
     */
    private int index;


    private String deviceType;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
