package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.api.rest.DeviceController;
import com.mydreamplus.smartdevice.config.MQTTConfig;
import com.mydreamplus.smartdevice.dao.jpa.DeviceRepository;
import com.mydreamplus.smartdevice.dao.jpa.PIRespository;
import com.mydreamplus.smartdevice.domain.DeviceStateEnum;
import com.mydreamplus.smartdevice.domain.in.*;
import com.mydreamplus.smartdevice.entity.Device;
import com.mydreamplus.smartdevice.entity.PI;
import com.mydreamplus.smartdevice.util.JsonUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/8/23
 * Time: 下午6:41
 * To change this template use File | Settings | File Templates.
 */
public class MQTTService {

    private static final Logger log = LoggerFactory.getLogger(MQTTService.class);
    private static org.eclipse.paho.client.mqttv3.MqttClient asyncClient;
    private static int qos = 0;
    private static PIRespository pIRespository;
    private static DeviceController deviceController;
    private static DeviceRepository deviceRepository;

    /**
     * Gets async client.
     *
     * @return the async client
     */
    public static MqttClient getAsyncClient() {
        return asyncClient;
    }


    /**
     * A
     * Init mqtt.
     *
     * @param applicationContext the application context
     */
    public static void initMQTT(ApplicationContext applicationContext) {

        deviceController = applicationContext.getBean(DeviceController.class);
        pIRespository = applicationContext.getBean(PIRespository.class);
        deviceRepository = applicationContext.getBean(DeviceRepository.class);

        String broker = MQTTConfig.getBroker();
        String clientId = MQTTConfig.getClientId();
        String userName = MQTTConfig.getUserName();
        String password = MQTTConfig.getPassword();
        String serverTopic = MQTTConfig.getTopic();
        String deviceWillTopic = MQTTConfig.getDeviceWillTopic();

        qos = MQTTConfig.getQos();
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            asyncClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            DisconnectedBufferOptions op = new DisconnectedBufferOptions();
            op.setBufferEnabled(true);
            op.setBufferSize(100);
            connOpts.setCleanSession(true);
            connOpts.setUserName(userName);
            connOpts.setPassword(password.toCharArray());
            connOpts.setCleanSession(true);
            log.info("Connecting to broker: " + broker);
            asyncClient.connect(connOpts);
            subscripe(deviceWillTopic, serverTopic);
            asyncClient.setCallback(new DeviceMQTTCallBack(connOpts, serverTopic, deviceWillTopic));
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    /**
     * Subscripe.
     *
     * @param deviceWillTopic the device will topic
     * @param serverTopic     the server topic
     * @throws MqttException the mqtt exception
     */
    public static void subscripe(String deviceWillTopic, String serverTopic) throws MqttException {
        subscribeWillTopic(deviceWillTopic);
        subscribeServerTopic(serverTopic);
    }

    /**
     * Subscribe will topic.
     *
     * @param deviceWillTopic the device will topic
     * @throws MqttException the mqtt exception
     */
    private static void subscribeWillTopic(String deviceWillTopic) throws MqttException {
        log.info("订阅频道: {}", deviceWillTopic);
        asyncClient.subscribe(deviceWillTopic, qos, (s, mqttMessage) -> {
            // 更新状态
            String macAddress = new String(mqttMessage.getPayload(), StandardCharsets.UTF_8);
            log.info("设备离线: {}", macAddress);
            PI pi = pIRespository.findByMacAddress(macAddress);
            if (pi != null) {
                pi.setOffLine(true);
                pi.setUpdateTime(new Date());
                pIRespository.save(pi);
            } else {
                Device device = deviceRepository.findBySymbol(macAddress + "-1");
                if (device != null) {
                    device.setDeviceState(DeviceStateEnum.OFFLINE);
                    deviceRepository.save(device);
                }
            }
        });
    }

    /**
     * Subscripe topic.
     *
     * @param serverTopic the topic
     * @throws MqttException the mqtt exception
     */
    private static void subscribeServerTopic(String serverTopic) throws MqttException {
        log.info("订阅频道: {}", serverTopic);
        asyncClient.subscribe(serverTopic, qos, (s, mqttMessage) -> {
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
                case "commonReg": {
                    CommonDeviceRequest request = JsonUtil.getEntity(content, CommonDeviceRequest.class);
                    deviceController.registerCommonDevice(request);
                    break;
                }
                case "register": {
                    DeviceRegisterRequest request = JsonUtil.getEntity(content, DeviceRegisterRequest.class);
                    deviceController.registerDevice(request);
                    break;
                }
                case "status": {
                    DeviceSituationRequest request = JsonUtil.getEntity(content, DeviceSituationRequest.class);
                    deviceController.status(request);
                    break;
                }
                case "event": {
                    DeviceEventRequest request = JsonUtil.getEntity(content, DeviceEventRequest.class);
                    deviceController.event(request);
                    break;
                }
                case "ping": {
                    DevicePingRequest request = JsonUtil.getEntity(content, DevicePingRequest.class);
                    deviceController.ping(request);
                    break;
                }
                case "rest": {
                    deviceController.reset(content);
                    break;
                }
                case "value": {
                    SensorValueRequest request = JsonUtil.getEntity(content, SensorValueRequest.class);
                    deviceController.getSensorValue(request);
                    break;
                }
                case "/pm25/register": {
                    DeviceRegisterRequest request = JsonUtil.getEntity(content, DeviceRegisterRequest.class);
                    deviceController.registerPM25(request);
                    break;
                }
                case "/door/register": {
                    CommonDeviceRequest request = JsonUtil.getEntity(content, CommonDeviceRequest.class);
                    deviceController.registerDoor(request);
                    break;
                }
            }
        });
    }

    /**
     * The type Device mqtt call back.
     */
    static class DeviceMQTTCallBack implements MqttCallback {

        /**
         * The Conn opts.
         */
        MqttConnectOptions connOpts;
        /**
         * The Device will topic.
         */
        String deviceWillTopic;
        /**
         * The Server topic.
         */
        String serverTopic;

        /**
         * Instantiates a new Device mqtt call back.
         *
         * @param connOpts        the conn opts
         * @param serverTopic     the server topic
         * @param deviceWillTopic the device will topic
         */
        public DeviceMQTTCallBack(MqttConnectOptions connOpts, String serverTopic, String deviceWillTopic) {
            this.connOpts = connOpts;
            this.serverTopic = serverTopic;
            this.deviceWillTopic = deviceWillTopic;
        }

        @Override
        public void connectionLost(Throwable cause) {
            try {
                log.info("服务器掉线,1秒以后重新连接!");
                // 等待5秒后重新连接
                Thread.sleep(1000 * 1);
                asyncClient.connect(connOpts);
                subscripe(deviceWillTopic, serverTopic);
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
        }
    }


}
