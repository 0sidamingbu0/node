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
public class DeviceRegisterRequest extends BaseDeviceRequest {

    /**
     * 拆分前的设备类型
     */
    private String parentDeviceType;

    /**
     * 设备的MAC地址
     */
    private String macAddress;

    /**
     * 短地址
     */
    private int shortAddress;

    /**
     * 注册设备信息
     */
    private List<DeviceRegisterDto> deviceRegisters;

    /**
     * Gets parent device type.
     *
     * @return the parent device type
     */
    public String getParentDeviceType() {
        return parentDeviceType;
    }

    /**
     * Sets parent device type.
     *
     * @param parentDeviceType the parent device type
     */
    public void setParentDeviceType(String parentDeviceType) {
        this.parentDeviceType = parentDeviceType;
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
     * Gets short address.
     *
     * @return the short address
     */
    public int getShortAddress() {
        return shortAddress;
    }

    /**
     * Sets short address.
     *
     * @param shortAddress the short address
     */
    public void setShortAddress(int shortAddress) {
        this.shortAddress = shortAddress;
    }

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
}
