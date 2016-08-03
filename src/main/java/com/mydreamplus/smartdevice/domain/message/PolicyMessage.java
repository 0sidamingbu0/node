package com.mydreamplus.smartdevice.domain.message;

import com.mydreamplus.smartdevice.domain.PolicyConfigDto;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/27
 * Time: 上午11:14
 * To change this template use File | Settings | File Templates.
 */
public class PolicyMessage {

    /**
     * 策略ID
     */
    private Long policyId;

    /**
     * 策略内容
     */
    private PolicyConfigDto policyConfigDto;

    /**
     * 策略的更新时间
     */
    private Long updateTime;

    /**
     * 策略被删除
     */
    private Boolean deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public PolicyConfigDto getPolicyConfigDto() {
        return policyConfigDto;
    }

    public void setPolicyConfigDto(PolicyConfigDto policyConfigDto) {
        this.policyConfigDto = policyConfigDto;
    }
}
