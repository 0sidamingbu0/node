package com.mydreamplus.smartdevice.dao.jpa;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 * 传感器上报的当前值,后期做负载的时候可以放到redis中
 */
public class SensorRepositoryImpl {

    /**
     * 例: < Symbol <温度:27> >
     */
    private static Map<String, Map<String, String>> map = new HashMap<>();

    /**
     * Add value.
     *
     * @param symbol the symbol
     * @param data    the map
     */
    public static void addValue(String symbol, Map<String, String> data) {
        map.put(symbol, data);
    }

    /**
     * Get value map.
     *
     * @param symbol the symbol
     * @return the map
     */
    public static Map<String, String> getValue(String symbol) {
        return map.get(symbol);
    }
}
