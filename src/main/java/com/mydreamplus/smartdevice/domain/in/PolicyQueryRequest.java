package com.mydreamplus.smartdevice.domain.in;

import com.mydreamplus.smartdevice.domain.PageDto;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午12:20
 * To change this template use File | Settings | File Templates.
 * 策略查询Request
 */
public class PolicyQueryRequest extends BaseRequest {

    /**
     * 分页信息
     */
    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}

