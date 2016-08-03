package com.mydreamplus.smartdevice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 上午11:27
 * To change this template use File | Settings | File Templates.
 * 拆分前的设备类型
 */
@Entity
public class ParentDeviceType extends BaseEntity {


    /**
     * 资源数,设备上的资源对数,下发命令的是时候要指定
     */
    private int resourceSum;
    /**
     * 设备类型名称
     */
    @Column(unique = true, name = "parent_type_name")
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
     * 拆分过后的子设备类型
     */
    @OneToMany(mappedBy = "parentDeviceType")
    private List<DeviceType> deviceTypes;

    /**
     * Gets resource sum.
     *
     * @return the resource sum
     */
    public int getResourceSum() {
        return resourceSum;
    }

    /**
     * Sets resource sum.
     *
     * @param resourceSum the resource sum
     */
    public void setResourceSum(int resourceSum) {
        this.resourceSum = resourceSum;
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
