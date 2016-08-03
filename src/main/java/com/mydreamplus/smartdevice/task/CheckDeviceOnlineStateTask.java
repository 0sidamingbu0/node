package com.mydreamplus.smartdevice.task;

import com.mydreamplus.smartdevice.dao.jpa.DeviceRepository;
import com.mydreamplus.smartdevice.domain.DeviceStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 检查设备的离线状态任务
 */
@Component("task")
public class CheckDeviceOnlineStateTask {

    // 一个小时
    private static final long ONE_HOUR = 1000 * 60 * 60;
    private static final long ONE_MINUTE = 1000 * 60;

    private final Logger log = LoggerFactory.getLogger(CheckDeviceOnlineStateTask.class);
    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * 60分钟执行一次
     */
    @Scheduled(fixedRate = ONE_HOUR, initialDelay = 1000)
    public void run() {
        // 一小时前的时间
        Date date = new Date(System.currentTimeMillis() - ONE_HOUR);
        this.deviceRepository.updateOfflineState(DeviceStateEnum.OFFLINE, date);
//        this.deviceRepository.findAllByUpdateTimeLessThan(date).forEach(device -> {
//            log.info("更新设备{} {}为离线状态", device.getName(), device.getSymbol());
//            device.setDeviceState(DeviceStateEnum.OFFLINE);
//            this.deviceRepository.save(device);
//        });
    }

}  