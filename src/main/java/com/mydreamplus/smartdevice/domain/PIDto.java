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

    private Long ID;
    private Date createTime;
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
    /**
     * 允许入网的截止时间
     */
    private Date permitEndTime;
    /**
     * IP
     */
    private String ipAddress;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Date getPermitEndTime() {
        return permitEndTime;
    }

    public void setPermitEndTime(Date permitEndTime) {
        this.permitEndTime = permitEndTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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
