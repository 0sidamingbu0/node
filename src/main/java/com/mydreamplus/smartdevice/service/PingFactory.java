package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.domain.PingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 */
public class PingFactory {

    private static final Logger log = LoggerFactory.getLogger(PingFactory.class);

    private static Map<String, PingDto> map = new HashMap<>();

    /**
     * Get ping ping dto.
     *
     * @param macAddress the mac address
     * @return the ping dto
     */
    public static PingDto getPing(String macAddress){
        if(StringUtils.isEmpty(macAddress)){
            log.info(String.format("获取Ping失败, mac地址为空"));
            return new PingDto(0);
        }else{
            return map.get(macAddress);
        }
    }

    public static void setPing(String macAddress, PingDto ping){
        if(StringUtils.isEmpty(macAddress) || ping == null){
            log.info("存储Ping失败");
        }else{
            map.put(macAddress, ping);
        }
    }
}
