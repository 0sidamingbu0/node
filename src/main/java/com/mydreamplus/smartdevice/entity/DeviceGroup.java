package com.mydreamplus.smartdevice.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class DeviceGroup extends BaseEntity {

    /**
     * 区 名
     */
    @Column(name = "group_name")
    private String name;
    /**
     * 区 描述
     */
    private String description;


    /**
     * PI的分组
     */
    @OneToMany(mappedBy = "deviceGroup")
    private List<PI> pis;


    /**
     * Gets pis.
     *
     * @return the pis
     */
    public List<PI> getPis() {
        return pis;
    }

    /**
     * Sets pis.
     *
     * @param pis the pis
     */
    public void setPis(List<PI> pis) {
        this.pis = pis;
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

}
