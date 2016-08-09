package com.mydreamplus.smartdevice.api.rest;

import com.mydreamplus.smartdevice.domain.out.BaseResponse;
import com.mydreamplus.smartdevice.entity.DeviceType;
import com.mydreamplus.smartdevice.service.DeviceManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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


    private static final String DOOR_TYPE_NAME = "MiButton";

    @Autowired
    private DeviceManager deviceManager;

    @RequestMapping(value = "/find/door",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询门信息", notes = "全部")
    public BaseResponse findDoors() {
        BaseResponse baseResponse = new BaseResponse(RESPONSE_SUCCESS);
        baseResponse.setMessage("门的mac地址");
        DeviceType deviceType = deviceManager.findDeviceTypeByName(DOOR_TYPE_NAME);
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        deviceManager.findAllDevicesByType(deviceType).forEach(device -> {
//            map.put("mac", device.getMacAddress());
        });
        baseResponse.setData(map);
        return baseResponse;
    }


}
