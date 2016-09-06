package com.khoubyari;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;

/**
 * Created by Alex on 16/8/18.
 */
public class PahoTest {
    public static void main(String[] args) throws Exception {
        byte[] macAddrBytes = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
        StringBuilder macSb = new StringBuilder();
        for (int i = 0; i < macAddrBytes.length; i++) {
            macSb.append(String.format("%02X%s", macAddrBytes[i], (i < macAddrBytes.length - 1) ? ":" : ""));
        }
        String macAddr = macSb.toString();

        MemoryPersistence persistence = new MemoryPersistence();
        MqttClient client = new MqttClient("tcp://localhost:1883", macAddr, persistence);

        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        connectOptions.setUserName("alex");
        connectOptions.setPassword("alex".toCharArray());

        client.connect(connectOptions);

        client.subscribe("outTopic");
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println(topic);
                System.out.println(message.getPayload());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        client.publish("inTopic", "Hello from Idea".getBytes(), 1, true);


        System.out.println(client.isConnected());
    }
}