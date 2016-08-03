package com.mydreamplus.smartdevice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydreamplus.smartdevice.domain.PolicyConfigDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/20
 * Time: 下午10:20
 * To change this template use File | Settings | File Templates.
 */
public class PolicyParseUtil {

    private static final Logger log = LoggerFactory.getLogger(PolicyParseUtil.class);

    /**
     * Josn to policy config dto string.
     *
     * @param json the json
     * @return the string
     */
    public static PolicyConfigDto josnToPolicyConfigDto(String json) {
        PolicyConfigDto policyConfigDto = null;
        try {
            policyConfigDto = new ObjectMapper().readValue(json, PolicyConfigDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return policyConfigDto;
    }

}
