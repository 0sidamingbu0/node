package com.mydreamplus.smartdevice.domain.in;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 * 传感器上报的数据
 */
public class SensorValueRequest extends BaseDeviceRequest {

    /**
     * symbol
     */
    private String symbol;


    /**
     * 数据内容
     */
    private Map<String, String> value;

    /**
     * Gets value.
     *
     * @return the value
     */
    public Map<String, String> getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(Map<String, String> value) {
        this.value = value;
    }

    /**
     * Gets symbol.
     *
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets symbol.
     *
     * @param symbol the symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
