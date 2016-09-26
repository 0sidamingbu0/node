package com.mydreamplus.smartdevice.entity;

import com.mydreamplus.smartdevice.domain.DeviceSituationEnum;
import com.mydreamplus.smartdevice.domain.DeviceStateEnum;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "zb_device")
public class Device extends BaseEntity {
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pi_id")
    private PI pi;
    /**
     * 设备的名称
     */
    @Column(name = "device_name")
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
     * 设备的MAC地址用于分组
     */
    private String macAddress;

    /**
     * 设备的生成厂家
     */
    @Column
    private String factory;


    /**
     * 设备所在分组的别名
     */
    private String deviceGroupName;
    /**
     * 已经注册
     */
    private boolean isRegistered = false;

    /**
     * 代理设备产生的事件
     */
    @Column
    private String eventProxy;
    /**
     * 开关档位、1、2、3档位
     */
    private int level;
    /**
     * 设备所在组
     */
    @ManyToMany
    @JoinTable(
            name = "device_group_on_device",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "device_id", referencedColumnName = "ID"))
    private List<DeviceGroup> deviceGroupList;
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
     * 设备的类型
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_type_id")
    private DeviceType deviceType;
    /**
     * 附加的属性
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column
    private String additionalAttributes;

    public String getEventProxy() {
        return eventProxy;
    }

    public void setEventProxy(String eventProxy) {
        this.eventProxy = eventProxy;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDeviceGroupName() {
        return deviceGroupName;
    }

    public void setDeviceGroupName(String deviceGroupName) {
        this.deviceGroupName = deviceGroupName;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public String getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(String additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
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

    /**
     * Gets parent device type.
     *
     * @return the parent device type
     */
    public String getParentDeviceType() {
        return parentDeviceType;
    }

    /**
     * Sets parent device type.
     *
     * @param parentDeviceType the parent device type
     */
    public void setParentDeviceType(String parentDeviceType) {
        this.parentDeviceType = parentDeviceType;
    }

    /**
     * Gets device group list.
     *
     * @return the device group list
     */
    public List<DeviceGroup> getDeviceGroupList() {
        return deviceGroupList;
    }

    /**
     * Sets device group list.
     *
     * @param deviceGroupList the device group list
     */
    public void setDeviceGroupList(List<DeviceGroup> deviceGroupList) {
        this.deviceGroupList = deviceGroupList;
    }
}
