package com.khoubyari;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.nio.charset.StandardCharsets;

public class MqttPublishSample {

    public static void main(String[] args) {
        String topic = "client2";
        String liliangTopic = "serverTopic";
        String content = "Message from liji's IntellJIDEA:";
        StringBuilder sb = new StringBuilder();
        int qos = 1;
        String broker = "tcp://localhost:1883";
        String clientId = "liji-mqtt-1";
//        MemoryPersistence persistence = new MemoryPersistence();
        MqttDefaultFilePersistence persistence = new MqttDefaultFilePersistence("/Users/liji/test");
        try {
//            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            MqttAsyncClient sampleClient = new MqttAsyncClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            DisconnectedBufferOptions op = new DisconnectedBufferOptions();
            op.setBufferEnabled(true);
            op.setBufferSize(100);
            sampleClient.setBufferOpts(op);
//            connOpts.setMaxInflight(100);
            connOpts.setCleanSession(true);
            connOpts.setUserName("deviceServer");
            connOpts.setPassword("deviceServer".toCharArray());
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts).waitForCompletion();
            System.out.println("Connected");
            System.out.println("Publishing message: " + content.toString());
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.subscribe(liliangTopic, qos, (s, mqttMessage) -> {
                System.out.println("Alex message : " + new String(mqttMessage.getPayload(), StandardCharsets.UTF_8));
            });
            for (int i = 0; i < 1000; i++) {
                Thread.sleep(1000);
                System.out.println("send..." + i);
                message.setPayload((content + String.valueOf(i)).getBytes());
                sampleClient.publish(liliangTopic, message);
            }
            System.out.println("Message published");
//            sampleClient.disconnect();
            System.out.println("Disconnected");
//            System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}