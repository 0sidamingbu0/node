package com.mydreamplus.smartdevice.domain;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:48
 * To change this template use File | Settings | File Templates.
 */
public class DeviceInfo {

    private String symbol;

    /**
     * 设备的别名,在云端设置
     */
    private String aliases;
    /**
     * 设备的描述信息
     */
    private String description;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
