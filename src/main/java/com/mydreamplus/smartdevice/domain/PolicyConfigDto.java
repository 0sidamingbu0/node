package com.mydreamplus.smartdevice.domain;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/19
 * Time: 上午10:37
 * To change this template use File | Settings | File Templates.
 */
public class PolicyConfigDto {


    /**
     * 主控设备触发事件
     * Key:设备的symbol , value ,事件name
     */
    private Map<String, String> masterDeviceMap;

    /**
     * 条件和执行的操作集合
     * 满足某些条件以后干某些事情
     */
    private List<ConditionAndSlaveDto> conditionAndSlaveDtos;



    public List<ConditionAndSlaveDto> getConditionAndSlaveDtos() {
        return conditionAndSlaveDtos;
    }

    public void setConditionAndSlaveDtos(List<ConditionAndSlaveDto> conditionAndSlaveDtos) {
        this.conditionAndSlaveDtos = conditionAndSlaveDtos;
    }

    //    /**
//     * 策略之间有控制关系, key 策略控制 value  策略
//     */
//    private Map<Long, Long> policyMap;

    public Map<String, String> getMasterDeviceMap() {
        return masterDeviceMap;
    }

    public void setMasterDeviceMap(Map<String, String> masterDeviceMap) {
        this.masterDeviceMap = masterDeviceMap;
    }


//    public Map<Long, Long> getPolicyMap() {
//        return policyMap;
//    }
//
//    public void setPolicyMap(Map<Long, Long> policyMap) {
//        this.policyMap = policyMap;
//    }


    @Override
    public String toString() {
        return "PolicyConfigDto{" +
                "conditionAndSlaveDtos=" + conditionAndSlaveDtos +
                ", masterDeviceMap=" + masterDeviceMap +
                '}';
    }
}
