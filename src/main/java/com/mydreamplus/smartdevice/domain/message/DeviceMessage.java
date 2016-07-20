package com.mydreamplus.smartdevice.domain.message;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/19
 * Time: 下午2:37
 * To change this template use File | Settings | File Templates.
 */
public class DeviceMessage extends BaseDeviceMessage {

    /**
     * 设备的短地址
     */
    private int shortAddress;

    private String symbol;

    private Map<String, Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * Gets short address.
     *
     * @return the short address
     */
    public int getShortAddress() {
        return shortAddress;
    }

    /**
     * Sets short address.
     *
     * @param shortAddress the short address
     */
    public void setShortAddress(int shortAddress) {
        this.shortAddress = shortAddress;
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
