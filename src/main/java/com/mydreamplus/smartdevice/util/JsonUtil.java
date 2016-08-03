package com.mydreamplus.smartdevice.util;

import com.mydreamplus.smartdevice.domain.message.DeviceMessage;
import com.mydreamplus.smartdevice.exception.DataInvalidException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/20
 * Time: 下午10:20
 * To change this template use File | Settings | File Templates.
 */
public class JsonUtil {

    /**
     * Object to json string string.
     *
     * @param object the object
     * @return the string
     */
    public static String toJsonString(Object object){
        JSONObject jsonObject = new JSONObject(object);
        return jsonObject.toString();
    }
}
