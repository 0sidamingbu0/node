package com.mydreamplus.smartdevice;

import com.mydreamplus.smartdevice.config.MQTTConfig;
import com.mydreamplus.smartdevice.dao.jpa.DeviceRepository;
import com.mydreamplus.smartdevice.domain.DeviceStateEnum;
import com.mydreamplus.smartdevice.service.MQTTService;
import mousio.etcd4j.responses.EtcdAuthenticationException;
import mousio.etcd4j.responses.EtcdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/*
 * This is the main Spring Boot application class. It configures Spring Boot, JPA, Swagger
 */

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.mydreamplus.smartdevice")
@EnableJpaRepositories("com.mydreamplus.smartdevice.dao.jpa")
@EnableSwagger2
public class Application extends SpringBootServletInitializer {

    private static final String ETCD_URL_KEY = "etcd.url";
    private static final String WEBSOCKET_SERVICE_URL_ETCD_KEY = "etcd.websocketService";


    private static final Class<Application> applicationClass = Application.class;
    private static final Logger log = LoggerFactory.getLogger(applicationClass);


    public static void main(String[] args) throws IOException, EtcdAuthenticationException, TimeoutException, EtcdException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Environment env = context.getEnvironment();
        DeviceRepository deviceRepository = context.getBean(DeviceRepository.class);

        //================== WebSocket ==================
        /*String etcdUrl = env.getProperty(ETCD_URL_KEY);
        String webSocketUrl = env.getProperty(WEBSOCKET_SERVICE_URL_ETCD_KEY);
        log.info("-----------------------------" + etcdUrl);
        log.info("-----------------------------" + webSocketUrl);
        // 获取服务的URL
        EtcdClient etcdClient = new EtcdClient(URI.create(etcdUrl));
        EtcdKeyGetRequest keyGetRequest = etcdClient.get(webSocketUrl);
        EtcdKeysResponse resp = keyGetRequest.send().get();
        String webSocketServiceUrl = resp.node.getValue();
        if (StringUtils.isEmpty(webSocketServiceUrl)) {
            log.error("cant find server ip info from etcd, please check you config file");
            System.exit(1);
        }
        Constant.WEBSOCKET_SERVICE_URI = webSocketServiceUrl;
        log.info("WebSocket service: {}", webSocketServiceUrl);*/


        //================== MQTT ==================
        // Configuration mqtt
        MQTTConfig.setBroker(env.getProperty("mqtt.broker"));
        MQTTConfig.setClientId(env.getProperty("mqtt.clientId"));
        MQTTConfig.setPassword(env.getProperty("mqtt.password"));
        MQTTConfig.setQos(Integer.valueOf(env.getProperty("mqtt.qos")));
        MQTTConfig.setUserName(env.getProperty("mqtt.userName"));
        MQTTConfig.setTopic(env.getProperty("mqtt.topic"));
        MQTTConfig.setDeviceWillTopic(env.getProperty("mqtt.deviceWillTopic"));

        System.out.println("userName:" + MQTTConfig.getUserName());
        System.out.println("topic:" + MQTTConfig.getTopic());
        MQTTService.initMQTT(context);

        // 初始化设备离线
        log.info("初始化设备,设置为离线状态!");
        deviceRepository.updateOfflineState(DeviceStateEnum.OFFLINE, new Date());
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

}
