package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.config.Constant;
import com.mydreamplus.smartdevice.dao.jpa.DeviceRepository;
import com.mydreamplus.smartdevice.dao.jpa.DeviceTypeRepository;
import com.mydreamplus.smartdevice.dao.jpa.PIRespository;
import com.mydreamplus.smartdevice.domain.DeviceDto;
import com.mydreamplus.smartdevice.domain.DeviceStateEnum;
import com.mydreamplus.smartdevice.domain.PIDeviceDto;
import com.mydreamplus.smartdevice.domain.out.DeviceMessage;
import com.mydreamplus.smartdevice.entity.Device;
import com.mydreamplus.smartdevice.entity.DeviceType;
import com.mydreamplus.smartdevice.entity.PI;
import com.mydreamplus.smartdevice.exception.DeviceNotFoundException;
import com.mydreamplus.smartdevice.exception.DeviceTypeNotFoundException;
import com.mydreamplus.smartdevice.exception.PINotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/12
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 * 设备服务,面向设备服务的接口,提供给PI的API调用
 */
@Service
public class DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceService.class);
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private PIRespository piRespository;
    @Autowired
    private DeviceTypeRepository deviceTypeRepository;
    @Autowired
    private DeviceRestService deviceRestService;

    /**
     * Register.
     *
     * @param deviceDto the device dto
     */
    public void registerDevice(DeviceDto deviceDto) {
        Device device = deviceRepository.findByMacAddress(deviceDto.getMacAddress());
        // 设备第一次注册
        if (device == null) {
            device = new Device();
            DeviceType deviceType = deviceTypeRepository.findByName(deviceDto.getDeviceType());
            if (deviceType == null) {
                throw new DeviceTypeNotFoundException(String.format("没有找到该设备类型:%s, 注册失败!", deviceDto.getDeviceType()));
            }
            device.setDeviceType(deviceType);
            PI pi = piRespository.findOne(deviceDto.getPIID());
            if (pi == null) {
                throw new PINotFoundException("没有绑定PI,注册失败!");
            }
            pi.getZbDeviceList().add(device);
            device.setPi(pi);
            device.setCreateTime(new Date());
        }
        device.setRegisteTime(new Date());
        device.setDeviceState(DeviceStateEnum.ONLINE);
        log.info(String.format("智能设备:%s | MAC地址:%s,注册成功!", deviceDto.getDeviceType(), deviceDto.getMacAddress()));
        deviceRepository.save(device);

        deviceRestService.registerFeedback(new DeviceMessage());
    }

    /**
     * Register pi.
     *
     * @param piDeviceDto the pi
     */
    public void registerPI(PIDeviceDto piDeviceDto) {
        PI pi = piRespository.findByMacAddress(piDeviceDto.getMacAddress());
        // PI第一次注册
        if (pi == null) {
            pi = new PI();
            pi.setMacAddress(piDeviceDto.getMacAddress());
            pi.setCreateTime(new Date());
            pi.setCreateBy(Constant.DEFAULT_CREATE_USER);
        }
        pi.setRegisterTime(new Date());
        log.info(String.format("MAC地址: %s PI注册成功!", pi.getMacAddress()));
        piRespository.save(pi);
    }


    /**
     * Update device situation.
     *
     * @param deviceDto the device dto
     */
    public void updateDeviceSituation(DeviceDto deviceDto){
        Device device = deviceRepository.findByMacAddress(deviceDto.getMacAddress());
        if(device == null){
            throw new DeviceNotFoundException(String.format("没有找到设备:%s",deviceDto.getMacAddress()));
        }
        device.setDeviceSituation(deviceDto.getDeviceSituation());
        deviceRepository.save(device);
    }

    /**
     * Reset.
     *
     * @param deviceDto the device dto
     */
    public void reset(DeviceDto deviceDto) {
        Device device = deviceRepository.findByMacAddress(deviceDto.getMacAddress());
        device.setDeviceState(DeviceStateEnum.RESET);
        deviceRepository.save(device);
    }
}
