package com.mydreamplus.smartdevice.domain;

import com.mydreamplus.smartdevice.entity.DeviceType;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:50
 * To change this template use File | Settings | File Templates.
 */
public class DeviceDto {

    public DeviceDto() {
    }

    public DeviceDto(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * 设备的唯一标识,由NODE解析生成(mac地址)
     */
    private String symbol;

    /**
     * 设备所在的PI设备
     */
    private Long PIID;

    /**
     * 设备的名称
     */
    private String name;

    /**
     * 设备的别名,在云端设置
     */
    private String aliases;

    /**
     * 设备的描述信息
     */
    private String description;

    /**
     * 设备的类型
     */
    private String deviceType;

    /**
     * 设备的MAC地址
     */
    private String macAddress;

    /**
     * 设备状态
     */
    private DeviceStateEnum deviceState;

    /**
     * 设备的状况, 开启/关闭
     */
    private DeviceSituationEnum deviceSituation;

    /**
     * 设备的注册时间
     */
    private Date registeTime;

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

    /**
     * Gets aliases.
     *
     * @return the aliases
     */
    public String getAliases() {
        return aliases;
    }

    /**
     * Sets aliases.
     *
     * @param aliases the aliases
     */
    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Gets device state.
     *
     * @return the device state
     */
    public DeviceStateEnum getDeviceState() {
        return deviceState;
    }

    /**
     * Sets device state.
     *
     * @param deviceState the device state
     */
    public void setDeviceState(DeviceStateEnum deviceState) {
        this.deviceState = deviceState;
    }

    /**
     * Gets device situation.
     *
     * @return the device situation
     */
    public DeviceSituationEnum getDeviceSituation() {
        return deviceSituation;
    }

    /**
     * Sets device situation.
     *
     * @param deviceSituation the device situation
     */
    public void setDeviceSituation(DeviceSituationEnum deviceSituation) {
        this.deviceSituation = deviceSituation;
    }

    /**
     * Gets registe time.
     *
     * @return the registe time
     */
    public Date getRegisteTime() {
        return registeTime;
    }

    /**
     * Sets registe time.
     *
     * @param registeTime the registe time
     */
    public void setRegisteTime(Date registeTime) {
        this.registeTime = registeTime;
    }

    @Override
    public String toString() {
        return "DeviceDto{" +
                "symbol='" + symbol + '\'' +
                ", PIID=" + PIID +
                ", name='" + name + '\'' +
                ", aliases='" + aliases + '\'' +
                ", description='" + description + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", deviceState=" + deviceState +
                ", deviceSituation=" + deviceSituation +
                ", registeTime=" + registeTime +
                '}';
    }
}
