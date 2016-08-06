package com.mydreamplus.smartdevice;

import com.mydreamplus.smartdevice.config.Constant;
import mousio.etcd4j.EtcdClient;
import mousio.etcd4j.promises.EtcdResponsePromise;
import mousio.etcd4j.requests.EtcdKeyGetRequest;
import mousio.etcd4j.responses.EtcdAuthenticationException;
import mousio.etcd4j.responses.EtcdException;
import mousio.etcd4j.responses.EtcdKeysResponse;
import mousio.etcd4j.responses.EtcdResponse;
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
import org.springframework.util.StringUtils;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeoutException;

/*
 * This is the main Spring Boot application class. It configures Spring Boot, JPA, Swagger
 */

@EnableAutoConfiguration  // Sprint Boot Auto Configuration
@ComponentScan(basePackages = "com.mydreamplus.smartdevice")
@EnableJpaRepositories("com.mydreamplus.smartdevice.dao.jpa")
// To segregate MongoDB and JPA repositories. Otherwise not needed.
@EnableSwagger2 // auto generation of API docs
public class Application extends SpringBootServletInitializer {

    private static final String ETCD_URL_KEY = "etcd.url";
    private static final String WEBSOCKET_SERVICE_URL_ETCD_KEY = "etcd.websocketService";


    private static final Class<Application> applicationClass = Application.class;
    private static final Logger log = LoggerFactory.getLogger(applicationClass);

    public static void main(String[] args) throws IOException, EtcdAuthenticationException, TimeoutException, EtcdException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Environment env = context.getEnvironment();
        String etcdUrl = env.getProperty(ETCD_URL_KEY);
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
        log.info("WebSocket service: {}", webSocketServiceUrl);

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

}
