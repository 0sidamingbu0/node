package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.config.Constant;
import com.mydreamplus.smartdevice.dao.jpa.PolicyRepository;
import com.mydreamplus.smartdevice.domain.ConditionAndSlaveDto;
import com.mydreamplus.smartdevice.domain.PolicyConfigDto;
import com.mydreamplus.smartdevice.domain.condition.BaseCondition;
import com.mydreamplus.smartdevice.domain.in.CommonDeviceRequest;
import com.mydreamplus.smartdevice.entity.Device;
import com.mydreamplus.smartdevice.entity.Policy;
import com.mydreamplus.smartdevice.util.JsonUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DoorHelper {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceManager deviceManager;

    @Autowired
    private PolicyRepository policyRepository;


    public void registerDoor(CommonDeviceRequest commonDeviceRequest) {

        // 老版门禁
        if (commonDeviceRequest.getDeviceType().equals(Constant.OLD_DOOR_TYPE)) {
            deviceService.registerCommonDevice(commonDeviceRequest);
            Device door = this.deviceManager.getDevice(commonDeviceRequest.getMacAddress());
            String policyName = "Default_" + door.getDeviceType().getName() + "_" + door.getMacAddress();
            // 如果默认策略不存在则创建默认策略
            if (this.policyRepository.findByNameAndDeleted(policyName, false) == null) {
                Policy policy = new Policy();
                JSONObject jsonObject = new JSONObject(door.getAdditionalAttributes());
                String apiUrl = (String) jsonObject.get(Constant.API_CONDITION_URL);
                policy.setName(policyName);
                policy.setDescription("门禁默认场景");
                policy.setPi(null);
                PolicyConfigDto policyConfigDto = new PolicyConfigDto();
                Map<String, String> masterDeviceMap = new HashMap<>();
                masterDeviceMap.put(door.getMacAddress(), Constant.DEVICE_EVENT_REPORT_PASSWORD_OR_CARD);
                policyConfigDto.setMasterDeviceMap(masterDeviceMap);
                Map<String, String> slaveDeviceMap = new HashMap<>();
                slaveDeviceMap.put(door.getMacAddress(), Constant.DEVICE_FUNCTION_OPEN_DOOR);
                policyConfigDto.setMasterDeviceMap(masterDeviceMap);
                ConditionAndSlaveDto conditionAndSlaveDto = new ConditionAndSlaveDto();
                conditionAndSlaveDto.setSlaveDeviceMap(slaveDeviceMap);
                List<BaseCondition> conditions = new ArrayList<>();
                BaseCondition condition = new BaseCondition();
                condition.setConditionType("CONTROLLED");
                condition.setConditionTypeId(UUID.randomUUID().toString());
                condition.setLogic(Constant.LOGIC_TRUE);
                condition.setUri(apiUrl);
                conditions.add(condition);
                conditionAndSlaveDto.setConditions(conditions);
                List<ConditionAndSlaveDto> conditionAndSlaveDtos = new ArrayList<>();
                conditionAndSlaveDtos.add(conditionAndSlaveDto);
                policyConfigDto.setConditionAndSlaveDtos(conditionAndSlaveDtos);
                policy.setPolicyConfig(JsonUtil.toJsonString(policyConfigDto));
                policy.setDefaultPolicy(true);
                policy.setMasterEvent(masterDeviceMap.toString());
                policy.setDeleted(false);
                policy.setRootPolicy(true);
                this.policyRepository.save(policy);
            }
        }
    }
}

