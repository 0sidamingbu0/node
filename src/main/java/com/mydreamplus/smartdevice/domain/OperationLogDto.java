package com.mydreamplus.smartdevice.domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 * 管理员在云端触发的事件记录
 */
public class OperationLogDto extends BaseLogDto {

    /**
     * 操作用户ID
     */
    private Long userID;

    /**
     * 操作用户名
     */
    private String userName;

    /**
     * 事件发生的时间
     */
    private Date createAt;

    /**
     * 操作人的IP地址
     */
    private String ipAddress;

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUserID() {
        return userID;
    }

    /**
     * Sets user id.
     *
     * @param userID the user id
     */
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets create at.
     *
     * @return the create at
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * Sets create at.
     *
     * @param createAt the create at
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * Gets ip address.
     *
     * @return the ip address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Sets ip address.
     *
     * @param ipAddress the ip address
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
