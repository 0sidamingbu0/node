package com.mydreamplus.smartdevice.entity;

import com.mydreamplus.smartdevice.domain.DeviceFunctionTypeEnum;
import com.mydreamplus.smartdevice.domain.DeviceSourceEnum;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 上午11:27
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class DeviceType extends BaseEntity {

    /**
     * 设备类型名称
     */
    @Column(unique = true, name = "device_type_name")
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
    @Enumerated(EnumType.STRING)
    private DeviceSourceEnum deviceSource;

    /**
     * 设备的职能分类
     */
    @Enumerated(EnumType.STRING)
    private DeviceFunctionTypeEnum deviceFunctionType;

    /**
     * 设备事件
     */
    @ManyToMany(mappedBy = "deviceTypes", cascade = CascadeType.ALL)
    private List<DeviceEvent> deviceEvents;

    /**
     * 设备可以被施放的方法
     *
     * @return the description
     */
    @ManyToMany(mappedBy = "deviceTypes", cascade = CascadeType.ALL)
    private List<DeviceFunction> deviceFunctions;


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
     * Gets device functions.
     *
     * @return the device functions
     */
    public List<DeviceFunction> getDeviceFunctions() {
        return deviceFunctions;
    }

    /**
     * Sets device functions.
     *
     * @param deviceFunctions the device functions
     */
    public void setDeviceFunctions(List<DeviceFunction> deviceFunctions) {
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
     * Gets device events.
     *
     * @return the device events
     */
    public List<DeviceEvent> getDeviceEvents() {
        return deviceEvents;
    }

    /**
     * Sets device events.
     *
     * @param deviceEvents the device events
     */
    public void setDeviceEvents(List<DeviceEvent> deviceEvents) {
        this.deviceEvents = deviceEvents;
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
}
