package com.mydreamplus.smartdevice.domain.condition;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/25
 * Time: 下午9:54
 * To change this template use File | Settings | File Templates.
 */
public class SwitchStatusCondition extends BaseCondition {

    /**
     * 开关类型的设备标识,标识那个设备的状态
     */
    private String symbol;

    /**
     * 设备状态、是开还是关 On , Off
     */
    private String status;

    /**
     * 条件持续事件
     * 单位:毫秒
     */
    private long continuedTime;

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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
