package com.mydreamplus.smartdevice.entity;

import com.mydreamplus.smartdevice.domain.DeviceSituationEnum;
import com.mydreamplus.smartdevice.domain.DeviceStateEnum;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "zb_device")
public class Device extends BaseEntity{

    /**
     * 拆分前的设备类型
     */
    @Column
    private String parentDeviceType;
    /**
     * 设备的唯一标识,由NODE解析生成(mac地址)
     */
    @Column(unique = true)
    private String symbol;
    /**
     * 设备所在的PI设备
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pi_id")
    private PI pi;
    /**
     * 设备的名称
     */
    @Column(unique = true, name = "device_name")
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_type_id")
    private DeviceType deviceType;
    /**
     * 设备的MAC地址
     */
    @Column
    private String macAddress;
    /**
     * 设备状态
     */
    @Enumerated(EnumType.STRING)
    private DeviceStateEnum deviceState;
    /**
     * 设备的状况, 开启/关闭
     */
    @Enumerated(EnumType.STRING)
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
     * Gets pi.
     *
     * @return the pi
     */
    public PI getPi() {
        return pi;
    }

    /**
     * Sets pi.
     *
     * @param pi the pi
     */
    public void setPi(PI pi) {
        this.pi = pi;
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
    public DeviceType getDeviceType() {
        return deviceType;
    }

    /**
     * Sets device type.
     *
     * @param deviceType the device type
     */
    public void setDeviceType(DeviceType deviceType) {
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

    public String getParentDeviceType() {
        return parentDeviceType;
    }

    public void setParentDeviceType(String parentDeviceType) {
        this.parentDeviceType = parentDeviceType;
    }
}
