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
     * 设备所在的PI设备, PI的MAC地址
     */
    private String PIID;

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

    public String getParentDeviceType() {
        return parentDeviceType;
    }

    public void setParentDeviceType(String parentDeviceType) {
        this.parentDeviceType = parentDeviceType;
    }

    public String getPIID() {
        return PIID;
    }

    public void setPIID(String PIID) {
        this.PIID = PIID;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(int shortAddress) {
        this.shortAddress = shortAddress;
    }

    public List<DeviceRegisterDto> getDeviceRegisters() {
        return deviceRegisters;
    }

    public void setDeviceRegisters(List<DeviceRegisterDto> deviceRegisters) {
        this.deviceRegisters = deviceRegisters;
    }
}
