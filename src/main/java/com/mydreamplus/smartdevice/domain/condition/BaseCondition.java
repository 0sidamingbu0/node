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

    /**
     * 条件类型ID,前端用
     */
    private String conditionTypeId;

    /**
     * 开关类型的设备标识,标识那个设备的状态
     */
    private String symbol;

    /**
     * 设备状态、是开还是关 On , Off
     */
    private String status;

    /**
     * 传感器的值
     */
    private double value;

    /**
     * 0 : 等于;  1:  于;  -1:  于
     */
    private int logic;

    private String uri = "";
    /**
     * 条件持续事件
     * 单位:毫秒
     */
    private long continuedTime;

    /**
     * 设备类型
     */
    private String deviceType;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getLogic() {
        return logic;
    }

    public void setLogic(int logic) {
        this.logic = logic;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getContinuedTime() {
        return continuedTime;
    }

    public void setContinuedTime(long continuedTime) {
        this.continuedTime = continuedTime;
    }

    public String getConditionTypeId() {
        return conditionTypeId;
    }

    public void setConditionTypeId(String conditionTypeId) {
        this.conditionTypeId = conditionTypeId;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }
}
