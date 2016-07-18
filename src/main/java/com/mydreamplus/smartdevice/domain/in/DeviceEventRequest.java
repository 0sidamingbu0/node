package com.mydreamplus.smartdevice.domain.in;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
public class DeviceEventRequest extends BaseDeviceRequest {

    /**
     * 资源序号
     */
    private String indaddressex;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * Gets indaddressex.
     *
     * @return the indaddressex
     */
    public String getIndaddressex() {
        return indaddressex;
    }

    /**
     * Sets indaddressex.
     *
     * @param indaddressex the indaddressex
     */
    public void setIndaddressex(String indaddressex) {
        this.indaddressex = indaddressex;
    }

    /**
     * Gets event name.
     *
     * @return the event name
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets event name.
     *
     * @param eventName the event name
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}

