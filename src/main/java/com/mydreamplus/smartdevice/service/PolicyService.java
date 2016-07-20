package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.config.Constant;
import com.mydreamplus.smartdevice.domain.DeviceRegisterDto;
import com.mydreamplus.smartdevice.domain.PolicyDto;
import com.mydreamplus.smartdevice.domain.in.DeviceRegisterRequest;
import com.mydreamplus.smartdevice.exception.DataInvalidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    //默认场景解析
    public List<PolicyDto> parsePolicy(DeviceRegisterRequest request) {
        if (request.getDeviceRegisters().size() % 2 != 0) {
            throw new DataInvalidException("资源必须成对出现");
        }
        List<PolicyDto> policyDots = new ArrayList<>();
        List<DeviceRegisterDto> deviceRegisterDtos = request.getDeviceRegisters();
        // 拆分设备
        if (request.getParentDeviceType().equals(Constant.PARENT_DEVICE_TYPE_N_SWITCHLIGHTPANEL)) {
            int size = deviceRegisterDtos.size();
            // 前一半主控设备
            List<DeviceRegisterDto> masterDevices = deviceRegisterDtos.subList(0, (size / 2));
            masterDevices.forEach(deviceRegisterDto -> {
                deviceRegisterDto.setDeviceType("Switch");
                log.info("拆分设备:" + deviceRegisterDto.getSymbol() + "::::::" + deviceRegisterDto.getDeviceType());
            });
            // 后一半被控设备
            List<DeviceRegisterDto> slaveDevices = deviceRegisterDtos.subList(size / 2, deviceRegisterDtos.size());
            slaveDevices.forEach(deviceRegisterDto -> {
                deviceRegisterDto.setDeviceType("Relay");
                log.info("拆分设备:" + deviceRegisterDto.getSymbol() + "::::::" + deviceRegisterDto.getDeviceType());
            });
            // 创建策略
            /*for (int i = 0; i <= size / 2; i++) {
                PolicyDto policy1 = new PolicyDto();
                Map<String, String> master1 = new HashMap<>();
                master1.put(masterDevices.get(i).getSymbol(), "PressUp");
                Map<String, String> slave1 = new HashMap<>();
                slave1.put(slaveDevices.get(i).getSymbol(), "Off");
                policy1.setMasterDeviceMap(master1);
                policy1.setSlaveDeviceMap(slave1);

                PolicyDto policy2 = new PolicyDto();
                Map<String, String> master2 = new HashMap<>();
                master2.put(masterDevices.get(i).getSymbol(), "PressDown");
                Map<String, String> slave2 = new HashMap<>();
                slave2.put(slaveDevices.get(i).getSymbol(), "On");
                policy2.setMasterDeviceMap(master2);
                policy2.setSlaveDeviceMap(slave2);

                policyDots.add(policy1);
                policyDots.add(policy2);
            }*/
        }
        return policyDots;
    }

}
