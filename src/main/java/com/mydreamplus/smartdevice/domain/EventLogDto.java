package com.mydreamplus.smartdevice.domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 */
public class EventLogDto extends BaseLogDto {

    /**
     * 智能设备ID
     */
    private Long deviceID;

    /**
     * 智能设备的名称
     */
    private String deviceName;

    /**
     * 设备的描述信息
     */
    private String deviceDescription;


    /**
     * PI的ID
     */
    private String PIID;

    /**
     * PI名字
     */
    private String piName;


    /**
     * 事件发生的时间
     */
    private Date createAt;

    /**
     * 相关的策略ID
     */
    private Long policyID;

    /**
     * 策略名称
     */
    private String policyName;


    /**
     * 其他相关信息
     */
    private String message;

    /**
     * Gets device id.
     *
     * @return the device id
     */
    public Long getDeviceID() {
        return deviceID;
    }

    /**
     * Sets device id.
     *
     * @param deviceID the device id
     */
    public void setDeviceID(Long deviceID) {
        this.deviceID = deviceID;
    }

    /**
     * Gets device name.
     *
     * @return the device name
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Sets device name.
     *
     * @param deviceName the device name
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * Gets device description.
     *
     * @return the device description
     */
    public String getDeviceDescription() {
        return deviceDescription;
    }

    /**
     * Sets device description.
     *
     * @param deviceDescription the device description
     */
    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    /**
     * Gets pi name.
     *
     * @return the pi name
     */
    public String getPiName() {
        return piName;
    }

    /**
     * Sets pi name.
     *
     * @param piName the pi name
     */
    public void setPiName(String piName) {
        this.piName = piName;
    }

    /**
     * Gets create at.
     *
     * @return the create at
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * Sets create at.
     *
     * @param createAt the create at
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * Gets policy id.
     *
     * @return the policy id
     */
    public Long getPolicyID() {
        return policyID;
    }

    /**
     * Sets policy id.
     *
     * @param policyID the policy id
     */
    public void setPolicyID(Long policyID) {
        this.policyID = policyID;
    }

    /**
     * Gets policy name.
     *
     * @return the policy name
     */
    public String getPolicyName() {
        return policyName;
    }

    /**
     * Sets policy name.
     *
     * @param policyName the policy name
     */
    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public String getPIID() {
        return PIID;
    }

    public void setPIID(String PIID) {
        this.PIID = PIID;
    }
}
