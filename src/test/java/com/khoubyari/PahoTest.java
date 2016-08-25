package com.khoubyari;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.InetAddress;
import java.net.NetworkInterface;

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
        MqttClient client = new MqttClient("tcp://10.28.1.30:1883", macAddr, persistence);

        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        connectOptions.setUserName("devices");
        connectOptions.setPassword("123456".toCharArray());

        client.connect(connectOptions);

//        client.subscribe(macAddr, 1, (topic, message) -> System.out.println(new String(message.getPayload(), StandardCharsets.UTF_8)));

        for (int i = 0; i < 100; i++) {
            client.publish(macAddr, "Hello".getBytes(), 1, true);
        }


        MemoryPersistence persistence2 = new MemoryPersistence();
        MqttClient client2 = new MqttClient("tcp://localhost:1883", macAddr, persistence2);
        MqttConnectOptions connectOptions2 = new MqttConnectOptions();
        connectOptions2.setCleanSession(true);
        connectOptions2.setUserName("devices");
        connectOptions2.setPassword("123456".toCharArray());
        client2.connect(connectOptions2);

        System.out.println(client.isConnected());
        System.out.println(client2.isConnected());
    }
}