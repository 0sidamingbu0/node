package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.config.DoorApi;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/18
 * Time: 下午7:44
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ExternalAPIService {


    private static final int TIME_OUT = 5000;
    private final static Logger log = LoggerFactory.getLogger(ExternalAPIService.class);
    private String host = "http://dev.so.aws.mxj.mx/";

    private static Object callApi(Map<String, String> map, String url) {
        log.info("Send message to url: {}", url);
        Object response = null;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(TIME_OUT);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        try {
            JSONObject jsonObj = new JSONObject(map);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>{}", jsonObj.toString());
            HttpEntity<String> entityCredentials = new HttpEntity<>(jsonObj.toString(), httpHeaders);
            ResponseEntity<ResponseMessage> responseEntity = restTemplate.exchange(url,
                    HttpMethod.POST, entityCredentials, ResponseMessage.class);
            if (responseEntity != null) {
                response = responseEntity.getBody();
                log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< {}", response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        ExternalAPIService externalAPIService = new ExternalAPIService();
//        externalAPIService.checkPermissionDoorPassword("1", "1", "Door");
//        externalAPIService.checkPermissionDoorCard("1", "2", "Door");
//        externalAPIService.checkPermissionDoor("1","2","card","http://qa.so.aws.mxj.mx/");

//        externalAPIService.proxySensorData(300, "f4:5c:89:c4:24:c7", "PM2.5Sensor", "http://...");
        Map<String, String> data = new HashMap<>();
        data.put("data", "12321");
        data.put("piMacAddress", "12:21:21:22:33");
        data.put("type", "ReportCardId");
        // 设备的类型
        data.put("deviceType", "Door");
        externalAPIService.checkPermission(data, "http://10.28.0.115:9090/api/door/authentication/card");
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Check permission door password boolean.
     * <p>
     * 正确返回
     * { "code": 1, "message": "success" }
     * 错误返回
     * code: -2 随机密码错误，无微信消息
     * code:-3 门被关闭,微信提示 ：当前门禁已关闭
     * code:-4 门被禁用， 微信提示：当前门被禁用
     * code:-5 没有门禁权限微信提示：门禁权限不足
     * code:-6 没有找到门,如果输入密码正确，微信提示:请绑定门禁: 门禁mac地址.
     *
     * @param password     the password
     * @param piMacAddress the pi mac address
     * @return the boolean
     */
    public boolean checkPermissionDoorPassword(String password, String piMacAddress, String deviceType) {
        String url = host + DoorApi.DOOR_PASSWORD_API;
        Map<String, String> map = new HashMap<>();
        map.put("password", password);
        map.put("piMacAddress", piMacAddress);
        map.put("deviceType", deviceType);
        ResponseMessage responseMessage = (ResponseMessage) callApi(map, url);
        if (responseMessage.getCode() != 1) {
            log.info("密码验证失败:{}", responseMessage.getMessage());
            return false;
        } else {
            log.info("密码验证成功!");
            return true;
        }
    }

    /**
     * Check permission door card boolean.
     * <p>
     * 错误返回
     * code: -2 门禁卡号错误
     * code:-3 门被关闭
     * code:-4 门被禁用
     * code:-5 没有门禁权限
     * code:-6 没有找到门
     * code: -1 其他错误，如参数错误等
     *
     * @param card         the card
     * @param piMacAddress the pi mac address
     * @return the boolean
     */
    public boolean checkPermissionDoorCard(String card, String piMacAddress, String deviceType) {
        String url = host + DoorApi.DOOR_CARD_API;
        Map<String, String> map = new HashMap<>();
        map.put("card", card);
        map.put("piMacAddress", piMacAddress);
        map.put("deviceType", deviceType);
        ResponseMessage responseMessage = (ResponseMessage) callApi(map, url);
        if (responseMessage.getCode() != 1) {
            log.info("卡号验证失败:{}", responseMessage.getMessage());
            return false;
        } else {
            log.info("卡号验证成功!");
            return true;
        }
    }

    /**
     * 发送数据
     *
     * @param value
     * @param macAddress
     */
    public void proxySensorData(float value, String macAddress, String deviceType, String url) {
        Map<String, String> map = new HashMap<>();
        map.put("value", String.valueOf(value));
        map.put("macAddress", macAddress);
        map.put("deviceType", deviceType);
        this.callApi(map, url);
    }

    /**
     * Check permission door card boolean.
     * <p>
     * 错误返回
     *
     * @param data         the card
     * @param piMacAddress the pi mac address 门设备的mac地址
     * @return the boolean
     */
    public boolean checkPermissionDoor(String data, String piMacAddress, String eventType, String url) {
        Map<String, String> map = new HashMap<>();
        map.put("data", data);
        map.put("piMacAddress", piMacAddress);
        map.put("type", eventType);
        ResponseMessage responseMessage = (ResponseMessage) callApi(map, url + DoorApi.DOOR_EVENT_API);
        if (responseMessage.getCode() != 1) {
            log.info("卡号验证失败:{} ,{}", responseMessage.getMessage(), eventType);
            return false;
        } else {
            log.info("卡号验证成功!, {}", eventType);
            return true;
        }
    }

    /**
     * 验证API
     *
     * @param data
     * @param url
     * @return
     */
    public boolean checkPermission(Map<String, String> data, String url) {
        ResponseMessage responseMessage = (ResponseMessage) callApi(data, url);
        if (responseMessage.getCode() != 1) {
            log.info("验证失败!");
            return false;
        } else {
            log.info("验证成功!");
            return true;
        }
    }


    /**
     * The type Response message.
     */
    static class ResponseMessage {
        private int code;
        private String message;

        /**
         * Gets code.
         *
         * @return the code
         */
        public int getCode() {
            return code;
        }

        /**
         * Sets code.
         *
         * @param code the code
         */
        public void setCode(int code) {
            this.code = code;
        }

        /**
         * Gets message.
         *
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Sets message.
         *
         * @param message the message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    static class ResponseDoorCode {
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

}
