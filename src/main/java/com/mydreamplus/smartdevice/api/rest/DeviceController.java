package com.mydreamplus.smartdevice.api.rest;

import com.mydreamplus.smartdevice.config.Constant;
import com.mydreamplus.smartdevice.config.DeviceConfig;
import com.mydreamplus.smartdevice.dao.jpa.SensorRepositoryImpl;
import com.mydreamplus.smartdevice.domain.*;
import com.mydreamplus.smartdevice.domain.in.*;
import com.mydreamplus.smartdevice.domain.out.BaseResponse;
import com.mydreamplus.smartdevice.entity.Device;
import com.mydreamplus.smartdevice.entity.Policy;
import com.mydreamplus.smartdevice.entity.SensorData;
import com.mydreamplus.smartdevice.exception.DataInvalidException;
import com.mydreamplus.smartdevice.service.*;
import com.mydreamplus.smartdevice.util.JsonUtil;
import com.mydreamplus.smartdevice.util.PolicyParseUtil;
import com.mydreamplus.smartdevice.util.SymbolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private DeviceManager deviceManager;

    @Autowired
    private DoorHelper doorHelper;

    @Autowired
    private ExternalAPIService externalAPIService;

    @Autowired
    private PM25Helper pm25Helper;


    /**
     * Register pm 25 base response.
     *
     * @param request the request
     * @return the base response
     */
    @RequestMapping(value = "/pm25/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "PM2.5设备注册")
    @Transactional
    public BaseResponse registerPM25(@RequestBody DeviceRegisterRequest request) {
        pm25Helper.register(request);
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    @RequestMapping(value = "/door/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "注册门")
    @Transactional
    public BaseResponse registerDoor(@RequestBody CommonDeviceRequest commonDeviceRequest) {
        doorHelper.registerDoor(commonDeviceRequest);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    @RequestMapping(value = "/common/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "通用设备注册")
    @Transactional
    public BaseResponse registerCommonDevice(@RequestBody CommonDeviceRequest commonDeviceRequest) {
        this.deviceService.registerCommonDevice(commonDeviceRequest);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


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
        // 已经注册过
        if (deviceManager.findDevicesByMacAddress(deviceRegisterRequest.getMacAddress()).size() > 0) {
            log.info("设备已经注册{}", deviceRegisterRequest.getMacAddress());
            return new BaseResponse(RESPONSE_FAILURE);
        }
        // 拆分设备类型
        this.policyService.parseAndSavePolicy(deviceRegisterRequest);
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
        if (DeviceConfig.isAutoJoinIn()) {
            log.info("默认允许设备自动加入网络");
            this.deviceService.allowDeviceJoinIn(deviceRegisterRequest.getPiMacAddress(), deviceRegisterRequest.getMacAddress());
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
        piDeviceDto.setIpAddress(request.getPiIpAddress());
        piDeviceDto.setHostName(request.getHostName());
        this.deviceService.registerPi(piDeviceDto);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * Status base response.
     * 设 每个 时会主动上报状态,作为心跳
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
            // 更新设备状态,开关
            this.deviceService.updateDeviceSituationAndSetOnline(deviceDto);
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
        log.info("::::::::::::::::::::::::设备触发事件:{} , symbol:{}, {}, 耗时:{} 毫秒", request.getEventName(), request.getSymbol(),
                new Date(request.getEventTime()), System.currentTimeMillis() - request.getEventTime());
        // 查找默认云端场景

        Policy policy = this.policyService.findByDeviceAndEvent(request.getSymbol(), Constant.DEVICE_EVENT_REPORT_PASSWORD_OR_CARD);
        if (policy == null && request.getEventName().equals(Constant.DEVICE_EVENT_REPORT_CARD)) {
            policy = this.policyService.findByDeviceAndEvent(request.getSymbol(), Constant.DEVICE_EVENT_REPORT_CARD);
        }
        if (policy == null && request.getEventName().equals(Constant.DEVICE_EVENT_REPORT_PASSWORD)) {
            policy = this.policyService.findByDeviceAndEvent(request.getSymbol(), Constant.DEVICE_EVENT_REPORT_PASSWORD);
        }
        if (policy != null) {
            log.info("场景:{} , 云端:{}", policy.getPolicyConfig(), policy.isRootPolicy());
            if (policy.isRootPolicy()) {
                if (Constant.EXECUTE_POLICY_INTERVAL > (System.currentTimeMillis() - request.getOccurTime())) {
                    doorAction(request, policy);
                } else {
                    log.info("事件超时,不执行动作");
                }
            }
        }
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * 处理门事件
     *
     * @param request
     */
    private void doorAction(DeviceEventRequest request, Policy policy) {
        // 刷卡或者扫码
        if (request.getEventName().equals(Constant.DEVICE_EVENT_REPORT_CARD) || request.getEventName().equals(Constant.DEVICE_EVENT_REPORT_PASSWORD)) {
            // 查询场景是否存在
            if (policy != null && !StringUtils.isEmpty(policy.getPolicyConfig())) {
                Device device = deviceManager.getDevice(request.getSymbol());
                if (device == null) {
                    throw new DataInvalidException("没有找到这个设备!");
                }
                // 默认使用属性的API Host
                if (!StringUtils.isEmpty(device.getAdditionalAttributes())) {
                    log.info("没有配置门的API Host属性!");
                    externalAPIService.setHost(JsonUtil.getKey(device.getAdditionalAttributes(), Constant.API_CONDITION_URL));
                }
                PolicyConfigDto configDto = PolicyParseUtil.josnToPolicyConfigDto(policy.getPolicyConfig());
                List<ConditionAndSlaveDto> conditionAndSlaveDtos = configDto.getConditionAndSlaveDtos();
                // 查找条件
                conditionAndSlaveDtos.forEach(conditionAndSlaveDto -> {
                    final boolean[] b = {true};
                    conditionAndSlaveDto.getConditions().forEach(baseCondition -> {
                        // API设备
                        if (baseCondition.getConditionType().equals(Constant.CONDITION_TYPE_API)) {
                            // 如果设置了场景的API则使用场景中的API地址验证
                            if (!StringUtils.isEmpty(baseCondition.getUri())) {
                                externalAPIService.setHost(baseCondition.getUri());
                            }
                            log.info("验证API: 数据:{}, MAC地址:{}, 事件名:{}", request.getData(), request.getPiMacAddress(), request.getEventName());
                            if (request.getEventName().equals(Constant.DEVICE_EVENT_REPORT_PASSWORD)) {
                                log.info("验证密码开门:");
                                b[0] = b[0] & externalAPIService.checkPermissionDoorPassword(request.getData(), SymbolUtil.parseMacAddress(request.getSymbol()));
                            }
                            if (request.getEventName().equals(Constant.DEVICE_EVENT_REPORT_CARD)) {
                                log.info("验证刷卡开门:");
                                b[0] = b[0] & externalAPIService.checkPermissionDoorCard(request.getData(), request.getPiMacAddress());
                            }
//                            b[0] = b[0] & externalAPIService.checkPermissionDoor(request.getData(), device.getMacAddress(), request.getEventName(), hostUrl);
                        }
                    });
                    // 具有API条件的场景, 并且事件满足场景
                    Map<String, String> slaveDeviceMap = conditionAndSlaveDto.getSlaveDeviceMap();
                    if (b[0]) {
                        slaveDeviceMap.forEach((symbol, event) -> {
                            deviceRestService.sendCommandToDevice(request.getPiMacAddress(), symbol, event, request.getData()); // 附加卡号
                        });
                    } else {
                        slaveDeviceMap.forEach((symbol, event) -> {
                            deviceRestService.sendCommandToDevice(request.getPiMacAddress(), symbol, "removeCard", request.getData()); // 删除卡号
                        });
                    }
                });
            } else {
                throw new DataInvalidException("没有找到场景配置!");
            }
        }
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
        log.info("========ZIGBEE设备与PI的网络延迟======== {}", request.getPing());
        long serverPing = System.currentTimeMillis() - request.getServerTimeStamp();
        log.info("=======云端与设备之间的网络延迟======{}", serverPing);
        PingDto pingDto = new PingDto(request.getPing(), serverPing);
        pingDto.setLinkQuality(request.getLinkQuality());
        pingDto.setDate(new Date());
        pingDto.setMacAddress(request.getMacAddress());
        deviceService.savePing(request.getMacAddress(), pingDto);
        this.webSocketService.sendPingMessage(pingDto);
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


}
