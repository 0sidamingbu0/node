package com.mydreamplus.smartdevice.domain;


import com.mydreamplus.smartdevice.entity.DeviceGroup;
import com.mydreamplus.smartdevice.util.SymbolUtil;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:50
 * To change this template use File | Settings | File Templates.
 */
public class DeviceDto {


    /**
     * 拆分前的设备类型
     */
    private String parentDeviceType;
    /**
     * 设备的唯一标识,由NODE解析生成(mac地址)
     */
    private String symbol;
    /**
     * 设备所在的PI设备 PI的mac地址
     */
    private String PIID;

    private String piName;
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
     * 厂家
     */
    private String factory;

    private int level;
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
     * 设备分组
     */
    private List<DeviceGroup> deviceGroupList;
    /**
     * 设备网络情况
     */
    private int linkQuality;
    private boolean isRegistered;
    private String deviceGroupName;

    /**
     * Instantiates a new Device dto.
     */
    public DeviceDto() {
    }

    /**
     * Instantiates a new Device dto.
     *
     * @param symbol the mac address
     */
    public DeviceDto(String symbol) {
        this.symbol = symbol;
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

    public String getPiName() {
        return piName;
    }

    public void setPiName(String piName) {
        this.piName = piName;
    }

    public int getLinkQuality() {
        return linkQuality;
    }

    public void setLinkQuality(int linkQuality) {
        this.linkQuality = linkQuality;
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

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
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
     * Gets piid.
     *
     * @return the piid
     */
    public String getPIID() {
        return PIID;
    }

    /**
     * Sets piid.
     *
     * @param PIID the piid
     */
    public void setPIID(String PIID) {
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
     * Gets mac address.
     *
     * @return the mac address
     */
    public String getMacAddress() {
        if (StringUtils.isEmpty(this.symbol)) {
            return "";
        } else {
            return SymbolUtil.parseMacAddress(this.symbol);
        }
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
                ", deviceState=" + deviceState +
                ", deviceSituation=" + deviceSituation +
                ", registeTime=" + registeTime +
                '}';
    }
}
