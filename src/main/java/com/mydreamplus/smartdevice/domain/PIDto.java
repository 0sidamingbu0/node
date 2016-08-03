package com.mydreamplus.smartdevice.domain;


import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:50
 * To change this template use File | Settings | File Templates.
 */
public class PIDto {

    private String name;
    /**
     * PI的mac地址
     */
    private String macAddress;
    /**
     * PI的描述信息
     */
    private String description;

    /**
     * 最近一次注册时间
     */
    private Date registerTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
}
