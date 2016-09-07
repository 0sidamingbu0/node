package com.mydreamplus.smartdevice.domain.in;

import com.mydreamplus.smartdevice.domain.PolicyConfigDto;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/14
 * Time: 下午3:01
 * To change this template use File | Settings | File Templates.
 */
public class DevicePolicyRequest extends BaseRequest {

    /**
     * 场景名称
     */
    private String name;

    /**
     * 场景的描述
     */
    private String description;

    private PolicyConfigDto policyConfigDto;

    private Long groupId;
    private boolean defaultPolicy;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PolicyConfigDto getPolicyConfigDto() {
        return policyConfigDto;
    }

    public void setPolicyConfigDto(PolicyConfigDto policyConfigDto) {
        this.policyConfigDto = policyConfigDto;
    }
}
