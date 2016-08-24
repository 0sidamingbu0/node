package com.mydreamplus.smartdevice.config;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午2:15
 * To change this template use File | Settings | File Templates.
 * MQTT 配置
 */
public class MQTTConfig {


    private static String topic;

    private static int qos;

    private static String broker;

    private static String clientId;

    private static String userName;

    private static String password;


    public static String getTopic() {
        return topic;
    }

    public static void setTopic(String topic) {
        MQTTConfig.topic = topic;
    }

    public static int getQos() {
        return qos;
    }

    public static void setQos(int qos) {
        MQTTConfig.qos = qos;
    }

    public static String getBroker() {
        return broker;
    }

    public static void setBroker(String broker) {
        MQTTConfig.broker = broker;
    }

    public static String getClientId() {
        return clientId;
    }

    public static void setClientId(String clientId) {
        MQTTConfig.clientId = clientId;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        MQTTConfig.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        MQTTConfig.password = password;
    }
}
