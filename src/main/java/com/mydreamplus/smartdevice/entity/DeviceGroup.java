package com.mydreamplus.smartdevice.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class DeviceGroup extends BaseEntity {

    /**
     * 区域名字
     */
    @Column(name = "group_name")
    private String name;
    /**
     * 区域描述
     */
    private String description;


    /**
     * 区域的设备
     */
    @ManyToMany
    @JoinTable(
            name = "device_group_on_device",
            joinColumns = @JoinColumn(name = "device_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "ID"))
    private List<Device> devices;


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
     * Gets devices.
     *
     * @return the devices
     */
    public List<Device> getDevices() {
        return devices;
    }

    /**
     * Sets devices.
     *
     * @param devices the devices
     */
    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
