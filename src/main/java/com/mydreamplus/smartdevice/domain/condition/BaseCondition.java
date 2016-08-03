package com.mydreamplus.smartdevice.domain.condition;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/25
 * Time: 下午9:54
 * To change this template use File | Settings | File Templates.
 * 场景策略条件基类
 */
public class BaseCondition {

    /**
     * 条件类型
     */
    private String conditionType;

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }
}
