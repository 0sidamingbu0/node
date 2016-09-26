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

    private static final String STATUS_SUCCESS = "1";
    private static final String STATUS_FAILURE = "-1";

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceManager deviceManager;

    @Autowired
    private PolicyRepository policyRepository;


    /**
     * Register door.
     *
     * @param commonDeviceRequest the common device request
     */
    public void registerDoor(CommonDeviceRequest commonDeviceRequest) {

        // 老版门禁
        if (commonDeviceRequest.getDeviceType().equals(Constant.OLD_DOOR_TYPE) || commonDeviceRequest.getDeviceType().equals(Constant.MEET_ROOM_DOOR_TYPE)) {
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
                masterDeviceMap.put(door.getMacAddress(), Constant.DEVICE_EVENT_REPORT_CARD);
                policyConfigDto.setMasterDeviceMap(masterDeviceMap);
                List<ConditionAndSlaveDto> conditionAndSlaveDtos = new ArrayList<>();

                /*
                 * 验证成功
                 */
                conditionAndSlaveDtos.add(setConditon(door.getMacAddress(), apiUrl, Constant.DEVICE_FUNCTION_OPEN_DOOR, STATUS_SUCCESS));
                /*
                 * 条件验证失败删除卡号
                 */
                conditionAndSlaveDtos.add(setConditon(door.getMacAddress(), apiUrl, Constant.DEVICE_FUNCTION_REMOVE_CARD, STATUS_FAILURE));

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

    /**
     * 设置成功,验证卡号
     *
     * @param slaveSymbol
     * @param apiUrl
     * @return
     */
    private ConditionAndSlaveDto setConditon(String slaveSymbol, String apiUrl, String event, String status) {
        Map<String, String> slaveDeviceMap = new HashMap<>();
        slaveDeviceMap.put(slaveSymbol, event);
        ConditionAndSlaveDto conditionAndSlaveDto = new ConditionAndSlaveDto();
        conditionAndSlaveDto.setSlaveDeviceMap(slaveDeviceMap);
        List<BaseCondition> conditions = new ArrayList<>();
        BaseCondition condition = new BaseCondition();
        condition.setConditionType("CONTROLLED");
        condition.setConditionTypeId(UUID.randomUUID().toString());
        condition.setStatus(status);
        condition.setUri(apiUrl);
        conditions.add(condition);
        conditionAndSlaveDto.setConditions(conditions);
        return conditionAndSlaveDto;
    }
}

