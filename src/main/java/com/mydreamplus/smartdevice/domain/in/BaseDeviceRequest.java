package com.mydreamplus.smartdevice.domain.in;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 * 设备请求基础类
 */
public class BaseDeviceRequest extends BaseRequest {

    /**
     * 设备上场景策略的最后一次更新时间
     */
    private Long policyUpdateTime;

    /**
     * PI的mac地址
     */
    private String piMacAddress;

    /**
     * 设备的网络状况,ZIGBEE设备返回,小米的不能主动获取
     */
    private int linkQuality;


    /**
     * 事件发生时间
     */
    private long occurTime;

    public long getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(long occurTime) {
        this.occurTime = occurTime;
    }

    public int getLinkQuality() {
        return linkQuality;
    }

    public void setLinkQuality(int linkQuality) {
        this.linkQuality = linkQuality;
    }

    public String getPiMacAddress() {
        return piMacAddress;
    }

    public void setPiMacAddress(String piMacAddress) {
        this.piMacAddress = piMacAddress;
    }

    public Long getPolicyUpdateTime() {
        return policyUpdateTime;
    }

    public void setPolicyUpdateTime(Long policyUpdateTime) {
        this.policyUpdateTime = policyUpdateTime;
    }
}
