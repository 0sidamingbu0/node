package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.dao.jpa.*;
import com.mydreamplus.smartdevice.domain.*;
import com.mydreamplus.smartdevice.domain.in.AndroidDeviceRequest;
import com.mydreamplus.smartdevice.domain.in.CommonDeviceRequest;
import com.mydreamplus.smartdevice.entity.Device;
import com.mydreamplus.smartdevice.entity.DeviceType;
import com.mydreamplus.smartdevice.entity.PI;
import com.mydreamplus.smartdevice.entity.SensorData;
import com.mydreamplus.smartdevice.exception.DataInvalidException;
import com.mydreamplus.smartdevice.exception.DeviceNotFoundException;
import com.mydreamplus.smartdevice.exception.DeviceTypeNotFoundException;
import com.mydreamplus.smartdevice.exception.PINotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

import static com.sun.tools.doclint.Entity.Pi;

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
    @Autowired
    private SensorDataRepository sensorDataRepository;
    @Autowired
    private WebSocketService webSocketService;


    /**
     * 注册通用设
     *
     * @param request
     */
    public void registerCommonDevice(CommonDeviceRequest request) {
        Device device = deviceRepository.findByMacAddressAndName(request.getMacAddress(), request.getDeviceType());
        if (device != null) {
            device.setRegistered(true);
            device.setRegisteTime(new Date());
            device.setUpdateTime(new Date());
            device.setDeviceState(DeviceStateEnum.ONLINE);
            this.deviceRestService.registerFeedback(request.getMacAddress());
            this.deviceRestService.sendConfigProperty(device);
            this.deviceRepository.save(device);
        } else {
            Device newDevice = new Device();
            newDevice.setMacAddress(request.getMacAddress());
            newDevice.setName(request.getDeviceType());
            DeviceType deviceType = this.deviceTypeRepository.findByName(request.getDeviceType());
            if (deviceType == null) {
                throw new DataInvalidException("没有找到设备类型!");
            }
            newDevice.setDeviceType(deviceType);
            newDevice.setDeviceState(DeviceStateEnum.ONLINE);
            newDevice.setSymbol(request.getMacAddress() + "-1");
            newDevice.setAliases(newDevice.getMacAddress() + "-" + request.getDeviceType());
            newDevice.setRegistered(false);
            newDevice.setFactory(DeviceSourceEnum.DREAMPLUS.toString());
            newDevice.setParentDeviceType(deviceType.getName());
            newDevice.setAdditionalAttributes(deviceType.getAdditionalAttributes());
            newDevice.setCreateTime(new Date());
            newDevice.setUpdateTime(new Date());
            this.deviceRepository.save(newDevice);
        }
        // 注册成功反馈
//        this.deviceRestService.registerFeedback(request.getMacAddress(), request.getMacAddress());
    }



    /**
     * Register.
     *
     * @param deviceDto the device dto
     */
    public void registerDevice(DeviceDto deviceDto) {
        Device device = deviceRepository.findBySymbol(deviceDto.getSymbol());
        // 设备第一次注册
        if (device == null) {
            device = new Device();
            BeanUtils.copyProperties(deviceDto, device, "pi", "deviceType");
            DeviceType deviceType = deviceTypeRepository.findByName(deviceDto.getDeviceType());
            if (deviceType == null) {
                throw new DeviceTypeNotFoundException(String.format("没有找到该设备类型:%s, 注册失败!", deviceDto.getDeviceType()));
            }
            device.setAdditionalAttributes(deviceType.getAdditionalAttributes());
            device.setDeviceType(deviceType);
            PI pi = piRespository.findByMacAddress(deviceDto.getPIID());
            if (pi == null) {
                throw new PINotFoundException("没有绑定PI,注册失败!");
            }
            pi.getZbDeviceList().add(device);
            device.setName(deviceType.getName());
            device.setAliases(deviceType.getAliases() + device.getSymbol());
            device.setPi(pi);
            device.setMacAddress(deviceDto.getMacAddress());
            device.setCreateTime(new Date());
            device.setUpdateTime(new Date());
            device.setDeviceState(DeviceStateEnum.UNREGISTERED);
            device.setFactory(deviceType.getDeviceSource().toString());
        } else {
            // 设备有可能注册到其他PI上
            PI pi = piRespository.findByMacAddress(deviceDto.getPIID());
            if(pi != null){
                device.setPi(pi);
            }
            device.setUpdateTime(new Date());
            // 已经注册过,更新设备上线状态
//            device.setDeviceState(DeviceStateEnum.ONLINE);
        }
        device.setRegistered(false);
        device.setRegisteTime(new Date());
        log.info(String.format("智能设备:%s | :%s,注册成功!", deviceDto.getDeviceType(), deviceDto.getSymbol()));
        deviceRepository.save(device);
    }

    /**
     * Register pi.
     * 保存PI信息
     *
     * @param piDeviceDto the pi
     */
    public void registerPi(PIDeviceDto piDeviceDto) {
        PI pi = piRespository.findByMacAddress(piDeviceDto.getPiMacAddress());
        // PI第一次注册
        if (pi == null) {
            pi = new PI();
            pi.setMacAddress(piDeviceDto.getPiMacAddress());
            pi.setName(piDeviceDto.getHostName());
            pi.setCreateTime(new Date());
        }
        pi.setIpAddress(piDeviceDto.getIpAddress());
        // 更新PI注册时间
        pi.setRegisterTime(new Date());
        pi.setUpdateTime(new Date());
        log.info(String.format("MAC地址: %s, PI注册成功!", pi.getMacAddress()));
        piRespository.save(pi);
        this.deviceRestService.registerPiFeedback(piDeviceDto.getPiMacAddress());
    }


    /**
     * Update device situation.
     *
     * @param deviceDto the device dto
     */
    public void updateDeviceSituationAndSetOnline(DeviceDto deviceDto) {
        Device device = deviceRepository.findBySymbol(deviceDto.getSymbol());
        if (device == null) {
            throw new DeviceNotFoundException(String.format("更新设备状态失败,没有找到设备,symbol:%s ", deviceDto.getSymbol()));
        }
        // 设备未注册,状态为未注册
        if (device.getDeviceState() != DeviceStateEnum.UNREGISTERED) {
            device.setDeviceSituation(deviceDto.getDeviceSituation());
            device.setDeviceState(DeviceStateEnum.ONLINE);
            device.setUpdateTime(new Date());
            deviceRepository.save(device);
        }
    }

    /**
     * Reset.
     *
     * @param macAddress the device dto
     */
    public void reset(String macAddress) {
        List<Device> devices = deviceRepository.findAllByMacAddress(macAddress);
        devices.forEach(device -> {
            webSocketService.sendMessage("时间:" + new Date() + ",设备重置:" + device.getMacAddress() + ",网关地址:" + device.getPi().getMacAddress() + ",设备类型:" + device.getName());
        });
        deviceRepository.deleteByMacAddress(macAddress);
    }


    /**
     * Remove device.
     *
     * @param piMacAddress     the pi mac address
     * @param deviceMacAddress the device mac address
     */
    public void removeDevice(String piMacAddress, String deviceMacAddress) {
//        deviceRepository.findAllByMacAddress(deviceMacAddress).forEach(device -> {
//            device.setDeviceState(DeviceStateEnum.UNREGISTERED);
//            device.setUpdateTime(new Date());
//            deviceRepository.save(device);
//        });
        log.info("----真实删除设备----");
        this.deviceRepository.deleteByMacAddress(deviceMacAddress);
        deviceRestService.removeDevice(piMacAddress, deviceMacAddress);
    }

    /**
     * 更新设备删除状态
     *
     * @param deviceMacAddress the device mac address
     */
    public void callbackRemoveDevice(String deviceMacAddress) {
        deviceRepository.findAllByMacAddress(deviceMacAddress).forEach(device -> {
            device.setDeviceState(DeviceStateEnum.UNREGISTERED);
            device.setUpdateTime(new Date());
            deviceRepository.save(device);
        });
    }


    /**
     * 允许设备入网
     *
     * @param piMacAddress     the pi mac address
     * @param deviceMacAddress the device mac address
     */
    public void allowDeviceJoinIn(String piMacAddress, String deviceMacAddress) {
        List<Device> devices = this.deviceRepository.findAllByMacAddress(deviceMacAddress);
        devices.forEach(device -> {
            device.setDeviceState(DeviceStateEnum.ONLINE);
            device.setRegisteTime(new Date());
            device.setUpdateTime(new Date());
            device.setRegistered(true);
            this.deviceRepository.save(device);
        });
        if(StringUtils.isEmpty(piMacAddress)){
            piMacAddress = deviceMacAddress;
        }
        deviceRestService.registerFeedback(piMacAddress, deviceMacAddress);
    }

    /**
     * Permit join in.
     *
     * @param piMacAddress the pi mac address
     * @param minute       the minute
     */
    public void permitJoinIn(String piMacAddress, int minute) {
        deviceRestService.permitByMinute(piMacAddress, minute);
    }

    /**
     * Save ping.
     *
     * @param macAddress the mac address
     * @param pingDto    the ping dto
     */
    public void savePing(String macAddress, PingDto pingDto) {
        PingRepositoryImpl.setPing(macAddress, pingDto);
    }


    /**
     * Save sensor data.
     *
     * @param sensorData the sensor data
     */
    @Transactional
    public void saveSensorData(SensorData sensorData) {
        sensorData.setUpdateTime(new Date());
        this.sensorDataRepository.save(sensorData);
    }

}
