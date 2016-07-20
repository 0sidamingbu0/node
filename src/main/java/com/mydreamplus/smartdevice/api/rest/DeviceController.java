package com.mydreamplus.smartdevice.api.rest;

import com.mydreamplus.smartdevice.domain.DeviceDto;
import com.mydreamplus.smartdevice.domain.DeviceSituationEnum;
import com.mydreamplus.smartdevice.domain.PIDeviceDto;
import com.mydreamplus.smartdevice.domain.PingDto;
import com.mydreamplus.smartdevice.domain.in.*;
import com.mydreamplus.smartdevice.domain.out.BaseResponse;
import com.mydreamplus.smartdevice.service.DeviceRestService;
import com.mydreamplus.smartdevice.service.DeviceService;
import com.mydreamplus.smartdevice.service.PingFactory;
import com.mydreamplus.smartdevice.service.PolicyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "注册设备")
    public BaseResponse registerDevice(@RequestBody DeviceRegisterRequest deviceRegisterRequest) {
        this.policyService.parsePolicy(deviceRegisterRequest);
        deviceRegisterRequest.getDeviceRegisters().forEach(deviceRegisterDto -> {
            DeviceDto deviceDto = new DeviceDto();
            BeanUtils.copyProperties(deviceRegisterDto, deviceDto);
            deviceDto.setParentDeviceType(deviceRegisterRequest.getParentDeviceType());
            log.info(String.format(":::::设备注册, mac:%s", deviceDto.getSymbol()));
            this.deviceService.registerDevice(deviceDto, deviceRegisterRequest.getPIID());
        });
        this.deviceRestService.registerFeedback(deviceRegisterRequest.getPIID(), deviceRegisterRequest.getShortAddress());
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    @RequestMapping(value = "/pi/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "注册PI")
    public BaseResponse registerPi(@RequestBody PIDeviceDto piDeviceDto) {
        log.info(String.format(":::::PI注册, mac:%s", piDeviceDto.getMacAddress()));
        this.deviceService.registerPI(piDeviceDto);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    @RequestMapping(value = "/status",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新设备状态,设备主动发送过来")
    public BaseResponse status(@RequestBody DeviceSituationRequest request) {
        log.info(String.format(":::::设备状态反馈:  mac:%s", request.getPiMacAddr()));
        request.getDeviceSituationDtos().forEach(deviceSituationDto -> {
            DeviceDto deviceDto = new DeviceDto();
            deviceDto.setSymbol(deviceSituationDto.getSymbol());
            if(0 == deviceSituationDto.getValue()){
                deviceDto.setDeviceSituation(DeviceSituationEnum.OFF);
            }else{
                deviceDto.setDeviceSituation(DeviceSituationEnum.ON);
            }
            this.deviceService.updateDeviceSituation(deviceDto);
        });
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    @RequestMapping(value = "/event",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "设备触发事件")
    public BaseResponse event(@RequestBody DeviceEventRequest request) {
        log.info(String.format(":::::设备触发事件:%s , mac:%s", request.getEventName(), request.getMacAddress()));
        //记录日志
        // do something
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    @RequestMapping(value = "/ping",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "上报设备PING值")
    public BaseResponse ping(@RequestBody DevicePingRequest request) {
        log.info(String.format(":::::设备网络情况: %s, mac:%s", request.getPing(), request.getMacAddress()));
        PingFactory.setPing(request.getMacAddress(), new PingDto(request.getPing(), request.getPingTime()));
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    @RequestMapping(value = "/reset",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "上报设备PING值")
    public BaseResponse reset(@RequestBody BaseDeviceRequest request) {
        log.info(String.format(":::::设备被重置, mac:%s", request.getMacAddress()));
        this.deviceService.reset(new DeviceDto(request.getMacAddress()));
        return new BaseResponse(RESPONSE_SUCCESS);
    }
}
