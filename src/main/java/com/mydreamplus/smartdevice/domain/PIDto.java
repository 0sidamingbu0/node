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

    /**
     * PI分组,逻辑上场地划分
     */
    private Long groupId;

    private Date createTime;
    private String name;
    private String groupName;

    private boolean isOffLine;
    /**
     * PI的mac
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

    public boolean isOffLine() {
        return isOffLine;
    }

    public void setOffLine(boolean offLine) {
        isOffLine = offLine;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

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
