package com.mydreamplus.smartdevice.api.websocket;

import com.mydreamplus.smartdevice.domain.message.Greeting;
import com.mydreamplus.smartdevice.domain.message.HelloMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        logger.info("Hello, " + message.getName() + "!");
        return new Greeting("Hello, " + message.getName() + "!");
    }

}