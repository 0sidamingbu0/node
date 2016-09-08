package com.mydreamplus.smartdevice.domain;


import com.mydreamplus.smartdevice.entity.DeviceGroup;
import com.mydreamplus.smartdevice.entity.SensorData;
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
public class DeviceInfoDto {

    private Long ID;
    /**
     * 拆分前的设 类
     */
    private String parentDeviceType;
    /**
     * 设 的唯一标识,由NODE解析生成(mac  )
     */
    private String symbol;
    /**
     * 设 所 的PI设  PI的mac
     */
    private String PIID;
    /**
     * 设 的名称
     */
    private String name;
    /**
     * 设 的别名, 云端设置
     */
    private String aliases;
    /**
     * 设 的描述信息
     */
    private String description;

    private int level;
    /**
     * 设 的类
     */
    private String deviceType;
    private String factory;
    private int linkQuality;
    /**
     * 设 的配置信息
     */
    private String additionalAttributes;
    /**
     * 设 状态
     */
    private DeviceStateEnum deviceState;
    /**
     * 设 的状况, 开启/关闭
     */
    private DeviceSituationEnum deviceSituation;
    /**
     * 设 的注册时间
     */
    private Date registeTime;
    /**
     * 传感 数据
     */
    private List<SensorData> sensorDatas;
    /**
     * 设 分组
     */
    private List<DeviceGroup> deviceGroupList;
    /**
     * Instantiates a new Device dto.
     */
    public DeviceInfoDto() {
    }
    /**
     * Instantiates a new Device dto.
     *
     * @param symbol the mac address
     */
    public DeviceInfoDto(String symbol) {
        this.symbol = symbol;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(String additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public int getLinkQuality() {
        return linkQuality;
    }

    public void setLinkQuality(int linkQuality) {
        this.linkQuality = linkQuality;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public List<SensorData> getSensorDatas() {
        return sensorDatas;
    }

    public void setSensorDatas(List<SensorData> sensorDatas) {
        this.sensorDatas = sensorDatas;
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
