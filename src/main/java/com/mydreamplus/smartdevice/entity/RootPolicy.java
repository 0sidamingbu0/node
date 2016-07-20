package com.mydreamplus.smartdevice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:24
 * To change this template use File | Settings | File Templates.
 * 云端的场景策略,根策略有可能关联几个PI上的场景策略
 */
@Entity
public class RootPolicy extends BaseEntity {

    /**
     * 策略名称
     */
    @Column(unique = true, name = "root_policy_name")
    private String name;
    /**
     * 场景的描述
     */
    private String description;
    /**
     * 关联到PI上的场景策略
     */
    @OneToMany(mappedBy = "rootPolicy")
    private List<Policy> policies;

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
     * Gets policies.
     *
     * @return the policies
     */
    public List<Policy> getPolicies() {
        return policies;
    }

    /**
     * Sets policies.
     *
     * @param policies the policies
     */
    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
}
