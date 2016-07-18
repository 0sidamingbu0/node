package com.mydreamplus.smartdevice.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 上午11:35
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class DeviceEvent extends BaseEntity {

    /**
     * 事件的名称
     */
    @Column(name = "event_name", nullable = false, unique = true)
    private String name;

    /**
     * 事件的别名
     */
    @Column()
    private String alias;

    /**
     * 事件的描述信息
     */
    @Column()
    private String description;


    @ManyToMany
    @JoinTable(
            name="device_type_on_device_event",
            joinColumns=@JoinColumn(name="type_id", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="event_id", referencedColumnName="ID"))
    private List<DeviceType> deviceTypes;


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
}
