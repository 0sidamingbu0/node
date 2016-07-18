package com.mydreamplus.smartdevice.domain.in;

import com.mydreamplus.smartdevice.domain.DeviceSituationEnum;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午12:04
 * To change this template use File | Settings | File Templates.
 */
public class DeviceSituationRequest extends BaseDeviceRequest {

    /**
     * 设备的运行状态
     */
    private DeviceSituationEnum situation;

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
}
