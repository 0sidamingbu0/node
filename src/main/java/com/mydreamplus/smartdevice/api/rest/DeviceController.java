package com.mydreamplus.smartdevice.api.rest;

import com.mydreamplus.smartdevice.config.Constant;
import com.mydreamplus.smartdevice.dao.jpa.SensorRepositoryImpl;
import com.mydreamplus.smartdevice.domain.DeviceDto;
import com.mydreamplus.smartdevice.domain.DeviceSituationEnum;
import com.mydreamplus.smartdevice.domain.PIDeviceDto;
import com.mydreamplus.smartdevice.domain.PingDto;
import com.mydreamplus.smartdevice.domain.in.*;
import com.mydreamplus.smartdevice.domain.message.PolicyMessage;
import com.mydreamplus.smartdevice.domain.out.BaseResponse;
import com.mydreamplus.smartdevice.entity.Device;
import com.mydreamplus.smartdevice.entity.SensorData;
import com.mydreamplus.smartdevice.exception.DataInvalidException;
import com.mydreamplus.smartdevice.exception.TimeOutException;
import com.mydreamplus.smartdevice.service.DeviceManager;
import com.mydreamplus.smartdevice.service.DeviceRestService;
import com.mydreamplus.smartdevice.service.DeviceService;
import com.mydreamplus.smartdevice.service.PolicyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/12
 * Time: 下午7:45
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping(value = "/device/service")
@Api(value = "设备API", description = "设备交互API,提供设备调用的API接口")
public class DeviceController extends AbstractRestHandler {

    private final Logger log = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private DeviceRestService deviceRestService;


    /**
     * Register device base response.
     *
     * @param deviceRegisterRequest the device register request
     * @return the base response
     */
    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "注册设备")
    @Transactional
    public BaseResponse registerDevice(@RequestBody DeviceRegisterRequest deviceRegisterRequest) {
        // 拆分设备类型
        List<PolicyMessage> policyMessages = new ArrayList<>();
        this.policyService.parseAndSavePolicy(deviceRegisterRequest).forEach(policyDto -> {
            PolicyMessage policyMessage = new PolicyMessage();
            policyMessage.setPolicyConfigDto(policyDto.getPolicyConfigDto());
            policyMessage.setUpdateTime(new Date().getTime());
            policyMessages.add(policyMessage);
        });
        // 将物理连接的设备放到一个分组里面
//        List<DeviceGroup> groupList = new ArrayList<>(1);
//        if(deviceRegisterRequest.getDeviceRegisters().size() > 0){
//            DeviceGroupDto group = new DeviceGroupDto();
//            group.setName(deviceRegisterRequest.getParentDeviceType());
//            group.setDescription("系统默认创建");
//            group.setMacAddress(deviceRegisterRequest.getMacAddress());
//            DeviceGroup g = deviceManager.saveGroup(group);
//            groupList.add(g);
//        }
        deviceRegisterRequest.getDeviceRegisters().forEach(deviceRegisterDto -> {
            DeviceDto deviceDto = new DeviceDto();
            BeanUtils.copyProperties(deviceRegisterDto, deviceDto);
            deviceDto.setParentDeviceType(deviceRegisterRequest.getParentDeviceType());
            deviceDto.setPIID(deviceRegisterRequest.getPiMacAddress());
            deviceDto.setSymbol(deviceRegisterRequest.getMacAddress() + "-" + deviceRegisterDto.getIndex());
            log.info(String.format(":::::设备注册, mac:%s, %s", deviceDto.getMacAddress(), deviceDto.getDeviceType()));
            this.deviceService.registerDevice(deviceDto);
        });
        // 默认允许设备自动加入网路
        if (Constant.AUTO_JOIN_IN) {
            this.deviceService.allowDeviceJoinIn(deviceRegisterRequest.getPiMacAddress(), deviceRegisterRequest.getMacAddress());
        }
        // 默认策略下发
        if (policyMessages.size() > 0) {
            this.deviceRestService.sendPolicy(deviceRegisterRequest.getPiMacAddress(), policyMessages);
        }
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * Register pi base response.
     *
     * @param request the pi device dto
     * @return the base response
     */
    @RequestMapping(value = "/pi/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "注册PI")
    public BaseResponse registerPi(@RequestBody PIRegisterRequest request) {
        if (request == null || request.getPiMacAddress() == null) {
            throw new DataInvalidException("PI的mac地址为空!");
        }
        log.info(String.format(":::::PI注册, mac:%s", request.getPiMacAddress()));
        PIDeviceDto piDeviceDto = new PIDeviceDto();
        piDeviceDto.setPiMacAddress(request.getPiMacAddress());
        this.deviceService.registerPi(piDeviceDto);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * Status base response.
     * 设备每个小时会主动上报状态,作为心跳
     *
     * @param request the request
     * @return the base response
     */
    @RequestMapping(value = "/status",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新设备状态,设备主动发送过来")
    public BaseResponse status(@RequestBody DeviceSituationRequest request) {
        log.info(":::::设备状态反馈::::::");
        request.getDeviceSituationDtos().forEach(deviceSituationDto -> {
            log.info("设备{} 状态{}", deviceSituationDto.getSymbol(), deviceSituationDto.getValue());
            DeviceDto deviceDto = new DeviceDto();
            deviceDto.setSymbol(deviceSituationDto.getSymbol());
            if (0 == deviceSituationDto.getValue()) {
                deviceDto.setDeviceSituation(DeviceSituationEnum.OFF);
            } else {
                deviceDto.setDeviceSituation(DeviceSituationEnum.ON);
            }
        });
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * Event base response.
     *
     * @param request the request
     * @return the base response
     */
    @RequestMapping(value = "/event",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "设备触发事件")
    public BaseResponse event(@RequestBody DeviceEventRequest request) {
        //记录日志
        log.info(":::::设备触发事件:{} , symbol:{}, {}, 耗时:{} 毫秒", request.getEventName(), request.getSymbol(),
                new Date(request.getEventTime()), System.currentTimeMillis() - request.getEventTime());
        // 请求的事件超时,不执行场景
        if(System.currentTimeMillis() - request.getEventTime() >= Constant.EVENT_TIME_OUT){
//            throw new TimeOutException("设备触发事件超时,不执行场景!");
            log.info("设备触发事件超时,不执行场景!");
        }else{
            if (request.getSymbol().equals("00:15:8d:00:00:fc:87:f8-1")) {
                if (request.getEventName().equals("PressDown")) {
                    deviceRestService.sendCommandToDevice(request.getPiMacAddress(), "00:12:4b:00:0a:b9:52:52-3", "On");
                }
                if (request.getEventName().equals("PressUp")) {
                    deviceRestService.sendCommandToDevice(request.getPiMacAddress(), "00:12:4b:00:0a:b9:52:52-3", "Off");
                }
                if (request.getEventName().equals("DoubleClick")) {
                    deviceRestService.sendCommandToDevice(request.getPiMacAddress(), "00:12:4b:00:0a:b9:52:52-3", "Reverse");
                    deviceRestService.sendCommandToDevice(request.getPiMacAddress(), "00:12:4b:00:0a:b9:52:52-4", "Reverse");
                }
            }
            if (request.getSymbol().equals("00:15:8d:00:01:06:c5:2f-1")) {
                if (request.getEventName().equals("PressDown")) {
                    deviceRestService.sendCommandToDevice(request.getPiMacAddress(), "00:12:4b:00:0a:b9:52:52-3", "On");
                }
                if (request.getEventName().equals("PressUp")) {
                    deviceRestService.sendCommandToDevice(request.getPiMacAddress(), "00:12:4b:00:0a:b9:52:52-3", "Off");
                }
            }
        }
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * Ping base response.
     *
     * @param request the request
     * @return the base response
     */
    @RequestMapping(value = "/ping",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "上报设备PING值")
    public BaseResponse ping(@RequestBody DevicePingRequest request) {
        log.info("========设备网络情况======== {}", request.getPing());
//        long serverPing = System.currentTimeMillis() - request.getServerTimeStamp();
//        log.info("=======设备到云端网络情况======{}", serverPing);
        deviceService.savePing(request.getMacAddress(), new PingDto(request.getPing(), new Date(request.getPing())));
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * Reset base response.
     *
     * @param macAddress the symbol
     * @return the base response
     */
    @RequestMapping(value = "/reset",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "重置设备")
    public BaseResponse reset(@PathParam(value = "macAddress") String macAddress) {
        this.deviceService.reset(macAddress);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * Gets sensor value.
     *
     * @param request the request
     * @return the sensor value
     */
    @RequestMapping(value = "/value",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取传感器测量数值")
    public BaseResponse getSensorValue(@RequestBody SensorValueRequest request) {
        log.info(String.format(":::::传感器测量数据: mac:%s, %s", request.getSymbol(), request.getValue()));
        SensorRepositoryImpl.addValue(request.getSymbol(), request.getValue());
        // 保存传感器上传数据
        request.getValue().forEach((s, o) -> {
            SensorData sensorData = new SensorData();
            sensorData.setSensorType(s);
            sensorData.setData(Double.valueOf(o));
            sensorData.setSymbol(request.getSymbol());
            this.deviceService.saveSensorData(sensorData);
        });
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    @RequestMapping(value = "/removeCallback",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除设备,设备重新注册")
    public BaseResponse removeCallback(@RequestBody DeviceMacAddrRequest request) {
        this.deviceService.callbackRemoveDevice(request.getMacAddress());
        return new BaseResponse(RESPONSE_SUCCESS);
    }


}
