package com.mydreamplus.smartdevice.domain.in;

import com.mydreamplus.smartdevice.domain.PageDto;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午12:20
 * To change this template use File | Settings | File Templates.
 * 设 查询vo
 */
public class DeviceQueryRequest extends BaseRequest {

    /**
     * 分页信息
     */
    private PageDto pageDto;


    /**
     * 设 的状态
     */
    private String state;
    /**
     * 设 类
     */
    private String deviceType;
    /**
     * 是否注册
     */
    private boolean isRegistered;

    /**
     * 搜索字段
     */
    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    /**
     * Gets page dto.
     *
     * @return the page dto
     */
    public PageDto getPageDto() {
        return pageDto;
    }

    /**
     * Sets page dto.
     *
     * @param pageDto the page dto
     */
    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }


    /**
     * Gets device type.
     *
     * @return the device type
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * Sets device type.
     *
     * @param deviceType the device type
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}

