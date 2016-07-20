package com.mydreamplus.smartdevice.domain.in;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/14
 * Time: 下午3:01
 * To change this template use File | Settings | File Templates.
 */
public class DevicePolicyRequest extends BaseRequest {

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
    private Long rootPolicy;

    /**
     * 主控设备触发事件
     * Key:设备的symbol , value ,事件name
     */
    private Map<String, String> masterDeviceMap;

    /**
     * 被控设备事件方法
     * Key:设备的symbol , value ,方法name
     */
    private Map<String, String> slaveDeviceMap;

    /**
     * 策略之间有控制关系, key 策略控制 value  策略
     */
    private Map<Long, Long> policyMap;

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

    public Long getRootPolicy() {
        return rootPolicy;
    }

    public void setRootPolicy(Long rootPolicy) {
        this.rootPolicy = rootPolicy;
    }

    public Map<Long, Long> getPolicyMap() {
        return policyMap;
    }

    public void setPolicyMap(Map<Long, Long> policyMap) {
        this.policyMap = policyMap;
    }

    public Map<String, String> getMasterDeviceMap() {
        return masterDeviceMap;
    }

    public void setMasterDeviceMap(Map<String, String> masterDeviceMap) {
        this.masterDeviceMap = masterDeviceMap;
    }

    public Map<String, String> getSlaveDeviceMap() {
        return slaveDeviceMap;
    }

    public void setSlaveDeviceMap(Map<String, String> slaveDeviceMap) {
        this.slaveDeviceMap = slaveDeviceMap;
    }

    @Override
    public String toString() {
        return "DevicePolicyRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rootPolicy=" + rootPolicy +
                ", masterDeviceMap=" + masterDeviceMap +
                ", slaveDeviceMap=" + slaveDeviceMap +
                ", policyMap=" + policyMap +
                '}';
    }
}
