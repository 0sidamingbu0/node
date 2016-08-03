package com.mydreamplus.smartdevice.util;

import com.mydreamplus.smartdevice.exception.DataInvalidException;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/20
 * Time: 下午10:20
 * To change this template use File | Settings | File Templates.
 */
public class SymbolUtil {

    /**
     * Parse mac address string.
     *
     * @param symbol the symbol
     * @return the string
     */
    public static String parseMacAddress(String symbol) {
        String s;
        if (StringUtils.isEmpty(symbol)) {
            throw new DataInvalidException("symbol不能为空");
        } else {
            String[] arr = symbol.split("-");
            s = arr[0];
        }
        return s;
    }

    /**
     * Parse index int.
     *
     * @param symbol the symbol
     * @return the int
     */
    public static int parseIndex(String symbol) {
        int s;
        if (StringUtils.isEmpty(symbol)) {
            throw new DataInvalidException("symbol不能为空");
        } else {
            String[] arr = symbol.split("-");
            s = Integer.parseInt(arr[1]);
        }
        return s;
    }

}
