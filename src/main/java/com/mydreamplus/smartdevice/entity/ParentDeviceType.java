package com.mydreamplus.smartdevice.entity;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 上午11:27
 * To change this template use File | Settings | File Templates.
 */
public class ParentDeviceType extends BaseEntity {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public List<DeviceType> getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(List<DeviceType> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }
}
