package com.mydreamplus.smartdevice.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 上午11:34
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class DeviceFunction extends BaseEntity {

    /**
     * 设备被控方法名
     */
    @Column(unique = true, name = "function_name")
    private String name;

    /**
     * 设备方法的描述
     */
    @Column
    private String description;

    /**
     * 对该设备方法的别称
     */
    @Column
    private String alias;

    @ManyToMany
    @JoinTable(
            name = "device_type_on_device_function",
            joinColumns = @JoinColumn(name = "type_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "function_id", referencedColumnName = "ID"))
    private List<DeviceType> deviceTypes;

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
     * Gets alias.
     *
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets alias.
     *
     * @param alias the alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Gets device types.
     *
     * @return the device types
     */
    public List<DeviceType> getDeviceTypes() {
        return deviceTypes;
    }

    /**
     * Sets device types.
     *
     * @param deviceTypes the device types
     */
    public void setDeviceTypes(List<DeviceType> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }
}
