package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.api.rest.DeviceController;
import com.mydreamplus.smartdevice.config.MQTTConfig;
import com.mydreamplus.smartdevice.domain.in.PIRegisterRequest;
import com.mydreamplus.smartdevice.util.JsonUtil;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.nio.charset.StandardCharsets;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/8/23
 * Time: 下午6:41
 * To change this template use File | Settings | File Templates.
 */
public class MQTTService {

    private static final Logger log = LoggerFactory.getLogger(MQTTService.class);
    private static MqttAsyncClient asyncClient;

    public static MqttAsyncClient getAsyncClient() {
        return asyncClient;
    }

    public static void setAsyncClient(MqttAsyncClient asyncClient) {
        MQTTService.asyncClient = asyncClient;
    }

    public static void initMQTT(ApplicationContext applicationContext) {

        DeviceController deviceController = (DeviceController) applicationContext.getBean("deviceController");

        String broker = MQTTConfig.getBroker();
        String clientId = MQTTConfig.getClientId();
        String userName = MQTTConfig.getUserName();
        String password = MQTTConfig.getPassword();
        String topic = MQTTConfig.getTopic();
        int qos = MQTTConfig.getQos();
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            asyncClient = new MqttAsyncClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            DisconnectedBufferOptions op = new DisconnectedBufferOptions();
            op.setBufferEnabled(true);
            op.setBufferSize(100);
            asyncClient.setBufferOpts(op);
            connOpts.setCleanSession(true);
            connOpts.setUserName(userName);
            connOpts.setPassword(password.toCharArray());
            connOpts.setCleanSession(true);
            log.info("Connecting to broker: " + broker);
            asyncClient.connect(connOpts).waitForCompletion();
            log.info("Connected");
            asyncClient.subscribe(topic, qos, (s, mqttMessage) -> {
                String content = new String(mqttMessage.getPayload(), StandardCharsets.UTF_8);
                log.info("Client message : " + content);
                JSONObject jsonObject = new JSONObject(content);
                String action = (String) jsonObject.get("action");
                log.info("Action =================>" + action);
                switch (action) {
                    case "regPi": {
                        PIRegisterRequest registerRequest = JsonUtil.getEntity(content, PIRegisterRequest.class);
                        deviceController.registerPi(registerRequest);
                        break;
                    }
                }
            });
        } catch (MqttException me) {
            log.info("reason " + me.getReasonCode());
            log.info("msg " + me.getMessage());
            log.info("loc " + me.getLocalizedMessage());
            log.info("cause " + me.getCause());
            log.info("excep " + me);
            me.printStackTrace();
        }
    }


}
