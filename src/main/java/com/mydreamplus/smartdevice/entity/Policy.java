package com.mydreamplus.smartdevice.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:22
 * To change this template use File | Settings | File Templates.
 * 场景策略
 */
@Entity
public class Policy extends BaseEntity {

    /**
     * 场景名称
     */
    @Column(name = "policy_name", unique = true)
    private String name;
    /**
     * 场景的描述
     */
    @Column(name = "policy_description")
    private String description;
    /**
     * 关联的云端场景策略
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_policy_id")
    private RootPolicy rootPolicy;
    /**
     * 关联了云端策略,策略下发到终端上,
     * 终端根据该属性判断是否要提交到云端执行操作。
     */
    private boolean hasRootPolicy;
    /**
     * 主控设备触发事件
     * Key:设备的symbol ,value:设备上的event
     */
    @ElementCollection
    @MapKeyColumn(name = "device")
    @Column(name = "event")
    @CollectionTable(name = "master_device_map", joinColumns = @JoinColumn(name = "policy_id"))
    private Map<String, String> masterDeviceMap;
    /**
     * 被控设备事件方法
     * Key:设备的symbol ,value:设备上的function
     */
    @ElementCollection
    @MapKeyColumn(name = "device")
    @Column(name = "function")
    @CollectionTable(name = "slave_device_map", joinColumns = @JoinColumn(name = "policy_id"))
    private Map<String, String> slaveDeviceMap;
    /**
     * 关联的PI
     */
    @ManyToMany(mappedBy = "policies")
    private List<PI> pis;

    /**
     * Gets pis.
     *
     * @return the pis
     */
    public List<PI> getPis() {
        return pis;
    }

    /**
     * Sets pis.
     *
     * @param pis the pis
     */
    public void setPis(List<PI> pis) {
        this.pis = pis;
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
     * Gets root policy.
     *
     * @return the root policy
     */
    public RootPolicy getRootPolicy() {
        return rootPolicy;
    }

    /**
     * Sets root policy.
     *
     * @param rootPolicy the root policy
     */
    public void setRootPolicy(RootPolicy rootPolicy) {
        this.rootPolicy = rootPolicy;
    }

    /**
     * Is has root policy boolean.
     *
     * @return the boolean
     */
    public boolean isHasRootPolicy() {
        return hasRootPolicy;
    }

    /**
     * Sets has root policy.
     *
     * @param hasRootPolicy the has root policy
     */
    public void setHasRootPolicy(boolean hasRootPolicy) {
        this.hasRootPolicy = hasRootPolicy;
    }

    /**
     * Gets master device map.
     *
     * @return the master device map
     */
    public Map<String, String> getMasterDeviceMap() {
        return masterDeviceMap;
    }

    /**
     * Sets master device map.
     *
     * @param masterDeviceMap the master device map
     */
    public void setMasterDeviceMap(Map<String, String> masterDeviceMap) {
        this.masterDeviceMap = masterDeviceMap;
    }

    /**
     * Gets slave device map.
     *
     * @return the slave device map
     */
    public Map<String, String> getSlaveDeviceMap() {
        return slaveDeviceMap;
    }

    /**
     * Sets slave device map.
     *
     * @param slaveDeviceMap the slave device map
     */
    public void setSlaveDeviceMap(Map<String, String> slaveDeviceMap) {
        this.slaveDeviceMap = slaveDeviceMap;
    }
}
