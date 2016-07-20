package com.mydreamplus.smartdevice.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/19
 * Time: 下午11:22
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    @Column(insertable = true, updatable = false)
    private Date createTime;

    @Column(insertable = false, updatable = true)
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @PrePersist
    void onCreate() {
        this.setCreateTime(new Date());
    }

    @PreUpdate
    void onUpdate(){
        this.setUpdateTime(new Date());
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
