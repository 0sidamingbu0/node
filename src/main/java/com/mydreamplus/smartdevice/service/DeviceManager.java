package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.dao.jpa.*;
import com.mydreamplus.smartdevice.domain.PageDto;
import com.mydreamplus.smartdevice.domain.PolicyDto;
import com.mydreamplus.smartdevice.domain.in.DeviceQueryRequest;
import com.mydreamplus.smartdevice.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;


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

    /**
     * Find device event by id device event.
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
        return deviceEventRepository.save(deviceEvent);
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
     * @param deviceType the device type
     * @return the device type
     */
    public DeviceType saveDeviceType(DeviceType deviceType) {
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
     * Find all devices by predicate iterable.
     *
     * @param request the request
     * @param pageDto the page dto
     * @return the iterable
     */
    public Iterable<Device> findAllDevicesByPredicate(DeviceQueryRequest request, PageDto pageDto) {
        return new ArrayList<>();
    }

    /**
     * Save policy.
     *
     * @param policyDto the policy dto
     */
    public void savePolicy(PolicyDto policyDto) {
        Policy policy = new Policy();
        BeanUtils.copyProperties(policyDto, policy ,"rootPolicy");
        policyRepository.save(policy);
    }
}
