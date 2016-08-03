package com.mydreamplus.smartdevice.domain;

import com.mydreamplus.smartdevice.domain.condition.BaseCondition;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/26
 * Time: 下午12:20
 * To change this template use File | Settings | File Templates.
 */
public class ConditionAndSlaveDto {

    /**
     * 满足的条件
     */
    private List<BaseCondition> conditions;

    /**
     * 被控设备事件方法
     * Key:设备的symbol , value ,方法name
     */
    private Map<String, String> slaveDeviceMap;

    public List<BaseCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<BaseCondition> conditions) {
        this.conditions = conditions;
    }

    public Map<String, String> getSlaveDeviceMap() {
        return slaveDeviceMap;
    }

    public void setSlaveDeviceMap(Map<String, String> slaveDeviceMap) {
        this.slaveDeviceMap = slaveDeviceMap;
    }
}
