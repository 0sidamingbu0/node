package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.dao.jpa.*;
import com.mydreamplus.smartdevice.domain.*;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 * 设备管理服务层,面向设备管理的接口,提供网页的API调用
 */
@Service
public class DeviceManager {

    @Autowired
    private DeviceEventRepository deviceEventRepository;

    @Autowired
    private DeviceFunctionFRepository deviceFunctionFRepository;

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
        return deviceFunctionFRepository.findOne(ID);
    }

    /**
     * Save device event device event.
     *
     * @param deviceEvent the device event
     * @return the device event
     */
    public DeviceEvent saveDeviceEvent(DeviceEvent deviceEvent) {
        // 修改
        if (deviceEvent.getID() != 0) {
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
        return deviceFunctionFRepository.save(function);
    }


    /**
     * Save device type device type.
     *
     * @param deviceTypeDto the device type
     * @return the device type
     */
    public DeviceType saveDeviceType(DeviceTypeDto deviceTypeDto) {
        DeviceType deviceType = new DeviceType();
        BeanUtils.copyProperties(deviceTypeDto, deviceType, "deviceEvents", "deviceFunctions");
        List<DeviceEvent> deviceEventList = new ArrayList<>();
        deviceTypeDto.getDeviceEvents().stream().forEach(aLong -> {
            DeviceEvent event = this.findDeviceEventByID(aLong);
            if (event != null) {
                deviceEventList.add(event);
                event.getDeviceTypes().add(deviceType);
            }
        });
        List<DeviceFunction> deviceFunctionList = new ArrayList<>();
        deviceTypeDto.getDeviceFunctions().stream().forEach(aLong -> {
            DeviceFunction function = this.findDeviceFunctionByID(aLong);
            if (function != null) {
                deviceFunctionList.add(function);
                function.getDeviceTypes().add(deviceType);
            }
        });
        deviceType.setDeviceFunctions(deviceFunctionList);
        deviceType.setDeviceEvents(deviceEventList);
//        ParentDeviceType parentDeviceType = new ParentDeviceType();
//        parentDeviceType.setID(deviceTypeDto.getParentDeviceType());
        return deviceTypeRepository.save(deviceType);
    }


    /**
     * Find all device types list.
     *
     * @return the list
     */
    public Iterable<DeviceType> findALLDeviceTypes() {
        return deviceTypeRepository.findAll();
    }

    /**
     * Find all device functions iterable.
     *
     * @return the iterable
     */
    public Iterable<DeviceFunction> findALLDeviceFunctions() {
        return deviceFunctionFRepository.findAll();
    }


    /**
     * Find all devices by predicate list.
     *
     * @param deviceDto the device dto
     * @param pageable  the page dto
     * @return the list
     */
    public Page<Device> findAllDevicesByPredicate(DeviceDto deviceDto, Pageable pageable) {
       /* Device device = new Device();
        BeanUtils.copyProperties(deviceDto, device);*/
        return this.deviceRepository.search(deviceDto.getDeviceState(), pageable);
    }


    /**
     * Save policy.
     *
     * @param policyDto the policy dto
     */
    public void savePolicy(PolicyDto policyDto) {
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
        DeviceGroup deviceGroup = new DeviceGroup();
        BeanUtils.copyProperties(groupDto, deviceGroup);
        return this.groupRepository.save(deviceGroup);
    }

    /**
     * 查询所有的场景策略
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
     * Find all devices by name for controlled list.
     * 查询所有被控设备, 同一个PI上面
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
     * 查询 PI上所有被控设备
     *
     * @param piMacAddress the pi mac address
     * @return the list
     */
    public List<Device> findAllDevicesByPiMacAddressForBecontrolled(String piMacAddress) {
        return this.deviceRepository.findAllByPiAndDeviceFunctionType(DeviceFunctionTypeEnum.CONTROLLED, piMacAddress);
    }

    /**
     * Find all devices by mac address and function type list.
     * 查询 PI上所有设备
     *
     * @param functionType the function type   设备的职能类型 : Switch / Sensor/ Controlled
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
    public List<DeviceEvent> findAllDeviceEventBySymbol(String symbol) {
        Device device = this.deviceRepository.findBySymbol(symbol);
        return device.getDeviceType().getDeviceEvents();
    }

    /**
     * Find all device function by symbol list.
     *
     * @param symbol the symbol
     * @return the list
     */
    public List<DeviceFunction> findAllDeviceFunctionBySymbol(String symbol) {
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
     * 假删除,更新策略删除状态
     *
     * @param ID the id
     */
    public void removePolicy(Long ID) {
        Policy policy = this.policyRepository.findOne(ID);
        policy.setDeleted(true);
        policy.setUpdateTime(new Date());
        this.policyRepository.save(policy);
    }

    /**
     * Remove pi.
     *
     * @param ID the id
     */
    public void removePi(Long ID){
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
     * Find devices by type list.
     *
     * @param deviceType the device type
     * @return the list
     */
    public List<Device> findAllDevicesByType(DeviceType deviceType) {
        return this.deviceRepository.findByDeviceType(deviceType);
    }


    public DeviceType findDeviceTypeByName(String typeName){
        return this.deviceTypeRepository.findByName(typeName);
    }

}

