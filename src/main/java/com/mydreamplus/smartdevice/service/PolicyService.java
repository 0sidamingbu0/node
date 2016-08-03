package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.config.Constant;
import com.mydreamplus.smartdevice.dao.jpa.PIRespository;
import com.mydreamplus.smartdevice.dao.jpa.PolicyRepository;
import com.mydreamplus.smartdevice.domain.ConditionAndSlaveDto;
import com.mydreamplus.smartdevice.domain.DeviceRegisterDto;
import com.mydreamplus.smartdevice.domain.PolicyConfigDto;
import com.mydreamplus.smartdevice.domain.PolicyDto;
import com.mydreamplus.smartdevice.domain.in.DeviceRegisterRequest;
import com.mydreamplus.smartdevice.entity.PI;
import com.mydreamplus.smartdevice.entity.Policy;
import com.mydreamplus.smartdevice.exception.DataInvalidException;
import com.mydreamplus.smartdevice.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/18
 * Time: 下午7:44
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PolicyService {

    private final Logger log = LoggerFactory.getLogger(PolicyService.class);
    private final String PRESS_UP_EVENT = "PressUp";
    private final String PRESS_EVENT_REVERSE = "Reverse";

    private final String POLICY_DESCRIPTION = "默认场景";
    @Autowired
    private PIRespository piRespository;
    @Autowired
    private PolicyRepository policyRepository;

    /**
     * Parse and save policy list.
     *
     * @param request the request
     * @return the list
     */
//默认场景解析
    public List<PolicyDto> parseAndSavePolicy(DeviceRegisterRequest request) {

        if (request == null || StringUtils.isEmpty(request.getPiMacAddress())) {
            throw new DataInvalidException("PIMac地址不完整");
        }

        if (request == null || StringUtils.isEmpty(request.getMacAddress())) {
            throw new DataInvalidException("Mac地址不完整");
        }

        PI pi = piRespository.findByMacAddress(request.getPiMacAddress());
        String macAddress = request.getMacAddress();

        log.info("设备类型:" + request.getParentDeviceType());
//        if (request.getDeviceRegisters().size() % 2 != 0) {
//            throw new DataInvalidException("资源必须成对出现");
//        }
        List<PolicyDto> policyDots = new ArrayList<>();
        List<DeviceRegisterDto> deviceRegisterDtos = request.getDeviceRegisters();
        // 拆分设备
        if (request.getParentDeviceType().equals(Constant.PARENT_DEVICE_TYPE_N_SWITCHLIGHTPANEL) ||
                request.getParentDeviceType().equals(Constant.PARENT_DEVICE_TYPE_N_SWITCHPANEL)) {
            int size = deviceRegisterDtos.size();
            // 前一半主控设备
            List<DeviceRegisterDto> masterDevices = deviceRegisterDtos.subList(0, (size / 2));
            masterDevices.forEach(deviceRegisterDto -> {
                deviceRegisterDto.setDeviceType("Switch");
                log.info("拆分设备:::::::" + deviceRegisterDto.getDeviceType());
            });
            // 后一半被控设备
            List<DeviceRegisterDto> slaveDevices = deviceRegisterDtos.subList(size / 2, deviceRegisterDtos.size());
            slaveDevices.forEach(deviceRegisterDto -> {
                deviceRegisterDto.setDeviceType("Relay");
                log.info("拆分设备:::::::" + deviceRegisterDto.getDeviceType());
            });

            // 创建默认场景
            for (int i = 0; i < size / 2; i++) {
                Policy policy = new Policy();
                policy.setName("Default_" + masterDevices.get(i).getDeviceType() + "_Controll_" +
                        slaveDevices.get(i).getDeviceType() + "_" + macAddress + "_" + (i + 1));
                policy.setDescription(POLICY_DESCRIPTION);
                policy.setPi(pi);
                PolicyConfigDto policyConfigDto = new PolicyConfigDto();
                Map<String, String> masterDeviceMap = new HashMap<>();
                masterDeviceMap.put(macAddress + "-" + masterDevices.get(i).getIndex(), PRESS_UP_EVENT);
                policyConfigDto.setMasterDeviceMap(masterDeviceMap);
                Map<String, String> slaveDeviceMap = new HashMap<>();
                slaveDeviceMap.put(macAddress + "-" + slaveDevices.get(i).getIndex(), PRESS_EVENT_REVERSE);
                policyConfigDto.setMasterDeviceMap(masterDeviceMap);
                ConditionAndSlaveDto conditionAndSlaveDto = new ConditionAndSlaveDto();
                conditionAndSlaveDto.setSlaveDeviceMap(slaveDeviceMap);
                List<ConditionAndSlaveDto> conditionAndSlaveDtos = new ArrayList<>();
                conditionAndSlaveDtos.add(conditionAndSlaveDto);
                policyConfigDto.setConditionAndSlaveDtos(conditionAndSlaveDtos);
                policy.setPolicyConfig(JsonUtil.toJsonString(policyConfigDto));
                policy.setDefaultPolicy(true);
                policy.setMasterEvent(masterDeviceMap.toString());
                this.saveOrUpdatePolicy(policy);
                PolicyDto policyDto = new PolicyDto();
                policyDto.setPi(pi);
                policyDto.setName(policy.getName());
                policyDto.setPolicyConfigDto(policyConfigDto);
                policyDots.add(policyDto);
            }
        } else { // 不需要拆分设备
            deviceRegisterDtos.forEach(deviceRegisterDto -> deviceRegisterDto.setDeviceType(request.getParentDeviceType()));
        }
        return policyDots;
    }


    /**
     * 保存策略,如果策略存在则更新
     * @param policy
     */
    private void saveOrUpdatePolicy(Policy policy) {
        Policy source = this.policyRepository.findByName(policy.getName());
        if (source == null) {
            this.policyRepository.save(policy);
        } else {
            log.info("策略:s% 已经存在!", policy.getName());
            source.setUpdateTime(new Date());
            this.policyRepository.save(source);
        }
    }

}
