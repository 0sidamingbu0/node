package com.mydreamplus.smartdevice.domain;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/19
 * Time: 下午6:30
 * To change this template use File | Settings | File Templates.
 */
public class DeviceSituationDto {

    /**
     * 设备的状态
     */
    private int value;

    /**
     * 设备标识
     */
    private String symbol;

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

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DeviceSituationDto{" +
                "value=" + value +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
