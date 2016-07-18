package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.domain.in.DeviceMessageRequest;
import com.mydreamplus.smartdevice.domain.out.WebSocketMessageResponse;
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
    public void registerFeedback(DeviceMessageRequest message) {
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
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        /*HttpComponentsClientHttpRequestFactory rf =
                (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
        rf.setReadTimeout(1 * 1000);
        rf.setConnectTimeout(1 * 1000);*/

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("deviceId", "123");
            jsonObj.put("message", "message");
            System.out.println(">>>>>>>>>>>>>>>> JSON credentials " + jsonObj.toString());
            HttpEntity<String> entityCredentials = new HttpEntity<>(jsonObj.toString(), httpHeaders);
            /*ResponseEntity<WebSocketMessageResponse> responseEntity = restTemplate.exchange(url,
                    HttpMethod.POST, entityCredentials, WebSocketMessageResponse.class);
            if (responseEntity != null) {
                WebSocketMessageResponse response = responseEntity.getBody();
                System.out.println(response.getSuccess() + response.getErrorMessage());
            }*/
            /*String result = restTemplate.getForObject("http://www.baidu.com",String.class);
            System.out.println(result);*/



            ResponseEntity<WebSocketMessageResponse> responseEntity = restTemplate.exchange(url,
                    HttpMethod.POST, entityCredentials, WebSocketMessageResponse.class);
            if (responseEntity != null) {
                WebSocketMessageResponse response = responseEntity.getBody();
                System.out.println(response.getSuccess() + response.getErrorMessage());
            }

//            return user;
        } catch (Exception e) {
//            Log.e(Constants.APP_NAME, ">>>>>>>>>>>>>>>> " + e.getLocalizedMessage());
        }
//        return null;
    }
}
