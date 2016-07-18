package com.mydreamplus.smartdevice.domain;

import com.mydreamplus.smartdevice.entity.Device;
import com.mydreamplus.smartdevice.entity.DeviceEvent;
import com.mydreamplus.smartdevice.entity.DeviceFunction;
import com.mydreamplus.smartdevice.entity.RootPolicy;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:49
 * To change this template use File | Settings | File Templates.
 */
public class PolicyDto {

    /**
     * 策略的ID
     */
    private Long ID;
    /**
     * 场景名称
     */
    private String name;

    /**
     * 场景的描述
     */
    private String description;

    /**
     * 关联的云端场景策略
     */
    private RootPolicy rootPolicy;

    /**
     * 关联了云端策略,策略下发到终端上,
     * 终端根据该属性判断是否要提交到云端执行操作。
     */
    private boolean hasRootPolicy;

    /**
     * 主控设备触发事件
     * Key:设备的ID
     */
    private Map<Long, Long> masterDeviceMap;

    /**
     * 被控设备事件方法
     */
    private Map<Long, Long> slaveDeviceMap;

    /**
     * 策略之间有控制关系, key 策略控制 value  策略
     */
    private Map<Long, Long> policyMap;

    /**
     * 策略的更新时间,时间戳,在PI上根据该字段来判断是否更新策略
     */
    private Long timeStamp;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getID() {
        return ID;
    }

    /**
     * Sets id.
     *
     * @param ID the id
     */
    public void setID(Long ID) {
        this.ID = ID;
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
     * Gets time stamp.
     *
     * @return the time stamp
     */
    public Long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets time stamp.
     *
     * @param timeStamp the time stamp
     */
    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Gets master device map.
     *
     * @return the master device map
     */
    public Map<Long, Long> getMasterDeviceMap() {
        return masterDeviceMap;
    }

    /**
     * Sets master device map.
     *
     * @param masterDeviceMap the master device map
     */
    public void setMasterDeviceMap(Map<Long, Long> masterDeviceMap) {
        this.masterDeviceMap = masterDeviceMap;
    }

    /**
     * Gets slave device map.
     *
     * @return the slave device map
     */
    public Map<Long, Long> getSlaveDeviceMap() {
        return slaveDeviceMap;
    }

    /**
     * Sets slave device map.
     *
     * @param slaveDeviceMap the slave device map
     */
    public void setSlaveDeviceMap(Map<Long, Long> slaveDeviceMap) {
        this.slaveDeviceMap = slaveDeviceMap;
    }

    /**
     * Gets policy map.
     *
     * @return the policy map
     */
    public Map<Long, Long> getPolicyMap() {
        return policyMap;
    }

    /**
     * Sets policy map.
     *
     * @param policyMap the policy map
     */
    public void setPolicyMap(Map<Long, Long> policyMap) {
        this.policyMap = policyMap;
    }

    @Override
    public String toString() {
        return "PolicyDto{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rootPolicy=" + rootPolicy +
                ", hasRootPolicy=" + hasRootPolicy +
                ", masterDeviceMap=" + masterDeviceMap +
                ", slaveDeviceMap=" + slaveDeviceMap +
                ", policyMap=" + policyMap +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
