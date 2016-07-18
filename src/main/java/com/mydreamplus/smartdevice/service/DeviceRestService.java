package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.domain.out.DeviceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午4:22
 * To change this template use File | Settings | File Templates.
 */

@Service
public class DeviceRestService {

    private final Logger log = LoggerFactory.getLogger(DeviceRestService.class);

    @Async(value = "messageExecutor")
    public void registerFeedback(DeviceMessage message) {
        log.info("register feedback!");
    }

}
