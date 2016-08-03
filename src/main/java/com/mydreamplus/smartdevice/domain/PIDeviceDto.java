package com.mydreamplus.smartdevice.domain;

import com.mydreamplus.smartdevice.domain.in.BaseDeviceRequest;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:49
 * To change this template use File | Settings | File Templates.
 */
public class PIDeviceDto extends BaseDeviceRequest {
    /**
     * PI的名称
     */
    private String name;

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

}
