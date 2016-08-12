package com.mydreamplus.smartdevice.domain.condition;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/25
 * Time: 下午9:54
 * To change this template use File | Settings | File Templates.
 */
public class SensorValueCondition extends BaseCondition {

    /**
     * 设备标识
     */
    private String symbol;

    /**
     * 传感器的值
     */
    private double value;

    /**
     * 条件持续时间
     * 单位:毫秒
     */
    private long continuedTime;

    /**
     * 0 : 等于;  1: 大于;  -1: 小于
     */
    private int logic;

    public long getContinuedTime() {
        return continuedTime;
    }

    public void setContinuedTime(long continuedTime) {
        this.continuedTime = continuedTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getLogic() {
        return logic;
    }

    public void setLogic(int logic) {
        this.logic = logic;
    }
}
