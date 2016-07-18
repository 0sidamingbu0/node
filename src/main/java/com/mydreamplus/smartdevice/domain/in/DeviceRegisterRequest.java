package com.mydreamplus.smartdevice.domain.in;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/12
 * Time: 下午8:01
 * To change this template use File | Settings | File Templates.
 */
public class DeviceRegisterRequest extends BaseRequest{

    /**
     * 设备的唯一标识,由NODE解析生成(mac地址)
     */
    private String symbol;

    /**
     * 设备所在的PI设备
     */
    private Long PIID;

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

    /**
     * Gets symbol.
     *
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets symbol.
     *
     * @param symbol the symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets piid.
     *
     * @return the piid
     */
    public Long getPIID() {
        return PIID;
    }

    /**
     * Sets piid.
     *
     * @param PIID the piid
     */
    public void setPIID(Long PIID) {
        this.PIID = PIID;
    }

    /**
     * Gets device type.
     *
     * @return the device type
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * Sets device type.
     *
     * @param deviceType the device type
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * Gets mac address.
     *
     * @return the mac address
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Sets mac address.
     *
     * @param macAddress the mac address
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }


    /**
     * Gets short address.
     *
     * @return the short address
     */
    public int getShortAddress() {
        return shortAddress;
    }

    /**
     * Sets short address.
     *
     * @param shortAddress the short address
     */
    public void setShortAddress(int shortAddress) {
        this.shortAddress = shortAddress;
    }

    /**
     * Gets index.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets index.
     *
     * @param index the index
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
