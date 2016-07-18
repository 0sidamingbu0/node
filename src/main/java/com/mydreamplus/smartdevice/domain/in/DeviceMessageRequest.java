package com.mydreamplus.smartdevice.domain.in;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
public class DeviceMessageRequest {

    /**
     * PI 的处理Action
     */
    private String action;

    /**
     * 数据参数内容
     */
    private Object data;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
