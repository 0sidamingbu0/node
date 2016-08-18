package com.mydreamplus.smartdevice.domain;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 */
public class AndroidTVConfigDto {

    private String defaultUrl;

    private String customerUrl;

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public String getCustomerUrl() {
        return customerUrl;
    }

    public void setCustomerUrl(String customerUrl) {
        this.customerUrl = customerUrl;
    }
}
