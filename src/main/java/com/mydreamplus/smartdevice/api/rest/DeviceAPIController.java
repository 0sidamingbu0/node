package com.mydreamplus.smartdevice.api.rest;

import com.mydreamplus.smartdevice.config.Constant;
import com.mydreamplus.smartdevice.dao.jpa.DeviceRepository;
import com.mydreamplus.smartdevice.domain.DoorCode;
import com.mydreamplus.smartdevice.domain.DoorInfo;
import com.mydreamplus.smartdevice.domain.out.BaseResponse;
import com.mydreamplus.smartdevice.entity.Device;
import com.mydreamplus.smartdevice.entity.DeviceType;
import com.mydreamplus.smartdevice.service.DeviceManager;
import com.mydreamplus.smartdevice.service.DeviceRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        BaseResponse baseResponse = new BaseResponse(RESPONSE_SUCCESS);
        baseResponse.setDetails("门的mac地址");
        DeviceType door = deviceManager.findDeviceTypeByName(Constant.DEVICE_TYPE_DOOR);
        DeviceType doorController = deviceManager.findDeviceTypeByName(Constant.DEVICE_TYPE_PASSWORD_CONTROLLER);
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        deviceManager.findAllDevicesByType(door).forEach(device -> list.add(device.getMacAddress()));
        deviceManager.findAllDevicesByType(doorController).forEach(device -> list.add(device.getMacAddress()));
        map.put("doors", list);
        baseResponse.setData(map);
        return baseResponse;
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

}
