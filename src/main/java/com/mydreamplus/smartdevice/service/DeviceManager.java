package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.dao.jpa.*;
import com.mydreamplus.smartdevice.domain.*;
import com.mydreamplus.smartdevice.domain.in.AndroidTVConfigRequest;
import com.mydreamplus.smartdevice.domain.in.DeviceQueryRequest;
import com.mydreamplus.smartdevice.entity.*;
import com.mydreamplus.smartdevice.exception.DataInvalidException;
import com.mydreamplus.smartdevice.util.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 * 设 管理服务 ,面向设 管理的接口,提供网页的API调用
 */
@Service
public class DeviceManager {

    @Autowired
    private DeviceEventRepository deviceEventRepository;

    @Autowired
    private DeviceFunctionRepository deviceFunctionRepository;

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ParentDeviceTypeRepository parentDeviceTypeRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private PIRespository piRespository;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private DeviceRestService deviceRestService;

    @Autowired
    private DeviceGroupRepository deviceGroupRepository;


    /**
     * FinFind device event by id device event.
     *
     * @param ID the id
     * @return the device event
     */
    public DeviceEvent findDeviceEventByID(Long ID) {
        return deviceEventRepository.findOne(ID);
    }

    /**
     * Find device function by id device function.
     *
     * @param ID the id
     * @return the device function
     */
    public DeviceFunction findDeviceFunctionByID(Long ID) {
        return deviceFunctionRepository.findOne(ID);
    }

    /**
     * Save device event device event.
     *
     * @param deviceEvent the device event
     * @return the device event
     */
    public DeviceEvent saveDeviceEvent(DeviceEvent deviceEvent) {
        // 修改
        if (deviceEvent != null && deviceEvent.getID() != null) {
            DeviceEvent deviceEvent1 = deviceEventRepository.findOne(deviceEvent.getID());
            BeanUtils.copyProperties(deviceEvent, deviceEvent1);
            return deviceEventRepository.save(deviceEvent1);
        } else {
            return deviceEventRepository.save(deviceEvent);
        }
    }

    /**
     * Save device function device function.
     *
     * @param function the function
     * @return the device function
     */
    public DeviceFunction saveDeviceFunction(DeviceFunction function) {
        if (function != null && function.getID() != null) {
            DeviceFunction function1 = this.deviceFunctionRepository.findOne(function.getID());
            BeanUtils.copyProperties(function, function1);
            this.deviceFunctionRepository.save(function1);
        }
        return deviceFunctionRepository.save(function);
    }


    /**
     * Save device type device type.
     *
     * @param deviceTypeDto the device type
     * @return the device type
     */
    public DeviceType saveDeviceType(DeviceTypeDto deviceTypeDto) {
        DeviceType deviceType = null;
        if (deviceTypeDto != null && deviceTypeDto.getID() != 0) {
            deviceType = this.deviceTypeRepository.findOne(deviceTypeDto.getID());
        }
        if (deviceType == null) {
            deviceType = new DeviceType();
        }
        BeanUtils.copyProperties(deviceTypeDto, deviceType, "deviceEvents", "deviceFunctions");
        Set<DeviceEvent> deviceEventList = new HashSet<>();
        DeviceType finalDeviceType = deviceType;
        deviceTypeDto.getDeviceEvents().stream().forEach(aLong -> {
            DeviceEvent event = this.findDeviceEventByID(aLong);
            if (event != null) {
                deviceEventList.add(event);
                event.getDeviceTypes().add(finalDeviceType);
            }
        });
        Set<DeviceFunction> deviceFunctionList = new HashSet<>();
        DeviceType finalDeviceType1 = deviceType;
        deviceTypeDto.getDeviceFunctions().stream().forEach(aLong -> {
            DeviceFunction function = this.findDeviceFunctionByID(aLong);
            if (function != null) {
                deviceFunctionList.add(function);
                function.getDeviceTypes().add(finalDeviceType1);
            }
        });
        deviceType.setDeviceFunctions(deviceFunctionList);
        deviceType.setDeviceEvents(deviceEventList);
        return deviceTypeRepository.save(deviceType);
    }


    /**
     * Find all device types list.
     *
     * @param pageDto the page dto
     * @return the list
     */
    public Page<DeviceType> findALLDeviceTypes(PageDto pageDto) {
        PageRequest pageRequest = new PageRequest(pageDto.getPage() - 1, pageDto.getSize());
        return deviceTypeRepository.findAll(pageRequest);
    }

    /**
     * Find all device functions iterable.
     *
     * @param pageDot the page dot
     * @return the iterable
     */
    public Page<DeviceFunction> findALLDeviceFunctions(PageDto pageDot) {
        PageRequest pageRequest = new PageRequest(pageDot.getPage() - 1, pageDot.getSize());
        return this.deviceFunctionRepository.findAll(pageRequest);
    }


    /**
     * Find all device functions iterable.
     *
     * @param pageDot the page dot
     * @return the iterable
     */
    public Page<DeviceEvent> findALLDeviceEvents(PageDto pageDot) {
        PageRequest pageRequest = new PageRequest(pageDot.getPage() - 1, pageDot.getSize());
        return this.deviceEventRepository.findAll(pageRequest);
    }

    /**
     * Find all groups page.
     *
     * @param pageDto the page dto
     * @return the page
     */
    public Page<DeviceGroup> findAllGroups(PageDto pageDto) {
        PageRequest pageRequest = new PageRequest(pageDto.getPage() - 1, pageDto.getSize());
        return this.deviceGroupRepository.findAll(pageRequest);
    }


    /**
     * Find all devices page.
     *
     * @param request  the request
     * @param pageable the pageable
     * @return the page
     */
    public Page<Device> findAllDevices(DeviceQueryRequest request, Pageable pageable) {
        if (!StringUtils.isEmpty(request.getState())) {
            return this.deviceRepository.search(request.isRegistered(), request.getDeviceType(), DeviceStateEnum.valueOf(request.getState()), pageable, request.getSearchKey());
        } else {
            return this.deviceRepository.search(request.isRegistered(), request.getDeviceType(), null, pageable, request.getSearchKey());
        }
    }


    /**
     * Save policy.
     *
     * @param policyDto the policy dto
     */
    public void savePolicy(PolicyDto policyDto) {
        if(policyDto.getGroupId() != null){
            DeviceGroup deviceGroup = this.getDeviceGroup(policyDto.getGroupId());
            if(deviceGroup != null){
                policyDto.setGroupName(deviceGroup.getName());
            }
        }
        Policy policy = new Policy();
        BeanUtils.copyProperties(policyDto, policy);
        policy.setPolicyConfig(JsonUtil.toJsonString(policyDto.getPolicyConfigDto()));
        policy.setMasterEvent(policyDto.getPolicyConfigDto().getMasterDeviceMap().toString());
        // 场景存在,更新场景
        Policy old = policyRepository.findByMasterEvent(policyDto.getPolicyConfigDto().getMasterDeviceMap().toString());
        if (old != null) {
            BeanUtils.copyProperties(policy, old, "ID");
            policyRepository.save(old);
        } else {
            policyRepository.save(policy);
        }
    }

    /**
     * Find policy by master event policy.
     *
     * @param symbol the symbol
     * @param event  the event
     * @return the policy  {00:15:8d:00:00:f2:44:9e-1=PressDown}
     */
    public Policy findPolicyByMasterEvent(String symbol, String event) {
        return policyRepository.findByMasterEvent("{" + symbol + "=" + event + "}");
    }

    /**
     * Save parent device type.
     *
     * @param parentDeviceTypeDto the parent device type dto
     */
    public void saveParentDeviceType(ParentDeviceTypeDto parentDeviceTypeDto) {
        ParentDeviceType parentDeviceType = new ParentDeviceType();
        BeanUtils.copyProperties(parentDeviceTypeDto, parentDeviceType);
        this.parentDeviceTypeRepository.save(parentDeviceType);
    }

    /**
     * Save group.
     *
     * @param groupDto the group dto
     * @return the device group
     */
    public DeviceGroup saveGroup(DeviceGroupDto groupDto) {
        DeviceGroup deviceGroup;
        if (groupDto.getID() != null) {
            deviceGroup = this.deviceGroupRepository.findOne(groupDto.getID());
        } else {
            deviceGroup = new DeviceGroup();
        }
        BeanUtils.copyProperties(groupDto, deviceGroup);
        return this.groupRepository.save(deviceGroup);
    }

    /**
     * 查询所有的 景策略
     *
     * @param pageDto the page dto
     * @return list list
     */
    public Page<Policy> findAllPolicy(PageDto pageDto) {
        Pageable pageable = new PageRequest(pageDto.getPage() - 1, pageDto.getSize());
        return this.policyRepository.search(null, pageable);
    }

    /**
     * Find all devices by name for switch or sensor list.
     *
     * @param aliases the aliases
     * @return the list
     */
    public List<Device> findAllDevicesByNameForSwitchOrSensor(String aliases) {
        if (StringUtils.isEmpty(aliases)) {
            return this.deviceRepository.findAllMasterByFunctionType(DeviceFunctionTypeEnum.SWITCH, DeviceFunctionTypeEnum.SENSOR);
        } else {
            return this.deviceRepository.findAllMasterByAliasesContainingAndFunctionType(aliases, DeviceFunctionTypeEnum.SWITCH, DeviceFunctionTypeEnum.SENSOR);
        }
    }


    /**
     * Find all master device by pi list.
     *
     * @param piMacAddress the pi mac address
     * @return the list
     */
    public List<Device> findAllMasterDeviceByPiMacAddress(String piMacAddress) {
        PI pi = this.piRespository.findByMacAddress(piMacAddress);
        if (pi == null) {
            throw new DataInvalidException("没有找到PI");
        }
        return this.deviceRepository.findAllMasterByFunctionTypeAndPI(DeviceFunctionTypeEnum.SWITCH, DeviceFunctionTypeEnum.SENSOR, pi);
    }

    /**
     * Find all master device by pi list.
     *
     * @param groupId the pi mac address
     * @return the list
     */
    public List<Device> findAllMasterDeviceByGroup(Long groupId) {
        DeviceGroup group = groupRepository.findOne(groupId);
        if (group == null) {
            throw new DataInvalidException("没有找到分组");
        }
        return this.deviceRepository.findAllMasterByFunctionTypeAndGroup(DeviceFunctionTypeEnum.SWITCH, DeviceFunctionTypeEnum.SENSOR, group);
    }


    /**
     * Find all devices by name for controlled list.
     * 查询所有被控设 , 同一个PI上面
     *
     * @param aliases      the aliases
     * @param piMacAddress the pi mac address
     * @return the list
     */
    public List<Device> findAllDevicesByNameForBecontrolled(String aliases, String piMacAddress) {
        return this.deviceRepository.findAllByAliasesContainingAndPi(aliases, DeviceFunctionTypeEnum.CONTROLLED, piMacAddress);
    }

    /**
     * Find all devices by pi mac address for becontrolled list.
     * 查询 PI上所有被控设
     *
     * @param piMacAddress the pi mac address
     * @return the list
     */
    public List<Device> findAllDevicesByPiMacAddressForBecontrolled(String piMacAddress) {
        return this.deviceRepository.findAllByPiAndDeviceFunctionType(DeviceFunctionTypeEnum.CONTROLLED, piMacAddress);
    }

    /**
     * Find all devices by mac address and function type list.
     * 查询 PI上所有设
     *
     * @param functionType the function type   设 的职能类  : Switch / Sensor/ Controlled
     * @param piMacAddress the pi mac address
     * @return the list
     */
    public List<Device> findAllDevicesByMacAddressAndFunctionType(DeviceFunctionTypeEnum functionType, String piMacAddress) {
        return this.deviceRepository.findAllByPiAndDeviceFunctionType(functionType, piMacAddress);
    }

    /**
     * Find all device event by symbol list.
     *
     * @param symbol the symbol
     * @return the list
     */
    public Set<DeviceEvent> findAllDeviceEventBySymbol(String symbol) {
        Device device = this.deviceRepository.findBySymbol(symbol);
        if(device == null){
            throw new DataInvalidException("没有找到设备:" + symbol);
        }
        return device.getDeviceType().getDeviceEvents();
    }

    /**
     * Find all device function by symbol list.
     *
     * @param symbol the symbol
     * @return the list
     */
    public Set<DeviceFunction> findAllDeviceFunctionBySymbol(String symbol) {
        Device device = this.deviceRepository.findBySymbol(symbol);
        return device.getDeviceType().getDeviceFunctions();
    }


    /**
     * Get policy policy.
     *
     * @param ID the id
     * @return the policy
     */
    public Policy getPolicy(Long ID) {
        return this.policyRepository.findOne(ID);
    }

    /**
     * Get device device.
     *
     * @param symbol the symbol
     * @return the device
     */
    public Device getDevice(String symbol) {
        return this.deviceRepository.findBySymbol(symbol);
    }

    /**
     * Find pi by mac address pi.
     *
     * @param macAddress the mac address
     * @return the pi
     */
    public PI findPiByMacAddress(String macAddress) {
        return this.piRespository.findByMacAddress(macAddress);
    }


    /**
     * 获取最近10条数据
     *
     * @param symbol the symbol
     * @return list list
     */
    public List<SensorData> findSensorDatasBySymbol(String symbol) {
        return this.sensorDataRepository.findFirst10BySymbolOrderByCreateTimeDesc(symbol);
    }

    /**
     * Find all pis page.
     *
     * @param pageDto the page dto
     * @return the page
     */
    public Page<PI> findAllPi(PageDto pageDto) {
        PageRequest pageRequest = new PageRequest(pageDto.getPage() - 1, pageDto.getSize(), Sort.Direction.DESC, "registerTime");
        return this.piRespository.findAll(pageRequest);
    }

    /**
     * Remove policy.
     *
     * @param ID the id
     */
    public void removePolicy(Long ID) {
        /*Policy policy = this.policyRepository.findOne(ID);
        policy.setDeleted(true);
        policy.setUpdateTime(new Date());
        this.policyRepository.save(policy);*/
        this.policyRepository.delete(ID);
    }

    /**
     * Remove device function.
     *
     * @param ID the id
     */
    public void removeDeviceFunction(Long ID) {
        this.deviceFunctionRepository.delete(ID);
    }


    /**
     * Remove device event.
     *
     * @param ID the id
     */
    public void removeDeviceEvent(Long ID) {
        this.deviceEventRepository.delete(ID);
    }

    /**
     * Remove pi.
     *
     * @param ID the id
     */
    public void removePi(Long ID) {
        this.piRespository.delete(ID);
    }

    /**
     * Update device info.
     *
     * @param deviceInfo the device info
     */
    public void updateDeviceInfo(DeviceInfo deviceInfo) {
        Device device = this.deviceRepository.findBySymbol(deviceInfo.getSymbol());
        if (device == null) {
            throw new DataInvalidException("没有找到设备!");
        }
        device.setAliases(deviceInfo.getAliases());
        device.setDescription(deviceInfo.getDescription());
        this.deviceRepository.save(device);
    }

    /**
     * Update pi info.
     *
     * @param piDto the pi dto
     */
    public void updatePiInfo(PIDto piDto) {
        PI pi = this.piRespository.findOne(piDto.getID());
        if (pi == null) {
            throw new DataInvalidException("没有找到PI");
        }
        if (piDto.getGroupId() != null) {
            DeviceGroup deviceGroup = this.deviceGroupRepository.findOne(piDto.getGroupId());
            if (deviceGroup != null) {
                pi.setDeviceGroup(deviceGroup);
                deviceGroup.getPis().add(pi);
                this.deviceGroupRepository.save(deviceGroup);
            }
        }
        pi.setUpdateTime(new Date());
        pi.setName(piDto.getName());
        pi.setDescription(piDto.getDescription());
        this.piRespository.save(pi);
    }


    /**
     * Find devices by type list.
     *
     * @param deviceType the device type
     * @return the list
     */
    public List<Device> findAllDevicesByType(DeviceType deviceType) {
        return this.deviceRepository.findByDeviceType(deviceType);
    }


    /**
     * Find device type by name device type.
     *
     * @param typeName the type name
     * @return the device type
     */
    public DeviceType findDeviceTypeByName(String typeName) {
        return this.deviceTypeRepository.findByName(typeName);
    }

    /**
     * Find devices by mac address list.
     *
     * @param macAddress the mac address
     * @return the list
     */
    public List<Device> findDevicesByMacAddress(String macAddress) {
        return this.deviceRepository.findAllByMacAddress(macAddress);
    }


    /**
     * 更新Android TV配置信息
     *
     * @param request the request
     */
    public void updateAndroidConfig(AndroidTVConfigRequest request) {
        Device device = this.deviceRepository.findOne(request.getDeviceId());
        if (device == null) {
            throw new DataInvalidException("没有找到设备");
        }
        AndroidTVConfigDto dto = new AndroidTVConfigDto();
        dto.setCustomerUrl(request.getCustomerUrl());
        dto.setDefaultUrl(request.getDefaultUrl());
        device.setAdditionalAttributes(JsonUtil.toJsonString(dto));
        this.deviceRepository.save(device);
        this.deviceRestService.sendConfigProperty(device);
    }

    /**
     * Find pi by group list.
     *
     * @param groupId the group id
     * @return the list
     */
    public List<PI> findPiByGroup(Long groupId) {
        DeviceGroup group = this.groupRepository.findOne(groupId);
        if (group != null) {
            return group.getPis();
        } else {
            return null;
        }
    }


    public DeviceGroup getDeviceGroup(Long id) {
        return this.deviceGroupRepository.findOne(id);
    }
}

