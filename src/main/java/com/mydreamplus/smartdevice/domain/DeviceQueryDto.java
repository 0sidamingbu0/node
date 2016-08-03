package com.mydreamplus.smartdevice.domain;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/19
 * Time: 下午6:30
 * To change this template use File | Settings | File Templates.
 */
public class DeviceQueryDto {

    /**
     * 分页信息
     */
    private PageDto pageDto;

    /**
     * 设备的状态
     */
    private DeviceStateEnum state;

    /**
     * 设备别名
     */
    private String aliases;

    /**
     * 设备类型
     */
    private String deviceType;


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
