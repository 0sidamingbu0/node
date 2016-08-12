package com.mydreamplus.smartdevice.domain.in;

import com.mydreamplus.smartdevice.domain.DeviceSituationEnum;
import com.mydreamplus.smartdevice.domain.DeviceStateEnum;
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
     * 设 的mac
     */
    private String macAddress;

    /**
     * 设 运行状况
     */
    private DeviceSituationEnum situation;

    /**
     * 设 的状态
     */
    private DeviceStateEnum state;

    /**
     * 设 别名
     */
    private String aliases;

    /**
     * 设 类
     */
    private String deviceType;

    /**
     * 是否注册
     */
    private boolean isRegistered;

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
     * Gets mac address.
     *
     * @return the mac address
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Sets mac address.
     *
     * @param macAddress the mac address
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * Gets situation.
     *
     * @return the situation
     */
    public DeviceSituationEnum getSituation() {
        return situation;
    }

    /**
     * Sets situation.
     *
     * @param situation the situation
     */
    public void setSituation(DeviceSituationEnum situation) {
        this.situation = situation;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public DeviceStateEnum getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(DeviceStateEnum state) {
        this.state = state;
    }

    /**
     * Gets aliases.
     *
     * @return the aliases
     */
    public String getAliases() {
        return aliases;
    }

    /**
     * Sets aliases.
     *
     * @param aliases the aliases
     */
    public void setAliases(String aliases) {
        this.aliases = aliases;
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

