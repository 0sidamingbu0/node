package com.mydreamplus.smartdevice.api.rest;

import com.mydreamplus.smartdevice.config.Constant;
import com.mydreamplus.smartdevice.dao.jpa.DeviceRepository;
import com.mydreamplus.smartdevice.domain.*;
import com.mydreamplus.smartdevice.domain.out.BaseResponse;
import com.mydreamplus.smartdevice.entity.Device;
import com.mydreamplus.smartdevice.entity.DeviceType;
import com.mydreamplus.smartdevice.entity.Policy;
import com.mydreamplus.smartdevice.service.DeviceManager;
import com.mydreamplus.smartdevice.service.DeviceRestService;
import com.mydreamplus.smartdevice.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午4:45
 * To change this template use File | Settings | File Templates.
 * 提供其他系统调用的API接口
 */
@RestController
@RequestMapping(value = "/device/api")
@Api(value = "设备管理API")
public class DeviceAPIController extends AbstractRestHandler {

    private static final Logger logger = LoggerFactory.getLogger(DeviceAPIController.class);
    @Autowired
    private DeviceManager deviceManager;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceRestService deviceRestService;


    /**
     * Find doors base response.
     *
     * @return the base response
     */
    @RequestMapping(value = "/find/door",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询门信息", notes = "全部")
    public BaseResponse findDoors() {
        log.info("--------------------------------- 外部API -----------------------------------");
        log.info("查询门信息!");
        BaseResponse baseResponse = new BaseResponse(RESPONSE_SUCCESS);
        baseResponse.setDetails("门的mac地址");
        DeviceType door = deviceManager.findDeviceTypeByName(Constant.DEVICE_TYPE_DOOR);
        DeviceType doorController = deviceManager.findDeviceTypeByName(Constant.DEVICE_TYPE_PASSWORD_CONTROLLER);
        Map<String, List<DeviceDto>> map = new HashMap<>();
        List<DeviceDto> list = new ArrayList<>();
        deviceManager.findAllDevicesByType(door).forEach(device -> {
            if (toDeviceDto(device) != null) {
                list.add(toDeviceDto(device));
            }
        });
        deviceManager.findAllDevicesByType(doorController).forEach(device -> {
            if (toDeviceDto(device) != null) {
                list.add(toDeviceDto(device));
            }
        });
        map.put("doors", list);
        baseResponse.setData(map);
        return baseResponse;
    }


    /**
     * 转换deviceDto
     *
     * @param device
     * @return
     */
    private DeviceDto toDeviceDto(Device device) {
        DeviceDto deviceDto = new DeviceDto();
        BeanUtils.copyProperties(device, deviceDto, "deviceGroupList");
        if (device.getDeviceState().equals(DeviceStateEnum.ONLINE)) {
            return deviceDto;
        } else {
            return null;
        }
    }

    /**
     * Sets door code.
     *
     * @param doorCode the door code
     * @return the door code
     */
    @Deprecated
    @RequestMapping(value = "/door/setCode",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "设置DoorCode", notes = "code为0视为解绑")
    public BaseResponse setDoorCode(@RequestBody DoorCode doorCode) {
        log.info("--------------------------------- 外部API -----------------------------------");
        BaseResponse baseResponse = new BaseResponse(RESPONSE_SUCCESS);
        if (StringUtils.isEmpty(doorCode.getMacAddress())) {
            baseResponse.setMessage(RESPONSE_FAILURE);
            baseResponse.setDetails("缺少MAC地址");
            return baseResponse;
        }
        List<Device> devices = deviceManager.findDevicesByMacAddress(doorCode.getMacAddress());
        if (devices != null && devices.size() > 0) {
            Device door = devices.get(0);
            String jsonConfig = door.getAdditionalAttributes();
            JSONObject jsonObject = new JSONObject(jsonConfig);
//            jsonObject.get(Constant.DOOR_ATTRIBUUTE);
            jsonObject.put(Constant.DOOR_ATTRIBUUTE, doorCode.getDoorCode());
            logger.info(jsonObject.toString());
            door.setAdditionalAttributes(jsonObject.toString());
            this.deviceRepository.save(door);
        } else {
            baseResponse.setMessage(RESPONSE_FAILURE);
            baseResponse.setDetails("没有找到该设备:" + doorCode.getMacAddress());
            return baseResponse;
        }
        return baseResponse;
    }

    /**
     * Code base response.
     *
     * @param doorInfo the mac address
     * @return the base response
     */
    @RequestMapping(value = "/door/open",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "开门", notes = "下发命令")
    public BaseResponse code(@RequestBody DoorInfo doorInfo) {
        log.info("--------------------------------- 外部API -----------------------------------");
        log.info("开门请求:{}:{}:{}", doorInfo.getMacAddress(), doorInfo.getDoorCode(), doorInfo.getCardNo());
        BaseResponse baseResponse = new BaseResponse(RESPONSE_SUCCESS);
        Device device = deviceManager.getDevice(doorInfo.getMacAddress());
        if (device == null) {
            baseResponse.setDetails("没有找到这个设备:" + doorInfo.getMacAddress());
            baseResponse.setMessage(RESPONSE_FAILURE);
            return baseResponse;
        }
        deviceRestService.sendCommandToDevice(device.getMacAddress(), device.getSymbol(), Constant.DEVICE_FUNCTION_OPEN_DOOR);
        return baseResponse;
    }

    /**
     * Sets api condition.
     *
     * @param conditionRequest the condition request
     * @return the api condition
     */
    @RequestMapping(value = "/policy/setApiCondition",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "设置API条件Host")
    public BaseResponse setApiCondition(@RequestBody ConditionRequest conditionRequest) {
        log.info("--------------------------------- 外部API -----------------------------------");
        log.info("设置API条件:{}:{}", conditionRequest.getMacAddress(), conditionRequest.getHost());
        BaseResponse baseResponse = new BaseResponse(RESPONSE_SUCCESS);
        if (StringUtils.isEmpty(conditionRequest.getMacAddress())) {
            baseResponse.setMessage(RESPONSE_FAILURE);
            baseResponse.setDetails("没有找到MAC地址!");
            return baseResponse;
        }
//        if (StringUtils.isEmpty(conditionRequest.getHost())) {
//            baseResponse.setMessage(RESPONSE_FAILURE);
//            baseResponse.setDetails("没有设置API Host!");
//            return baseResponse;
//        }
        Policy policy = this.deviceManager.findPolicyByMasterSymbol(conditionRequest.getMacAddress());
        if (policy == null) {
            log.info("没有找到相关场景!");
            baseResponse.setMessage(RESPONSE_FAILURE);
            baseResponse.setDetails("没有找到相关场景!");
            return baseResponse;
        } else {
            log.info("找到场景:{}", policy.getName());
            PolicyConfigDto configDto = JsonUtil.getEntity(policy.getPolicyConfig(), PolicyConfigDto.class);
            configDto.getConditionAndSlaveDtos().forEach(conditionAndSlaveDto -> conditionAndSlaveDto.getConditions().forEach(baseCondition -> baseCondition.setUri(conditionRequest.getHost())));
            policy.setPolicyConfig(JsonUtil.toJsonString(configDto));
            this.deviceManager.savePolicy(policy);
            log.info("保存场景, 场景配置:{}", policy.getPolicyConfig());
        }
        return baseResponse;
    }


    /**
     * Sets api condition.
     *
     * @param request the request
     * @return the api condition
     */
    @RequestMapping(value = "/device/config",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "设置设备属性")
    public BaseResponse configDevice(@RequestBody ConfigRequest request) {
        log.info("--------------------------------- 外部API -----------------------------------");
        log.info("设置设备属性:{}:{}", request.getMacAddress(), request.getConfigJSON());
        BaseResponse baseResponse = new BaseResponse(RESPONSE_SUCCESS);
        if (StringUtils.isEmpty(request.getMacAddress())) {
            baseResponse.setMessage(RESPONSE_FAILURE);
            baseResponse.setDetails("没有找到MAC地址!");
            return baseResponse;
        }
        if (StringUtils.isEmpty(request.getConfigJSON())) {
            baseResponse.setMessage(RESPONSE_FAILURE);
            baseResponse.setDetails("没有配置属性!");
            return baseResponse;
        }
        this.deviceManager.findDevicesByMacAddress(request.getMacAddress()).forEach(device -> {
            device.setAdditionalAttributes(request.getConfigJSON());
            this.deviceManager.saveDevice(device);
        });
        return baseResponse;
    }

    /**
     * The type Device config request.
     */
    static class ConfigRequest {
        /**
         * The Mac address.
         */
        String macAddress;
        /**
         * The Config json.
         */
        String configJSON;

        /**
         * Gets mac address.
         *
         * @return the mac address
         */
        public String getMacAddress() {
            return macAddress;
        }

        /**
         * Sets mac address.
         *
         * @param macAddress the mac address
         */
        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }

        /**
         * Gets config json.
         *
         * @return the config json
         */
        public String getConfigJSON() {
            return configJSON;
        }

        /**
         * Sets config json.
         *
         * @param configJSON the config json
         */
        public void setConfigJSON(String configJSON) {
            this.configJSON = configJSON;
        }
    }

    /**
     * The type Condition request.
     */
    static class ConditionRequest {
        /**
         * The Mac address.
         */
        String macAddress;
        /**
         * The Host.
         */
        String host;

        /**
         * Gets mac address.
         *
         * @return the mac address
         */
        public String getMacAddress() {
            return macAddress;
        }

        /**
         * Sets mac address.
         *
         * @param macAddress the mac address
         */
        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }

        /**
         * Gets host.
         *
         * @return the host
         */
        public String getHost() {
            return host;
        }

        /**
         * Sets host.
         *
         * @param host the host
         */
        public void setHost(String host) {
            this.host = host;
        }
    }

}
