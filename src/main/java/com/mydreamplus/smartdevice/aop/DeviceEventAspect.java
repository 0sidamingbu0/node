package com.mydreamplus.smartdevice.aop;

import com.mydreamplus.smartdevice.dao.jpa.LogRepository;
import com.mydreamplus.smartdevice.domain.DeviceDto;
import com.mydreamplus.smartdevice.domain.EventLogDto;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/17
 * Time: 上午12:38
 * To change this template use File | Settings | File Templates.
 */
@Aspect
@Component(value = "deviceEventAspect")
public class DeviceEventAspect {

    private final Logger log = LoggerFactory.getLogger(DeviceEventAspect.class);


    @Autowired
    private LogRepository logRepository;


    /**
     * 记录设备产生的时间信息
     * @param deviceDto
     * @throws Throwable
     */
    @Before("execution(public * com.mydreamplus.smartdevice.service.DeviceService.*(..)) &&" + "args(deviceDto,..)")
    public void fillCreateInformation(DeviceDto deviceDto) throws Throwable {

        log.info(":::::AOP Before deviceService call:::::" + deviceDto);
        if (deviceDto != null) {
            EventLogDto eventLogDto = new EventLogDto();
            eventLogDto.setCreateAt(new Date());
            eventLogDto.setPIID(deviceDto.getPIID());
            eventLogDto.setDeviceName(deviceDto.getSymbol());
            logRepository.saveEventLog(eventLogDto);
        }
    }
}
