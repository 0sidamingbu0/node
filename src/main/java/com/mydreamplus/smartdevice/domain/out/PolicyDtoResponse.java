package com.mydreamplus.smartdevice.domain.out;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/8/1
 * Time: 下午6:25
 * To change this template use File | Settings | File Templates.
 */
public class PolicyDtoResponse {


    private Long ID;
    /**
     * 场景名称
     */
    private String name;

    /**
     * 场景的描述
     */
    private String description;

    /**
     * Pi Mac address
     */
    private String piMacAddress;
    /**
     * 场景配置
     */
    private String policyConfigDto;
    /**
     * 策略的更新时间,时间戳,在PI上根据该字段来判断是否更新策略
     */
    private Long updateTime;

    public String getPiMacAddress() {
        return piMacAddress;
    }

    public void setPiMacAddress(String piMacAddress) {
        this.piMacAddress = piMacAddress;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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


    public String getPolicyConfigDto() {
        return policyConfigDto;
    }

    public void setPolicyConfigDto(String policyConfigDto) {
        this.policyConfigDto = policyConfigDto;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
