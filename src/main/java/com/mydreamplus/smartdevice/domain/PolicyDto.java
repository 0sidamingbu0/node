package com.mydreamplus.smartdevice.domain;

import com.mydreamplus.smartdevice.entity.PI;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:49
 * To change this template use File | Settings | File Templates.
 */
public class PolicyDto {

    private Long ID;
    /**
     * 场景名称
     */
    private String name;

    /**
     * 场景的描述
     */
    private String description;

    private PI pi;
    /**
     * 场景配置
     */
    private PolicyConfigDto policyConfigDto;

    private boolean defaultPolicy;
    /**
     * 策略的更新时间,时间戳,在PI上根据该字段来判断是否更新策略
     */
    private Long updateTime;
    private Long groupId;
    private String groupName;

    public boolean isDefaultPolicy() {
        return defaultPolicy;
    }

    public void setDefaultPolicy(boolean defaultPolicy) {
        this.defaultPolicy = defaultPolicy;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getID() {
        return ID;
    }

    /**
     * Sets id.
     *
     * @param ID the id
     */
    public void setID(Long ID) {
        this.ID = ID;
    }

    /**
     * 云端场景策略
     *//*
    private RootPolicy rootPolicy;

    *//**
     * 关联了云端策略,策略下发到终端上,
     * 终端根据该属性判断是否要提交到云端执行操作。
     *//*
    private boolean hasRootPolicy;*/

    /**
     * 主控设备触发事件
     * Key:设备的symbol
     */
    /*private Map<String, String> masterDeviceMap;

    *//**
     * 被控设备事件方法
     *//*
    private Map<String, String> slaveDeviceMap;

    */

    /**
     * 策略之间有控制关系, key 策略控制 value  策略
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
     * Gets pi.
     *
     * @return the pi
     */
    public PI getPi() {
        return pi;
    }

    /**
     * Sets pi.
     *
     * @param pi the pi
     */
    public void setPi(PI pi) {
        this.pi = pi;
    }

    /**
     * Gets policy config dto.
     *
     * @return the policy config dto
     */
    public PolicyConfigDto getPolicyConfigDto() {
        return policyConfigDto;
    }

    /**
     * Sets policy config dto.
     *
     * @param policyConfigDto the policy config dto
     */
    public void setPolicyConfigDto(PolicyConfigDto policyConfigDto) {
        this.policyConfigDto = policyConfigDto;
    }

    /**
     * Gets update time.
     *
     * @return the update time
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets update time.
     *
     * @param updateTime the update time
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
