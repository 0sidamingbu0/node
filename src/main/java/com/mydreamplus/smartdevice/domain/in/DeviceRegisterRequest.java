package com.mydreamplus.smartdevice.domain.in;

import com.mydreamplus.smartdevice.domain.DeviceRegisterDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/12
 * Time: 下午8:01
 * To change this template use File | Settings | File Templates.
 */
public class DeviceRegisterRequest extends BaseRequest {

    /**
     * 拆分前的设备类型
     */
    private String parentDeviceType;

    /**
     * 注册设备信息
     */
    private List<DeviceRegisterDto> deviceRegisters;

    /**
     * Gets device registers.
     *
     * @return the device registers
     */
    public List<DeviceRegisterDto> getDeviceRegisters() {
        return deviceRegisters;
    }

    /**
     * Sets device registers.
     *
     * @param deviceRegisters the device registers
     */
    public void setDeviceRegisters(List<DeviceRegisterDto> deviceRegisters) {
        this.deviceRegisters = deviceRegisters;
    }

    public String getParentDeviceType() {
        return parentDeviceType;
    }

    public void setParentDeviceType(String parentDeviceType) {
        this.parentDeviceType = parentDeviceType;
    }
}
