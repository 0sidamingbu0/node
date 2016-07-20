package com.mydreamplus.smartdevice.domain;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:48
 * To change this template use File | Settings | File Templates.
 */
public class DeviceTypeDto {
    /**
     * 设备类型名称
     */
    private String name;
    /**
     * 设备类型的描述
     */
    private String description;

    /**
     * 设备的别名,例如SensorBody_MI
     * 类型 + 设备的来源
     */
    private String aliases;

    /**
     * 设备制造的来源
     */
    private DeviceSourceEnum deviceSource;

    /**
     * 设备的职能分类
     */
    private DeviceFunctionTypeEnum deviceFunctionType;

    /**
     * 设备的事件列表
     */
    private List<Long> deviceEvents;
    /**
     * 设备的方法列表
     */
    private List<Long> deviceFunctions;

    private Long parentDeviceType;


    /**
     * Gets device functions.
     *
     * @return the device functions
     */
    public List<Long> getDeviceFunctions() {
        return deviceFunctions;
    }

    /**
     * Sets device functions.
     *
     * @param deviceFunctions the device functions
     */
    public void setDeviceFunctions(List<Long> deviceFunctions) {
        this.deviceFunctions = deviceFunctions;
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
     * Gets device source.
     *
     * @return the device source
     */
    public DeviceSourceEnum getDeviceSource() {
        return deviceSource;
    }

    /**
     * Sets device source.
     *
     * @param deviceSource the device source
     */
    public void setDeviceSource(DeviceSourceEnum deviceSource) {
        this.deviceSource = deviceSource;
    }

    /**
     * Gets device function type.
     *
     * @return the device function type
     */
    public DeviceFunctionTypeEnum getDeviceFunctionType() {
        return deviceFunctionType;
    }

    /**
     * Sets device function type.
     *
     * @param deviceFunctionType the device function type
     */
    public void setDeviceFunctionType(DeviceFunctionTypeEnum deviceFunctionType) {
        this.deviceFunctionType = deviceFunctionType;
    }

    /**
     * Gets device events.
     *
     * @return the device events
     */
    public List<Long> getDeviceEvents() {
        return deviceEvents;
    }

    /**
     * Sets device events.
     *
     * @param deviceEvents the device events
     */
    public void setDeviceEvents(List<Long> deviceEvents) {
        this.deviceEvents = deviceEvents;
    }

    /**
     * Gets parent device type.
     *
     * @return the parent device type
     */
    public Long getParentDeviceType() {
        return parentDeviceType;
    }

    /**
     * Sets parent device type.
     *
     * @param parentDeviceType the parent device type
     */
    public void setParentDeviceType(Long parentDeviceType) {
        this.parentDeviceType = parentDeviceType;
    }
}
