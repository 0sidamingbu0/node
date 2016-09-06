package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.domain.DeviceDto;
import com.mydreamplus.smartdevice.domain.DeviceRegisterDto;
import com.mydreamplus.smartdevice.domain.in.DeviceRegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/18
 * Time: 下午7:44
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PM25Helper {


    private final Logger log = LoggerFactory.getLogger(PM25Helper.class);
    private final String PM25_CONTROLLER = "PM2.5Controller";
    private final String PM25_SENSOR = "PM2.5Sensor";
    private final String PM10_SENSOR = "PM10Sensor";

    @Autowired
    private DeviceService deviceService;


    /**
     * Register.
     *
     * @param request the request
     */
    public void register(DeviceRegisterRequest request) {
        List<DeviceRegisterDto> deviceRegisterDtos = new ArrayList<>();
        DeviceRegisterDto pm10 = new DeviceRegisterDto();
        pm10.setDeviceType(PM10_SENSOR);
        pm10.setIndex(1);
        deviceRegisterDtos.add(pm10);
        DeviceRegisterDto pm25 = new DeviceRegisterDto();
        pm25.setDeviceType(PM25_SENSOR);
        pm25.setIndex(2);
        deviceRegisterDtos.add(pm25);
        DeviceRegisterDto pmController = new DeviceRegisterDto();
        pmController.setDeviceType(PM25_CONTROLLER);
        pmController.setIndex(3);
        deviceRegisterDtos.add(pmController);
        deviceRegisterDtos.forEach(deviceRegisterDto -> {
            DeviceDto deviceDto = new DeviceDto();
            BeanUtils.copyProperties(deviceRegisterDto, deviceDto);
            deviceDto.setParentDeviceType(request.getParentDeviceType());
            deviceDto.setPIID(request.getPiMacAddress());
            deviceDto.setSymbol(request.getMacAddress() + "-" + deviceRegisterDto.getIndex());
            log.info("注册设备:{}", deviceDto.getDeviceType());
            this.deviceService.registerDevice(deviceDto);
        });
    }

}
