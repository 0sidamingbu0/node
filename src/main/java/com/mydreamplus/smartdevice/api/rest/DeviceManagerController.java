package com.mydreamplus.smartdevice.api.rest;

import com.mydreamplus.smartdevice.config.Constant;
import com.mydreamplus.smartdevice.config.DeviceConfig;
import com.mydreamplus.smartdevice.dao.jpa.LinkQualityRepositoryImpl;
import com.mydreamplus.smartdevice.domain.*;
import com.mydreamplus.smartdevice.domain.in.*;
import com.mydreamplus.smartdevice.domain.out.BaseResponse;
import com.mydreamplus.smartdevice.domain.out.PageResponse;
import com.mydreamplus.smartdevice.entity.*;
import com.mydreamplus.smartdevice.exception.DataInvalidException;
import com.mydreamplus.smartdevice.service.DeviceManager;
import com.mydreamplus.smartdevice.service.DeviceRestService;
import com.mydreamplus.smartdevice.service.DeviceService;
import com.mydreamplus.smartdevice.util.JsonUtil;
import com.mydreamplus.smartdevice.util.SymbolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午4:45
 * To change this template use File | Settings | File Templates.
 * 设 管理API,提供给web页面管理设
 */
@RestController
@RequestMapping(value = "/device/manager")
@Api(value = "设备管理API", description = "设备管理API,提供设备管理接口,web页面调用")
public class DeviceManagerController extends AbstractRestHandler {

    /**
     * 设 入网时间缓
     */
    private static final Map<String, Date> PERMIT_TIME_CACHE = new HashMap<>(1000);
    /**
     * PING命令次数
     */
    private static final int PING_TIMES = 10;

    /**
     * ping间隔时间
     */
    private static final long PING_INTERVAL = 1000;

    private final Logger log = LoggerFactory.getLogger(DeviceManagerController.class);

    @Autowired
    private DeviceManager deviceManager;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceRestService restService;


    /**
     * Create device event.
     *
     * @param request the request
     * @return the base response
     */
    @RequestMapping(value = "/android/config",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "配置AndroidTV")
    public BaseResponse updateAndroidTVConfig(@RequestBody AndroidTVConfigRequest request) {
        this.deviceManager.updateAndroidConfig(request);
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * Create device event.
     *
     * @param symbol the symbol
     * @return the base response
     */
    @RequestMapping(value = "/config/{symbol}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "配置AndroidTV")
    public BaseResponse sendConfig(@PathVariable String symbol) {
        Device device = this.deviceManager.getDevice(symbol);
        if (device == null) {
            throw new DataInvalidException("没有找到设备");
        }
        this.restService.sendConfigProperty(device);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * Create device function.
     *
     * @param deviceFunctionDto the device function dto
     * @return the base response
     */
    @RequestMapping(value = "/deviceFunction/create",
            method = {RequestMethod.POST},
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "创建设备被操作方法")
    public BaseResponse createDeviceFunction(@RequestBody DeviceFunctionDto deviceFunctionDto) {
        DeviceFunction deviceFunction = new DeviceFunction();
        BeanUtils.copyProperties(deviceFunctionDto, deviceFunction);
        this.deviceManager.saveDeviceFunction(deviceFunction);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * Create parent device type base response.
     *
     * @param parentDeviceTypeDto the parent device type dto
     * @return the base response
     */
    @RequestMapping(value = "/parentDeviceType/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "创建设备类型")
    public BaseResponse createParentDeviceType(@RequestBody ParentDeviceTypeDto parentDeviceTypeDto) {
        this.deviceManager.saveParentDeviceType(parentDeviceTypeDto);
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * Create device type.
     *
     * @param deviceTypeDto the device type dto
     * @return the base response
     */
    @RequestMapping(value = "/deviceType/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "创建设备类型")
    public BaseResponse createDeviceType(@RequestBody DeviceTypeDto deviceTypeDto) {
        this.deviceManager.saveDeviceType(deviceTypeDto);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * Create device event base response.
     *
     * @param deviceEventDto the device event dto
     * @return the base response
     */
    @RequestMapping(value = "/deviceEvent/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "创建设备事件")
    public BaseResponse createDeviceEvent(@RequestBody DeviceEventDto deviceEventDto) {
        DeviceEvent deviceEvent = new DeviceEvent();
        BeanUtils.copyProperties(deviceEventDto, deviceEvent);
        this.deviceManager.saveDeviceEvent(deviceEvent);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * Find all device types.
     *
     * @param pageDto the page dto
     * @return the base response
     */
    @RequestMapping(value = "/deviceType/findAll",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询设备类型", notes = "全部")
    public PageResponse findAllDeviceTypes(@RequestBody PageDto pageDto) {
        PageResponse response = new PageResponse();
        Page<DeviceType> deviceTypes = deviceManager.findALLDeviceTypes(pageDto);
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
        response.setTotalPages(deviceTypes.getTotalPages());
        response.setCurrentPage(pageDto.getPage());
        response.setTotalElements(deviceTypes.getNumberOfElements());
        response.setPerPage(pageDto.getSize());
        response.setData(deviceTypeDtos);
        return response;
    }

    /**
     * Find all device functions page response.
     *
     * @param pageDto the page dto
     * @return the page response
     */
    @RequestMapping(value = "/deviceFunction/findAll",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询设备类型的可操作方法", notes = "全部")
    public PageResponse findAllDeviceFunctions(@RequestBody PageDto pageDto) {
        PageResponse response = new PageResponse();
        Page<DeviceFunction> deviceFunctions = deviceManager.findALLDeviceFunctions(pageDto);
        deviceFunctions.forEach(deviceFunction -> deviceFunction.setDeviceTypes(null));
        response.setTotalPages(deviceFunctions.getTotalPages());
        response.setCurrentPage(pageDto.getPage());
        response.setTotalElements(deviceFunctions.getNumberOfElements());
        response.setPerPage(pageDto.getSize());
        response.setData(deviceFunctions);
        return response;
    }

    /**
     * Find all device events page response.
     *
     * @param pageDto the page dto
     * @return the page response
     */
    @RequestMapping(value = "/deviceEvents/findAll",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询设备类型可触发的事件", notes = "全部")
    public PageResponse findAllDeviceEvents(@RequestBody PageDto pageDto) {
        PageResponse response = new PageResponse();
        Page<DeviceEvent> deviceEvents = deviceManager.findALLDeviceEvents(pageDto);
        deviceEvents.forEach(deviceFunction -> deviceFunction.setDeviceTypes(null));
        response.setTotalPages(deviceEvents.getTotalPages());
        response.setCurrentPage(pageDto.getPage());
        response.setTotalElements(deviceEvents.getNumberOfElements());
        response.setPerPage(pageDto.getSize());
        response.setData(deviceEvents);
        return response;
    }


    /**
     * Find all devices list.
     *
     * @param request the request
     * @return the list
     */
    @RequestMapping(value = "/device/findAll",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询全部设备", notes = "根据条件查询")
    public PageResponse findAllDevices(@RequestBody DeviceQueryRequest request) {
        List<DeviceDto> deviceDtos = new ArrayList<>();
        Page<Device> pageDevice = this.deviceManager.findAllDevices(request,
                new PageRequest(request.getPageDto().getPage() - 1, request.getPageDto().getSize()));
        if (pageDevice != null && pageDevice.getTotalElements() > 0) {
            pageDevice.forEach(device -> {
                PI pi = device.getPi();
                DeviceDto d = new DeviceDto();
                BeanUtils.copyProperties(device, d, "deviceGroupList");
                if (pi != null) {
                    d.setPIID(pi.getMacAddress());
                    d.setPiName(pi.getName());
                }
                if (device.getDeviceType() != null)
                    d.setDeviceType(device.getDeviceType().getAliases());
                d.setLinkQuality(LinkQualityRepositoryImpl.getLinkQuality(d.getMacAddress()));
                deviceDtos.add(d);
            });
        }
        PageResponse response = new PageResponse();
        response.setCurrentPage(request.getPageDto().getPage());
        response.setPerPage(request.getPageDto().getSize());
        response.setTotalElements(pageDevice.getTotalElements());
        response.setTotalPages(pageDevice.getTotalPages());
        response.setData(deviceDtos);
        return response;
    }


    /**
     * Find all devices by name list.
     *
     * @param aliases the name
     * @return the list
     */
    @RequestMapping(value = "/device/findAllMasterByAliases/{aliases}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询主控设备")
    public List<DeviceDto> findAllMasterDevicesByName(@PathVariable String aliases) {
        List<DeviceDto> deviceDtos = new ArrayList<>();
        this.deviceManager.findAllDevicesByNameForSwitchOrSensor(aliases).forEach(device -> {
            DeviceDto dto = new DeviceDto(device.getSymbol());
            dto.setPIID(device.getPi().getMacAddress());
            dto.setAliases(device.getAliases());
            deviceDtos.add(dto);
        });
        return deviceDtos;
    }

    /**
     * Find all master devices list.
     *
     * @return the list
     */
    @RequestMapping(value = "/device/findAllMaster",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询主控设备")
    public List<DeviceDto> findAllMasterDevices() {
        List<DeviceDto> deviceDtos = new ArrayList<>();
        this.deviceManager.findAllDevicesByNameForSwitchOrSensor("").forEach(device -> {
            DeviceDto dto = new DeviceDto(device.getSymbol());
            dto.setPIID(device.getPi().getMacAddress());
            String groupName = Constant.DEFAULT_GROUP_NAME;
            if (device.getPi().getDeviceGroup() != null) {
                groupName = device.getPi().getDeviceGroup().getName();
            }
            dto.setAliases(groupName + "-" + device.getPi().getName() + "-" + device.getAliases());
            deviceDtos.add(dto);
        });
        return deviceDtos;
    }


    /**
     * Find all slave devices by name list.
     *
     * @param aliases      the aliases
     * @param piMacAddress the piid
     * @return the list
     */
    @RequestMapping(value = "/device/findAllSlaveByName/{aliases}/{piMacAddress}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询被控设备")
    public List<DeviceDto> findAllSlaveDevicesByName(@PathVariable String aliases, @PathVariable String piMacAddress) {
        List<DeviceDto> deviceDtos = new ArrayList<>();
        this.deviceManager.findAllDevicesByNameForBecontrolled(aliases, piMacAddress).forEach(device -> {
            DeviceDto dto = new DeviceDto(device.getSymbol());
            dto.setAliases(device.getAliases());
            deviceDtos.add(dto);
        });
        return deviceDtos;
    }

    /**
     * Find all slave device by pi mac address list.
     *
     * @param piMacAddress the pi mac address
     * @return the list
     */
    @RequestMapping(value = "/device/findAllSlaveDeviceByPiMacAddress/{piMacAddress}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询被控设备")
    public List<DeviceDto> findAllSlaveDeviceByPiMacAddress(@PathVariable String piMacAddress) {
        List<DeviceDto> deviceDtos = new ArrayList<>();
        this.deviceManager.findAllDevicesByPiMacAddressForBecontrolled(piMacAddress).forEach(device -> {
            DeviceDto dto = new DeviceDto(device.getSymbol());
            dto.setAliases(device.getAliases());
            deviceDtos.add(dto);
        });
        return deviceDtos;
    }

    /**
     * Find all slave device by pi mac address list.
     *
     * @param piMacAddress the pi mac address
     * @param type         the type
     * @return the list
     */
    @RequestMapping(value = "/device/findAllConditionDeviceByType/{type}/{piMacAddress}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询条件类型的设备")
    public List<DeviceDto> findAllConditionDeviceByType(@PathVariable String piMacAddress, @PathVariable String type) {
        List<DeviceDto> deviceDtos = new ArrayList<>();
        this.deviceManager.findAllDevicesByMacAddressAndFunctionType(DeviceFunctionTypeEnum.valueOf(type), piMacAddress).forEach(device -> {
            DeviceDto dto = new DeviceDto(device.getSymbol());
            dto.setAliases(device.getAliases());
            deviceDtos.add(dto);
        });
        return deviceDtos;
    }


    /**
     * Find all device event by symbol list.
     *
     * @param symbol the symbol
     * @return the list
     */
    @RequestMapping(value = "/deviceEvent/findAllBySymbol/{symbol}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询某个设备上的事件")
    public List<DeviceEventDto> findAllDeviceEventBySymbol(@PathVariable String symbol) {
        List<DeviceEventDto> dtos = new ArrayList<>();
        this.deviceManager.findAllDeviceEventBySymbol(symbol).forEach(deviceEvent -> {
            DeviceEventDto deviceEventDto = new DeviceEventDto();
            deviceEventDto.setName(deviceEvent.getName());
            deviceEventDto.setAlias(deviceEvent.getAlias());
            dtos.add(deviceEventDto);
        });
        return dtos;
    }

    /**
     * Find all device function by symbol list.
     *
     * @param symbol the symbol
     * @return the list
     */
    @RequestMapping(value = "/deviceFunction/findAllBySymbol/{symbol}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询某个设备上可以被操作的方法")
    public List<DeviceFunctionDto> findAllDeviceFunctionBySymbol(@PathVariable String symbol) {
        List<DeviceFunctionDto> dtos = new ArrayList<>();
        this.deviceManager.findAllDeviceFunctionBySymbol(symbol).forEach(deviceFunction -> {
            DeviceFunctionDto deviceFunctionDto = new DeviceFunctionDto();
            deviceFunctionDto.setAlias(deviceFunction.getAlias());
            deviceFunctionDto.setName(deviceFunction.getName());
            dtos.add(deviceFunctionDto);
        });
        return dtos;
    }


    /**
     * Create policy base response.
     *
     * @param request the request
     * @return the base response
     */
    @RequestMapping(value = "/policy/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "创建场景策略")
    @Transactional
    public BaseResponse createPolicy(@RequestBody DevicePolicyRequest request) {
        if (request == null || StringUtils.isEmpty(request.getPiMacAddress())) {
            throw new DataInvalidException("没有PI的mac地址");
        }
        PI pi = this.deviceManager.findPiByMacAddress(request.getPiMacAddress());
        if (pi == null) {
            throw new DataInvalidException("没有找到PI");
        }
        log.info(request.toString());
        BaseResponse response = new BaseResponse(RESPONSE_SUCCESS);
        PolicyDto policyDto = new PolicyDto();
        BeanUtils.copyProperties(request, policyDto, "pi");
        policyDto.setPi(pi);
        log.info("保存策略:{}", request.getName());
        this.deviceManager.savePolicy(policyDto);
        // 下发策略
        log.info("下发策略:{}", JsonUtil.toJsonString(policyDto.getPolicyConfigDto()));
        this.restService.sendPolicy(request.getPiMacAddress(), policyDto.getPolicyConfigDto());
        return response;
    }

    /**
     * Create group base response.
     *
     * @param groupDto the group dto
     * @return the base response
     */
    @RequestMapping(value = "/group/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "创建场景策略")
    public BaseResponse createGroup(@RequestBody DeviceGroupDto groupDto) {
        if (groupDto == null || StringUtils.isEmpty(groupDto.getName())) {
            throw new DataInvalidException("组名不能为空");
        }
        this.deviceManager.saveGroup(groupDto);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * Remove base response.
     *
     * @param request the request
     * @return the base response
     */
    @RequestMapping(value = "/join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "允许某一个设备加入网络")
    public BaseResponse allow(@RequestBody DeviceMacAddrRequest request) {
        log.info(String.format(":::::允许设备 %s 加入!", request.getMacAddress()));
        this.deviceService.allowDeviceJoinIn(request.getPiMacAddress(), SymbolUtil.parseMacAddress(request.getMacAddress()));
        this.deviceManager.findDevicesByMacAddress(request.getMacAddress()).forEach(device -> {
            if (device.getAdditionalAttributes() != null) {
                this.restService.sendConfigProperty(device);
            }
        });
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * Find all devices list.
     *
     * @param request the request
     * @return the list
     */
    @RequestMapping(value = "/policy/findAll",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询全部策略", notes = "根据条件查询")
    public PageResponse findAllPolicy(@RequestBody PolicyQueryRequest request) {
        if (request == null || request.getPageDto() == null) {
            throw new DataInvalidException("没有分页信息");
        }
        PageResponse pageResponse = new PageResponse();
        Page<Policy> page = this.deviceManager.findAllPolicy(request.getPageDto(), request.getSearchKey());
        List<PolicyDto> policyDtos = new ArrayList<>();
        page.forEach(policy -> {
            policy.setPi(null);
            PolicyDto policyDto = new PolicyDto();
            BeanUtils.copyProperties(policy, policyDto, "pi");
            policyDto.setUpdateTime(policy.getUpdateTime() == null ? policy.getCreateTime().getTime() : policy.getUpdateTime().getTime());
            policyDtos.add(policyDto);
        });
        pageResponse.setData(policyDtos);
        pageResponse.setCurrentPage(request.getPageDto().getPage());
        pageResponse.setPerPage(request.getPageDto().getSize());
        pageResponse.setTotalElements(page.getTotalElements());
        pageResponse.setTotalPages(page.getTotalPages());
        return pageResponse;
    }


    /**
     * Gets policy.
     *
     * @param ID the id
     * @return the policy
     */
    @RequestMapping(value = "/policy/get/{ID}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询场景", notes = "获取场景配置")
    public BaseResponse getPolicy(@PathVariable Long ID) {
        if (ID == 0) {
            throw new DataInvalidException("没有场景ID");
        }
        BaseResponse response = new BaseResponse(RESPONSE_SUCCESS);
        Policy policy = deviceManager.getPolicy(ID);
        if (policy != null) {
            PI pi = policy.getPi();
            if (pi != null) {
                pi.setZbDeviceList(null);
                if (pi.getDeviceGroup() != null)
                    pi.getDeviceGroup().setPis(null);
                pi.setPolicies(null);
            }
        }
        response.setData(policy);
        return response;
    }

    /**
     * Gets device.
     *
     * @param symbol the symbol
     * @return the device
     */
    @RequestMapping(value = "/device/get/{symbol}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询设备信息", notes = "获取设备的详细信息")
    public BaseResponse getDevice(@PathVariable String symbol) {
        if (StringUtils.isEmpty(symbol)) {
            throw new DataInvalidException("没有找到设备ID");
        }
        BaseResponse response = new BaseResponse(RESPONSE_SUCCESS);
        Device device = deviceManager.getDevice(symbol);
        if (device != null) {
            DeviceInfoDto deviceInfoDto = new DeviceInfoDto();
            BeanUtils.copyProperties(device, deviceInfoDto, "deviceType");
            deviceInfoDto.setDeviceType(device.getDeviceType().getAliases());
            deviceInfoDto.setSensorDatas(deviceManager.findSensorDatasBySymbol(device.getSymbol()));
            PI pi = device.getPi();
            if (pi != null) {
                deviceInfoDto.setPIID(device.getPi().getMacAddress());
                deviceInfoDto.setLinkQuality(LinkQualityRepositoryImpl.getLinkQuality(device.getMacAddress()));
            }
            response.setData(deviceInfoDto);
        }
        return response;
    }


    /**
     * Permit base response.
     *
     * @param minute       the minute
     * @param piMacAddress the pi mac address
     * @return the base response
     */
    @RequestMapping(value = "/permit/{piMacAddress}/{minute}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "允许设备加入网络,单位:分钟")
    public BaseResponse permit(@PathVariable int minute, @PathVariable String piMacAddress) {
        if (minute > 60) {
            throw new DataInvalidException("设备入网时间不能超过1个小时");
        }
        // 倒计时还未结束
        if (PERMIT_TIME_CACHE.get(piMacAddress) != null && PERMIT_TIME_CACHE.get(piMacAddress).getTime() > System.currentTimeMillis()) {
            throw new DataInvalidException("倒计时还未结束,结束时间:" + PERMIT_TIME_CACHE.get(piMacAddress));
        }
        PERMIT_TIME_CACHE.put(piMacAddress, new Date(System.currentTimeMillis() + 1000 * 60 * minute));
        this.deviceService.permitJoinIn(piMacAddress, minute);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * Find all p is page response.
     *
     * @param request the request
     * @return the page response
     */
    @RequestMapping(value = "/pi/findAll",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询PI", notes = "分页")
    public PageResponse findAllPIs(@RequestBody PIQueryRequest request) {
        PageResponse pageResponse = new PageResponse();
        Page<PI> page = this.deviceManager.findAllPi(request.getPageDto());
        List<PIDto> list = new ArrayList<>();
        page.forEach(pi -> {
            PIDto piDto = new PIDto();
            piDto.setName(pi.getName());
            piDto.setID(pi.getID());
            piDto.setDescription(pi.getDescription());
            piDto.setMacAddress(pi.getMacAddress());
            piDto.setRegisterTime(pi.getRegisterTime());
            piDto.setCreateTime(pi.getCreateTime());
            if (pi.getDeviceGroup() != null) {
                piDto.setGroupId(pi.getDeviceGroup().getID());
                piDto.setGroupName(pi.getDeviceGroup().getName());
            }
            piDto.setIpAddress(pi.getIpAddress());
            piDto.setPermitEndTime(PERMIT_TIME_CACHE.get(pi.getMacAddress()));
            list.add(piDto);
        });
        pageResponse.setData(list);
        pageResponse.setTotalElements(page.getTotalElements());
        pageResponse.setPerPage(page.getNumberOfElements());
        pageResponse.setCurrentPage(request.getPageDto().getPage());
        pageResponse.setTotalPages(page.getTotalPages());
        return pageResponse;
    }


    /**
     * 删除 景
     *
     * @param ID the id
     * @return the base response
     */
    @RequestMapping(value = "/policy/remove/{ID}",
            method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除场景策略")
    public BaseResponse removePolicy(@PathVariable Long ID) {
        this.deviceManager.removePolicy(ID);
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    @RequestMapping(value = "/gateway/remove/{ID}",
            method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除网关")
    public BaseResponse removeGateway(@PathVariable Long ID) {
        this.deviceManager.removeGateway(ID);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    @RequestMapping(value = "/deviceType/remove/{ID}",
            method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除设备类型")
    public BaseResponse removeDeviceType(@PathVariable Long ID) {
        this.deviceManager.removeDeviceType(ID);
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    @RequestMapping(value = "/group/remove/{ID}",
            method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除组")
    public BaseResponse removeGroup(@PathVariable Long ID) {
        this.deviceManager.removeGroup(ID);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * Remove device event base response.
     *
     * @param ID the id
     * @return the base response
     */
    @RequestMapping(value = "/deviceEvent/remove/{ID}",
            method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除事件")
    public BaseResponse removeDeviceEvent(@PathVariable Long ID) {
        this.deviceManager.removeDeviceEvent(ID);
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * Remove device function base response.
     *
     * @param ID the id
     * @return the base response
     */
    @RequestMapping(value = "/deviceFunction/remove/{ID}",
            method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除方法")
    public BaseResponse removeDeviceFunction(@PathVariable Long ID) {
        this.deviceManager.removeDeviceFunction(ID);
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * Remove pi base response.
     *
     * @param ID the id
     * @return the base response
     */
    @RequestMapping(value = "/pi/remove/{ID}",
            method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除场景策略")
    public BaseResponse removePi(@PathVariable Long ID) {
        this.deviceManager.removePi(ID);
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * 删除设
     *
     * @param request the request
     * @return the base response
     */
    @RequestMapping(value = "/remove",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除设备,设备重新注册")
    public BaseResponse removeDevice(@RequestBody DeviceMacAddrRequest request) {
        log.info(String.format(":::::删除设备 %s!", request.getMacAddress()));
        this.deviceService.removeDevice(request.getPiMacAddress(), request.getMacAddress());
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * Ping device base response.
     *
     * @param symbol the symbol
     * @return the base response
     * @throws InterruptedException the interrupted exception
     */
    @RequestMapping(value = "/ping/{symbol}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "PING设备,获取网络延迟")
    public BaseResponse pingDevice(@PathVariable String symbol) throws InterruptedException {
        log.info(":::::PING设备{}!", symbol);
        Device device = deviceManager.getDevice(symbol);
        if (device == null) {
            throw new DataInvalidException("没有找到设备!" + symbol);
        }
        for (int i = 0; i < PING_TIMES; i++) {
            Thread.sleep(PING_INTERVAL);
            this.restService.getPing(device.getPi().getMacAddress(), device.getMacAddress());
        }
        return new BaseResponse(RESPONSE_SUCCESS);
    }

    /**
     * Update device info base response.
     *
     * @param deviceInfo the device info
     * @return the base response
     */
    @RequestMapping(value = "/updateDeviceInfo",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "修改设备信息")
    public BaseResponse updateDeviceInfo(@RequestBody DeviceInfo deviceInfo) {
        if (deviceInfo == null || StringUtils.isEmpty(deviceInfo.getSymbol())) {
            throw new DataInvalidException("设备标识信息为空");
        }
        this.deviceManager.updateDeviceInfo(deviceInfo);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * 更新PI的 名称 和 描述
     *
     * @param piDto the pi dto
     * @return base response
     */
    @RequestMapping(value = "/updatePiInfo",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "修改PI信息")
    public BaseResponse updateDeviceInfo(@RequestBody PIDto piDto) {
        if (piDto == null || piDto.getID() == 0) {
            throw new DataInvalidException("没有PI ID");
        }
        this.deviceManager.updatePiInfo(piDto);
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * Keep auto join base response.
     *
     * @param can the can
     * @return the base response
     */
    @RequestMapping(value = "/autoJoin/{can}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "注册设备")
    @Transactional
    public BaseResponse keepAutoJoin(@PathVariable String can) {
        if (can.equals("true")) {
            DeviceConfig.setAutoJoinIn(true);
        } else {
            DeviceConfig.setAutoJoinIn(false);
        }
        return new BaseResponse(RESPONSE_SUCCESS);
    }


    /**
     * Find all groups page response.
     *
     * @param pageDto the page dto
     * @return the page response
     */
    @RequestMapping(value = "/group/findAll",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询全部分组", notes = "分页")
    public PageResponse findAllGroups(@RequestBody PageDto pageDto) {
        PageResponse response = new PageResponse();
        List<DeviceGroupDto> deviceGroupDtos = new ArrayList<>();
        Page<DeviceGroup> deviceGroups = deviceManager.findAllGroups(pageDto);
        deviceGroups.forEach(deviceGroup -> {
            DeviceGroupDto dto = new DeviceGroupDto();
            dto.setName(deviceGroup.getName());
            dto.setDescription(deviceGroup.getDescription());
            dto.setID(deviceGroup.getID());
            deviceGroupDtos.add(dto);
        });
        deviceGroups.forEach(deviceGroup -> deviceGroup.setPis(null));
        response.setTotalPages(deviceGroups.getTotalPages());
        response.setCurrentPage(pageDto.getPage());
        response.setTotalElements(deviceGroups.getNumberOfElements());
        response.setPerPage(pageDto.getSize());
        response.setData(deviceGroupDtos);
        return response;
    }

    /**
     * Find pi by group base response.
     *
     * @param groupId the group id
     * @return the base response
     */
    @RequestMapping(value = "/findPiByGroup/{groupId}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询分组的PI")
    @Transactional
    public BaseResponse findPiByGroup(@PathVariable Long groupId) {
        BaseResponse response = new BaseResponse(RESPONSE_SUCCESS);
        List<PIDto> list = new ArrayList<>();
        List<PI> pis = this.deviceManager.findPiByGroup(groupId);
        if (pis != null) {
            pis.forEach(pi -> {
                PIDto piDto = new PIDto();
//                BeanUtils.copyProperties(pi, piDto, "deviceGroup");
                piDto.setMacAddress(pi.getMacAddress());
                piDto.setName(pi.getName());
                list.add(piDto);
            });
        }
        response.setData(list);
        return response;
    }


    /**
     * Find master device by group base response.
     *
     * @param piMacAddress the pi mac address
     * @return the base response
     */
    @RequestMapping(value = "/findMasterDeviceByPi/{piMacAddress}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据PI查询主控设备")
    @Transactional
    public BaseResponse findMasterDeviceByPi(@PathVariable String piMacAddress) {
        if (StringUtils.isEmpty(piMacAddress)) {
            throw new DataInvalidException("没有PI的Mac地址");
        }
        List<DeviceDto> deviceDtos = new ArrayList<>();
        this.deviceManager.findAllMasterDeviceByPiMacAddress(piMacAddress).forEach(device -> {
            DeviceDto dto = new DeviceDto();
            dto.setSymbol(device.getSymbol());
            dto.setAliases(device.getAliases());
            deviceDtos.add(dto);
        });
        BaseResponse response = new BaseResponse(RESPONSE_SUCCESS);
        response.setData(deviceDtos);
        return response;
    }


    /**
     * Find master device by group base response.
     *
     * @param groupId the pi mac address
     * @return the base response
     */
    @RequestMapping(value = "/findMasterDeviceByGroup/{groupId}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据group查询主控设备")
    @Transactional
    public BaseResponse findMasterDeviceByGroup(@PathVariable Long groupId) {
        if (groupId == null) {
            throw new DataInvalidException("没有找到分组");
        }
        List<DeviceDto> deviceDtos = new ArrayList<>();
        this.deviceManager.findAllMasterDeviceByGroup(groupId).forEach(device -> {
            DeviceDto dto = new DeviceDto();
            dto.setSymbol(device.getSymbol());
            dto.setPIID(device.getPi().getMacAddress());
            dto.setAliases(device.getAliases());
            deviceDtos.add(dto);
        });
        BaseResponse response = new BaseResponse(RESPONSE_SUCCESS);
        response.setData(deviceDtos);
        return response;
    }

    /**
     * Find master device by group base response.
     *
     * @param symbol the symbol
     * @param event  the event
     * @return the base response
     */
    @RequestMapping(value = "/findPolicy/{symbol}/{event}",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询分组的PI")
    @Transactional
    public BaseResponse findMasterDeviceBySymbolEvent(@PathVariable String symbol, @PathVariable String event) {

        Policy policy = this.deviceManager.findPolicyByMasterEvent(symbol, event);
        BaseResponse response;
        if (policy != null) {
            PolicyDto policyDto = new PolicyDto();
            BeanUtils.copyProperties(policy, policyDto, "pi");
            response = new BaseResponse(RESPONSE_SUCCESS);
            response.setData(policyDto);
        } else {
            response = new BaseResponse(RESPONSE_FAILURE);
        }
        return response;
    }


}
