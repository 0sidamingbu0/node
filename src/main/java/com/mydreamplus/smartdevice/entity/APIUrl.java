package com.mydreamplus.smartdevice.entity;

import com.mydreamplus.smartdevice.domain.APIUrlTypeEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "api_url")
public class APIUrl extends BaseEntity {

    @Column(unique = true)
    private String url;
    private String name;
    private String description;

    // API的类型:传感器 上报代理、或者场景 api条件
    private APIUrlTypeEnum type;

    public APIUrlTypeEnum getType() {
        return type;
    }

    public void setType(APIUrlTypeEnum type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
