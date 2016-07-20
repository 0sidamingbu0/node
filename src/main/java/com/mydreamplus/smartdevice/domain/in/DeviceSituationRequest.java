package com.mydreamplus.smartdevice.domain.in;

import com.mydreamplus.smartdevice.domain.DeviceSituationDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午12:04
 * To change this template use File | Settings | File Templates.
 */
public class DeviceSituationRequest{

    /**
     * PI MAC 地址
     */
    private String piMacAddr;

    /**
     * 设备状态集合
     */
    private List<DeviceSituationDto> deviceSituationDtos;

    public List<DeviceSituationDto> getDeviceSituationDtos() {
        return deviceSituationDtos;
    }

    public void setDeviceSituationDtos(List<DeviceSituationDto> deviceSituationDtos) {
        this.deviceSituationDtos = deviceSituationDtos;
    }

    public String getPiMacAddr() {
        return piMacAddr;
    }

    public void setPiMacAddr(String piMacAddr) {
        this.piMacAddr = piMacAddr;
    }


}
