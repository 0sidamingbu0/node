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
public class DeviceSituationRequest extends BaseDeviceRequest{

    /**
     * 设备状态集合
     */
    private List<DeviceSituationDto> deviceSituationDtos;

    /**
     * Gets device situation dtos.
     *
     * @return the device situation dtos
     */
    public List<DeviceSituationDto> getDeviceSituationDtos() {
        return deviceSituationDtos;
    }

    /**
     * Sets device situation dtos.
     *
     * @param deviceSituationDtos the device situation dtos
     */
    public void setDeviceSituationDtos(List<DeviceSituationDto> deviceSituationDtos) {
        this.deviceSituationDtos = deviceSituationDtos;
    }

}
