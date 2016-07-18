package com.mydreamplus.smartdevice.domain;

import com.mydreamplus.smartdevice.entity.Policy;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:49
 * To change this template use File | Settings | File Templates.
 */
public class RootPolicyDto {

    /**
     * 根策略ID
     */
    private Long ID;

    /**
     * 策略名称
     */
    private String name;

    /**
     * 策略的描述信息
     */
    private String description;

    /**
     * 与根策略相关联的子策略
     */
    private List<Policy> policies;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
}
