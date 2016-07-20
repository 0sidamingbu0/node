package com.mydreamplus.smartdevice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydreamplus.smartdevice.config.Constant;
import com.mydreamplus.smartdevice.domain.MessageTypeEnum;
import com.mydreamplus.smartdevice.domain.in.DeviceMessageRequest;
import com.mydreamplus.smartdevice.domain.message.DeviceMessage;
import com.mydreamplus.smartdevice.domain.message.WebSocketMessageResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午4:22
 * To change this template use File | Settings | File Templates.
 */

@Service
public class DeviceRestService {

    private final Logger log = LoggerFactory.getLogger(DeviceRestService.class);
    private final static String SUCCESS = "Success";
    private final static String FAILURE = "Failure";

    /**
     * 设备注册成功反馈
     * @param piMacAddress
     * @param shortAddress
     */
    @Async(value = "messageExecutor")
    public void registerFeedback(String piMacAddress, int shortAddress) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/register");
        deviceMessage.setMessage(SUCCESS);
        deviceMessage.setMessageType(MessageTypeEnum.FEEDBACK);
        deviceMessage.setPiAddress(piMacAddress);
        deviceMessage.setShortAddress(shortAddress);
        send(deviceMessage);
        log.info(":::::::::注册设备反馈");
    }


    /**
     * 删除设备
     * @param piMacAddress
     * @param shortAddress
     */
    @Async(value = "messageExecutor")
    public void removeDevice(String piMacAddress, int shortAddress) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/delete");
        deviceMessage.setMessageType(MessageTypeEnum.DELETE);
        deviceMessage.setPiAddress(piMacAddress);
        deviceMessage.setMessage(SUCCESS);
        deviceMessage.setShortAddress(shortAddress);
        send(deviceMessage);
        log.info(":::::::::删除设备");
    }

    /**
     * 允许设备60s加入
     */
    @Async(value = "messageExecutor")
    public void permit(String piMacAddress) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/permit");
        deviceMessage.setMessageType(MessageTypeEnum.FEEDBACK);
        deviceMessage.setPiAddress(piMacAddress);
        send(deviceMessage);
        log.info(":::::::::允许设备60秒注册过来");
    }


    /**
     * 获取设备状态信息
     */
    @Async(value = "messageExecutor")
    public void getDeviceState (String piMacAddress, int shortAddress) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/state");
        deviceMessage.setShortAddress(shortAddress);
        deviceMessage.setMessageType(MessageTypeEnum.GET_INFO);
        deviceMessage.setPiAddress(piMacAddress);
        deviceMessage.setMessage(SUCCESS);
        send(deviceMessage);
        log.info(":::::::::获取设备注册信息");
    }


    /**
     *
     * @param piMacAddress
     * @param symbol
     * @param command
     */
    @Async(value = "messageExecutor")
    public void sendCommandToDevice (String piMacAddress, String symbol, String command) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/command");
        deviceMessage.setMessageType(MessageTypeEnum.COMMAND);
        deviceMessage.setPiAddress(piMacAddress);
        Map<String, Object> data = new HashMap<>();
        data.put("command", command);
        deviceMessage.setMessage(SUCCESS);
        deviceMessage.setData(data);
        deviceMessage.setSymbol(symbol);
        send(deviceMessage);
        log.info(":::::::::对设备下发命令:" + command);
    }




    /**
     *
     * @param message
     */
    private static void send(DeviceMessage message) {
        String url = "http://10.28.0.179:8089/api/websocket/sendMessageToClient";
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ObjectMapper mapper = new ObjectMapper();
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("deviceId", message.getPiAddress());
            jsonObj.put("message", mapper.writeValueAsString(message));
            System.out.println(">>>>>>>>>>>>>>>> JSON credentials " + jsonObj.toString());
            HttpEntity<String> entityCredentials = new HttpEntity<>(jsonObj.toString(), httpHeaders);
            ResponseEntity<WebSocketMessageResponse> responseEntity = restTemplate.exchange(url,
                    HttpMethod.POST, entityCredentials, WebSocketMessageResponse.class);
            if (responseEntity != null) {
                WebSocketMessageResponse response = responseEntity.getBody();
                System.out.println(response.getSuccess() + response.getErrorMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String pi = "f4:5c:89:c4:24:c7";
        int shortAddress = 2054;

        DeviceRestService deviceRestService = new DeviceRestService();
        /*DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/register");
        deviceMessage.setMessage(SUCCESS);
        deviceMessage.setPiAddress("f4:5c:89:c4:24:c7");
        send(deviceMessage);*/

        /**
         * 删除
         */
        deviceRestService.removeDevice(pi, shortAddress);

        /**
         * 下发命令
         */
        deviceRestService.sendCommandToDevice(pi, "00:12:4b:00:0a:b8:e4:2e-2054-6", "1");
        /**
         * 获取状态
         */
        deviceRestService.getDeviceState(pi, 6);
    }
}
