package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.domain.out.DeviceMessage;
import org.apache.commons.logging.Log;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

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

    @Async(value = "messageExecutor")
    public void registerFeedback(DeviceMessage message) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://www.baidu.com",String.class);
        System.out.println(result);
        log.info("register feedback!");
    }

    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();
////        String result = restTemplate.getForObject("http://www.baidu.com",String.class);
        String url = "http://qa.websocket.aws.mxj.mx/api/websocket/sendMessageToClient";
//
//        HttpEntity<Message> request = new HttpEntity<>(new Message("id","message"));
//        restTemplate.postForLocation(url, request, Message.class);
//        System.out.println(result);

//
//        ResponseEntity<Message> response = restTemplate.
//                exchange(url, HttpMethod.POST, request, Message.class);

        RestTemplate restTemplate = new RestTemplate();
        Message user = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        try {
            JSONObject jsonCredentials = new JSONObject();
            jsonCredentials.put("deviceId", "");
            jsonCredentials.put("message", "");
//            Log.e("tt", ">>>>>>>>>>>>>>>> JSON credentials " + jsonCredentials.toString());
            HttpEntity<String> entityCredentials = new HttpEntity<String>(jsonCredentials.toString(), httpHeaders);
            ResponseEntity<Message> responseEntity = restTemplate.exchange(url,
                    HttpMethod.POST, entityCredentials, Message.class);
            if (responseEntity != null) {
                user = responseEntity.getBody();
            }
//            return user;
        } catch (Exception e) {
//            Log.e(Constants.APP_NAME, ">>>>>>>>>>>>>>>> " + e.getLocalizedMessage());
        }
//        return null;
    }


   static class Message{
        private String deviceId;
        private String message;

        public Message(String deviceId, String message) {
            this.deviceId = deviceId;
            this.message = message;
        }
    }

}
