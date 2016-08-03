package com.mydreamplus.smartdevice.dao.jpa;

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
public class LinkQualityRepositoryImpl {

//    private static final Logger log = LoggerFactory.getLogger(PingRepositoryImpl.class);

    private static Map<String, Integer> map = new HashMap<>();

    /**
     * Gets link quality.
     *
     * @param macAddress the mac address
     * @return the link quality
     */
    public static int getLinkQuality(String macAddress) {
        if (map.get(macAddress) == null) {
            return 0;
        } else {
            return map.get(macAddress);
        }
    }

    /**
     * Sets link quality.
     *
     * @param macAddress  the mac address
     * @param linkQuality the link quality
     */
    public static void setLinkQuality(String macAddress, int linkQuality) {
        map.put(macAddress, linkQuality);
    }

}
