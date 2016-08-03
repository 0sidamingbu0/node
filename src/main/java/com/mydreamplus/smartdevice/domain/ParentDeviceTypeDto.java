package com.mydreamplus.smartdevice.domain;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/18
 * Time: 下午10:04
 * To change this template use File | Settings | File Templates.
 */
public class ParentDeviceTypeDto {
    /**
     * 资源数,设备上的资源对数,下发命令的是时候要指定
     */
    private int resourceSum;
    /**
     * 设备类型名称
     */
    private String name;
    /**
     * 设备类型的描述
     */
    private String description;
    /**
     * 设备的别名,例如SensorBody_MI
     * 类型 + 设备的来源
     */
    private String aliases;

    /**
     * Gets resource sum.
     *
     * @return the resource sum
     */
    public int getResourceSum() {
        return resourceSum;
    }

    /**
     * Sets resource sum.
     *
     * @param resourceSum the resource sum
     */
    public void setResourceSum(int resourceSum) {
        this.resourceSum = resourceSum;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets aliases.
     *
     * @return the aliases
     */
    public String getAliases() {
        return aliases;
    }

    /**
     * Sets aliases.
     *
     * @param aliases the aliases
     */
    public void setAliases(String aliases) {
        this.aliases = aliases;
    }
}
