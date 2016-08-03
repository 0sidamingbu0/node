package com.mydreamplus.smartdevice.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "pi_device")
public class PI extends BaseEntity {

    /**
     * PI的名称
     */
    @Column(unique = true, name = "pi_name")
    private String name;
    /**
     * PI的mac地址
     */
    private String macAddress;
    /**
     * PI的描述信息
     */
    private String description;
    /**
     * 连接到这个PI上的ZIGBEE设备
     */
    @OneToMany(mappedBy = "pi")
    private List<Device> zbDeviceList;
    /**
     * 下发的策略
     */
    @OneToMany(mappedBy = "pi")
    private List<Policy> policies;
    /**
     * 最近一次注册时间
     */
    private Date registerTime;


    /**
     * Gets register time.
     *
     * @return the register time
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * Sets register time.
     *
     * @param registerTime the register time
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * Gets policies.
     *
     * @return the policies
     */
    public List<Policy> getPolicies() {
        return policies;
    }

    /**
     * Sets policies.
     *
     * @param policies the policies
     */
    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
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
     * Gets zb device list.
     *
     * @return the zb device list
     */
    public List<Device> getZbDeviceList() {
        return zbDeviceList;
    }

    /**
     * Sets zb device list.
     *
     * @param zbDeviceList the zb device list
     */
    public void setZbDeviceList(List<Device> zbDeviceList) {
        this.zbDeviceList = zbDeviceList;
    }
}
