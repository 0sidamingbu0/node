package com.mydreamplus.smartdevice.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:22
 * To change this template use File | Settings | File Templates.
 * 场景策略
 */
@Entity
public class Policy extends BaseEntity {

    /**
     * 场景名称
     */
    @Column(name = "policy_name")
    private String name;
    /**
     * 场景的描述
     */
    @Column(name = "policy_description")
    private String description;

    /**
     * 默认的场景策略
     */
    @Column
    private boolean defaultPolicy;

    /**
     * 主控设备的触发事件
     * symbol + eventName
     */
    @Column
    private String masterEvent;

    @Column
    private Boolean deleted = false;
    /**
     * 策略的配置 存储为JSON数据格式
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column
    private String policyConfig;

    private Long groupId;

    private String groupName;
    /**
     * Sets pis.
     *
     * @param pis the pis
     *//*
    public void setPis(List<PI> pis) {
        this.pis = pis;
    }*/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pi_id")
    private PI pi;

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

    public String getMasterEvent() {
        return masterEvent;
    }

    public void setMasterEvent(String masterEvent) {
        this.masterEvent = masterEvent;
    }

    public boolean isDefaultPolicy() {
        return defaultPolicy;
    }

    /**
     * Gets pis.
     *
     * @return the pis
     *//*
    public List<PI> getPis() {
        return pis;
    }

    */
    public void setDefaultPolicy(boolean defaultPolicy) {
        this.defaultPolicy = defaultPolicy;
    }

    public Boolean getDeleted() {
        return deleted;
    }


    /**
     * 关联的云端场景策略
     */
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_policy_id")
    private RootPolicy rootPolicy;
    *//**
     * 关联了云端策略,策略下发到终端上,
     * 终端根据该属性判断是否要提交到云端执行操作。
     *//*
    private boolean hasRootPolicy;
    *//**
     * 主控设备触发事件
     * Key:设备的symbol ,value:设备上的event
     *//*
    @ElementCollection
    @MapKeyColumn(name = "device")
    @Column(name = "event")
    @CollectionTable(name = "master_device_map", joinColumns = @JoinColumn(name = "policy_id"))
    private Map<String, String> masterDeviceMap;
    *//**
     * 被控设备事件方法
     * Key:设备的symbol ,value:设备上的function
     *//*
    @ElementCollection
    @MapKeyColumn(name = "device")
    @Column(name = "function")
    @CollectionTable(name = "slave_device_map", joinColumns = @JoinColumn(name = "policy_id"))
    private Map<String, String> slaveDeviceMap;*/

    /**
     * 关联的PI
     */
    /*@ManyToMany(mappedBy = "policies")
    private List<PI> pis;

    */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public PI getPi() {
        return pi;
    }

    public void setPi(PI pi) {
        this.pi = pi;
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

    public String getPolicyConfig() {
        return policyConfig;
    }

    public void setPolicyConfig(String policyConfig) {
        this.policyConfig = policyConfig;
    }
}
