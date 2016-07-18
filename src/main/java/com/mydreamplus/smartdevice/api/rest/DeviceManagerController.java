package com.mydreamplus.smartdevice.api.rest;

import com.mydreamplus.smartdevice.domain.*;
import com.mydreamplus.smartdevice.domain.in.BaseRequest;
import com.mydreamplus.smartdevice.domain.in.DevicePolicyRequest;
import com.mydreamplus.smartdevice.domain.in.DeviceQueryRequest;
import com.mydreamplus.smartdevice.domain.out.BaseResponse;
import com.mydreamplus.smartdevice.entity.DeviceEvent;
import com.mydreamplus.smartdevice.entity.DeviceFunction;
import com.mydreamplus.smartdevice.entity.DeviceType;
import com.mydreamplus.smartdevice.service.DeviceManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午4:45
 * To change this template use File | Settings | File Templates.
 * 设备管理API,提供给web页面管理设备
 */

@RestController
@RequestMapping(value = "/device/manager")
@Api(value = "设备管理API", description = "设备管理API,提供设备管理接口,web页面调用")
public class DeviceManagerController extends AbstractRestHandler {

    private final Logger log = LoggerFactory.getLogger(DeviceManagerController.class);

    @Autowired
    private DeviceManager deviceManager;

    /**
     * Create device event.
     *
     * @param deviceEventDto the device event dto
     */
    @RequestMapping(value = "/deviceEvent/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "创建设备事件", notes = "")
    public BaseResponse createDeviceEvent(@RequestBody DeviceEventDto deviceEventDto) {
        DeviceEvent deviceEvent = new DeviceEvent();
        BeanUtils.copyProperties(deviceEventDto, deviceEvent);
        this.deviceManager.saveDeviceEvent(deviceEvent);
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * Create device function.
     *
     * @param deviceFunctionDto the device function dto
     */
    @RequestMapping(value = "/deviceFunction/create",
            method = {RequestMethod.POST},
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "创建设备被操作方法", notes = "")
    public BaseResponse createDeviceFunction(@RequestBody DeviceFunctionDto deviceFunctionDto) {
        DeviceFunction deviceFunction = new DeviceFunction();
        BeanUtils.copyProperties(deviceFunctionDto, deviceFunction);
        this.deviceManager.saveDeviceFunction(deviceFunction);
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * Create device type.
     *
     * @param deviceTypeDto the device type dto
     */
    @RequestMapping(value = "/deviceType/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "创建设备类型")
    public BaseResponse createDeviceType(@RequestBody DeviceTypeDto deviceTypeDto) {
        DeviceType deviceType = new DeviceType();
        BeanUtils.copyProperties(deviceTypeDto, deviceType, "deviceEvents", "deviceFunctions");
        List<DeviceEvent> deviceEventList = new ArrayList<>();
        deviceTypeDto.getDeviceEvents().stream().forEach(aLong -> {
            DeviceEvent event = deviceManager.findDeviceEventByID(aLong);
            if (event != null) {
                deviceEventList.add(event);
                event.getDeviceTypes().add(deviceType);
            }
        });
        List<DeviceFunction> deviceFunctionList = new ArrayList<>();
        deviceTypeDto.getDeviceFunctions().stream().forEach(aLong -> {
            DeviceFunction function = deviceManager.findDeviceFunctionByID(aLong);
            if (function != null) {
                deviceFunctionList.add(function);
                function.getDeviceTypes().add(deviceType);
            }
        });
        deviceType.setDeviceFunctions(deviceFunctionList);
        deviceType.setDeviceEvents(deviceEventList);
        this.deviceManager.saveDeviceType(deviceType);
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * Find all device types.
     *
     * @return the base response
     */
    @RequestMapping(value = "/deviceType/findAll",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询设备类型", notes = "全部")
    public BaseResponse findAllDeviceTypes() {
        BaseResponse response = new BaseResponse();
        Iterable<DeviceType> deviceTypes = deviceManager.findALLDeviceTypes();
        List<DeviceTypeDto> deviceTypeDtos = new ArrayList<>();
        deviceTypes.forEach(deviceType -> {
            log.info(deviceType.getName());
            DeviceTypeDto dto = new DeviceTypeDto();
            BeanUtils.copyProperties(deviceType, dto, "deviceEvents", "deviceFunctions");
            List<Long> eventList = new ArrayList<>();
            deviceType.getDeviceEvents().stream().forEach(deviceEvent -> eventList.add(deviceEvent.getID()));
            dto.setDeviceEvents(eventList);
            List<Long> functionList = new ArrayList<>();
            deviceType.getDeviceFunctions().stream().forEach(deviceFunction -> functionList.add(deviceFunction.getID()));
            dto.setDeviceFunctions(functionList);
            deviceTypeDtos.add(dto);
        });
        response.setMessage(RESPONSE_SUCCESS);
        response.setData(deviceTypeDtos);
        return response;
    }


    @RequestMapping(value = "/device/findAll",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "查询全部设备", notes = "根据条件查询")
    public List<DeviceDto> findAllDevices(@RequestBody DeviceQueryRequest request) {
        List<DeviceDto> deviceDtos = new ArrayList<>();
        this.deviceManager.findAllDevicesByPredicate(request, request.getPageDto()).forEach(device -> {
            DeviceDto deviceDto = new DeviceDto();
            BeanUtils.copyProperties(device, deviceDto);
            deviceDtos.add(deviceDto);
        });
        return deviceDtos;
    }


    @RequestMapping(value = "/policy/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "创建场景策略")
    public BaseResponse createPolicy(@RequestBody DevicePolicyRequest request) {
        log.info(request.toString());
        BaseResponse response = new BaseResponse();
        PolicyDto policyDto = new PolicyDto();
        BeanUtils.copyProperties(request, policyDto, "masterDeviceMap", "slaveDeviceMap", "policyMap");
        // convert device event
        /*Map<Device, DeviceEvent> masterDeviceMap = new HashMap<>();
        Map<Long, Long> masterLongMap = request.getMasterDeviceMap();
        masterLongMap.forEach((k, v) -> {
            Device device = new Device();
            device.setID(k);
            DeviceEvent event = new DeviceEvent();
            event.setID(v);
            masterDeviceMap.put(device, event);
        });
        policyDto.setMasterDeviceMap(masterDeviceMap);
        // convert device function
        Map<Device, DeviceFunction> slaveDeviceMap = new HashMap<>();
        Map<Long, Long> slaveLongMap = new HashMap<>();
        slaveLongMap.forEach((k, v) ->{
            Device device = new Device();
            device.setID(k);
            DeviceFunction function = new DeviceFunction();
            function.setID(v);
            slaveDeviceMap.put(device, function);
        });
        policyDto.setSlaveDeviceMap(slaveDeviceMap);*/

        this.deviceManager.savePolicy(policyDto);
        return response;
    }


    @RequestMapping(value = "/test",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "test")
    public BaseResponse test() {
        BaseResponse response = new BaseResponse();
        return response;
    }
}
