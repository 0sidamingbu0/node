package com.mydreamplus.smartdevice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydreamplus.smartdevice.config.Constant;
import com.mydreamplus.smartdevice.domain.MessageTypeEnum;
import com.mydreamplus.smartdevice.domain.PolicyConfigDto;
import com.mydreamplus.smartdevice.domain.message.DeviceMessage;
import com.mydreamplus.smartdevice.domain.message.PolicyMessage;
import com.mydreamplus.smartdevice.domain.message.WebSocketMessageResponse;
import com.mydreamplus.smartdevice.entity.Device;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午4:22
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DeviceRestService {

    private final static String SUCCESS = "Success";
    private final static String FAILURE = "Failure";
    private static final int TIME_OUT = 5000;
    private final static Logger log = LoggerFactory.getLogger(DeviceRestService.class);

    /**
     * @param message
     */
    private static void send(DeviceMessage message) {
        String url = "http://localhost:8089/api/websocket/sendMessageToClient";
//        String url = Constant.WEBSOCKET_SERVICE_URI + Constant.WEBSOCKET_SERVICE_API;
        log.info("Send message to url: {}", url);
        int timeout = TIME_OUT;
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
//            log.info(">>>>>>>>>>>>>>>> JSON >>>>> " + jsonObj.toString());
            HttpEntity<String> entityCredentials = new HttpEntity<>(jsonObj.toString(), httpHeaders);
            ResponseEntity<WebSocketMessageResponse> responseEntity = restTemplate.exchange(url,
                    HttpMethod.POST, entityCredentials, WebSocketMessageResponse.class);
            if (responseEntity != null) {
                WebSocketMessageResponse response = responseEntity.getBody();
                log.info(response.getSuccess() + response.getErrorMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 配置Android TV
     *
     * @param device the device
     */
    @Async(value = "messageExecutor")
    public void sendConfigProperty(Device device) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/config");
        deviceMessage.setMessage(SUCCESS);
        deviceMessage.setMessageType(MessageTypeEnum.CONFIG);
        deviceMessage.setPiAddress(device.getMacAddress());
        deviceMessage.setConfig(device.getAdditionalAttributes());
        send(deviceMessage);
        log.info(":::::::::配置 Android TV!");
    }


    /**
     * 设 注册成功反馈
     *
     * @param piMacAddress the pi mac address
     * @param macAddress   设的mac
     */
    @Async(value = "messageExecutor")
    public void registerFeedback(String piMacAddress, String macAddress) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/register");
        deviceMessage.setMessage(SUCCESS);
        deviceMessage.setMessageType(MessageTypeEnum.FEEDBACK);
        deviceMessage.setPiAddress(piMacAddress);
        deviceMessage.setMacAddress(macAddress);
        send(deviceMessage);
        log.info(":::::::::注册设备反馈!");
    }


    @Async(value = "messageExecutor")
    public void registerFeedback(String macAddress) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/register");
        deviceMessage.setMessage(SUCCESS);
        deviceMessage.setMessageType(MessageTypeEnum.FEEDBACK);
        deviceMessage.setPiAddress(macAddress);
        send(deviceMessage);
        log.info(":::::::::注册设备反馈!");
    }

    /**
     * 注册PI成功反馈
     *
     * @param piMacAddress the pi mac address
     */
    @Async(value = "messageExecutor")
    public void registerPiFeedback(String piMacAddress) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setServerTime(System.currentTimeMillis());
        deviceMessage.setAction("/registerPi");
        deviceMessage.setMessage(SUCCESS);
        deviceMessage.setMessageType(MessageTypeEnum.FEEDBACK);
        deviceMessage.setPiAddress(piMacAddress);
        send(deviceMessage);
        log.info(":::::::::注册PI成功反馈:" + piMacAddress);
    }


    /**
     * 删除设
     *
     * @param piMacAddress the pi mac address
     * @param macAddress   the mac address
     */
    @Async(value = "messageExecutor")
    public void removeDevice(String piMacAddress, String macAddress) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/delete");
        deviceMessage.setMessageType(MessageTypeEnum.DELETE);
        deviceMessage.setPiAddress(piMacAddress);
        deviceMessage.setMessage(SUCCESS);
        deviceMessage.setMacAddress(macAddress);
        send(deviceMessage);
        log.info(":::::::::删除设备");
    }

    /**
     * 允许设备加入网络 60s
     *
     * @param piMacAddress the pi mac address
     */
    @Async(value = "messageExecutor")
    private void permit(String piMacAddress) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/permit");
        deviceMessage.setMessageType(MessageTypeEnum.SEND_POLICY);
        deviceMessage.setPiAddress(piMacAddress);
        deviceMessage.setMessage(SUCCESS);
        send(deviceMessage);
        log.info(":::::::::开放网关60秒,允许设备入网");
    }

    /**
     * 允许设 加入网络 60s
     *
     * @param piMacAddress the pi mac address
     * @param minute       the minute
     */
    @Async(value = "messageExecutor")
    public void permitByMinute(String piMacAddress, int minute) {
        for (int i = 0; i <= minute; i++) {
            this.permit(piMacAddress);
            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取设 状态信息
     *
     * @param piMacAddress the pi mac address
     * @param macAddress   the mac address
     */
    @Async(value = "messageExecutor")
    public void getDeviceState(String piMacAddress, String macAddress) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/state");
        deviceMessage.setMacAddress(macAddress);
        deviceMessage.setMessageType(MessageTypeEnum.GET_INFO);
        deviceMessage.setPiAddress(piMacAddress);
        deviceMessage.setMessage(SUCCESS);
        send(deviceMessage);
        log.info(":::::::::获取设备状态信息");
    }

    /**
     * Ping
     *
     * @param piMacAddress the pi mac address
     * @param macAddress   the mac address
     */
    @Async(value = "messageExecutor")
    public void getPing(String piMacAddress, String macAddress) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/ping");
        deviceMessage.setMacAddress(macAddress);
        Map<String, Object> data = new HashMap<>();
        data.put("serverTimeStamp", System.currentTimeMillis());
        deviceMessage.setData(data);
        deviceMessage.setMessageType(MessageTypeEnum.GET_PING);
        deviceMessage.setPiAddress(piMacAddress);
        deviceMessage.setMessage(SUCCESS);
        send(deviceMessage);
        log.info(":::::::::获取设备PING值");
    }

    /**
     * Command
     *
     * @param piMacAddress the pi mac address
     * @param symbol       the symbol
     * @param command      the command
     */
    @Async(value = "messageExecutor")
    public void sendCommandToDevice(String piMacAddress, String symbol, String command) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/command");
        deviceMessage.setMessageType(MessageTypeEnum.COMMAND);
        deviceMessage.setPiAddress(piMacAddress);
        Map<String, Object> data = new HashMap<>();
        data.put("command", command);
        data.put("symbol", symbol);
        deviceMessage.setMessage(SUCCESS);
        deviceMessage.setData(data);
        send(deviceMessage);
        log.info(":::::::::对设备下发命令:" + command);
    }


    /**
     * 让传感 上报数据
     *
     * @param piMacAddress the pi mac address
     * @param symbol       the pi mac address
     */
    @Async(value = "messageExecutor")
    public void getData(String piMacAddress, String symbol) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/getData");
        deviceMessage.setMessageType(MessageTypeEnum.COMMAND);
        deviceMessage.setPiAddress(piMacAddress);
        Map<String, Object> data = new HashMap<>();
        data.put("symbol", symbol);
        deviceMessage.setData(data);
        deviceMessage.setMessage(SUCCESS);
        send(deviceMessage);
        log.info("::::::::::下发命令, 获取测量数据:" + piMacAddress);
    }

    /**
     * 下发 景策略
     *
     * @param piMacAddress      the pi mac address
     * @param policyMessageList the policy message list
     */
    @Async(value = "messageExecutor")
    public void sendPolicy(String piMacAddress, List<PolicyMessage> policyMessageList) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setAction("/policy");
        deviceMessage.setMessageType(MessageTypeEnum.SEND_POLICY);
        deviceMessage.setPiAddress(piMacAddress);
        Map<String, Object> data = new HashMap<>();
        data.put("policyConfigDtos", policyMessageList);
        deviceMessage.setData(data);
        deviceMessage.setMessage(SUCCESS);
        send(deviceMessage);
        log.info("::::::::::: 下发策略:" + piMacAddress);
    }

    /**
     * Send policy.
     *
     * @param piMacAddress    the pi mac address
     * @param policyConfigDto the policy config dto
     */
    @Async(value = "messageExecutor")
    public void sendPolicy(String piMacAddress, PolicyConfigDto policyConfigDto) {
        List<PolicyMessage> policyMessageList = new ArrayList<>();
        PolicyMessage message = new PolicyMessage();
        message.setUpdateTime(System.currentTimeMillis());
        message.setPolicyConfigDto(policyConfigDto);
        policyMessageList.add(message);
        this.sendPolicy(piMacAddress, policyMessageList);
    }
}
