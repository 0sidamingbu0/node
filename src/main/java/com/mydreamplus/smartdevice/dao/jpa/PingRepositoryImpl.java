package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.domain.PingDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 * 储存ping值,后期做负载的时候可以放到redis中
 */
public class PingRepositoryImpl {

//    private static final Logger log = LoggerFactory.getLogger(PingRepositoryImpl.class);

    private static Map<String, PingDto> map = new HashMap<>();


    /**
     * Gets ping.
     *
     * @param shortAddress the short address
     * @return the ping
     */
    public static PingDto getPing(int shortAddress) {
        return map.get(shortAddress);
    }

    /**
     * Sets ping.
     *
     * @param macAddress the mac address
     * @param ping       the ping
     */
    public static void setPing(String macAddress, PingDto ping) {
        map.put(macAddress, ping);
    }
}
