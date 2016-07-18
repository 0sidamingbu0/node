package com.mydreamplus.smartdevice.util;

import com.mydreamplus.smartdevice.domain.PolicyDto;
import com.mydreamplus.smartdevice.domain.in.DeviceRegisterRequest;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/18
 * Time: 下午7:06
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PolicyUtil {

    private static final String DEFAULT_POLICY_NAME = "Default Policy Name";



    public static PolicyDto parseDefaultPolicy(DeviceRegisterRequest deviceRegisterRequest) {
        PolicyDto policy = new PolicyDto();
        policy.setName(DEFAULT_POLICY_NAME);
        policy.setHasRootPolicy(false);

        deviceRegisterRequest.getDeviceRegisters().forEach(deviceRegisterDto -> {
            deviceRegisterDto.getSymbol();
        });
//        policy.setMasterDeviceMap();
//        policy.setSlaveDeviceMap();
        return policy;
    }
}