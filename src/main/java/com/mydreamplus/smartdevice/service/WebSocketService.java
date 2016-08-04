package com.mydreamplus.smartdevice.service;

import com.mydreamplus.smartdevice.domain.PingDto;
import com.mydreamplus.smartdevice.domain.message.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/18
 * Time: 下午7:44
 * To change this template use File | Settings | File Templates.
 */
@Service
public class WebSocketService {


    @Autowired
    private MessageSendingOperations<String> messageTemplate;

    /**
     * Send message.
     *
     * @param message the message
     */
    public void sendMessage(String message) {
        messageTemplate.convertAndSend("/topic/greetings", new Greeting(message));
    }

    /**
     * Send ping message.
     * 发送PING 结果到UI
     *
     * @param pingDto the ping dto
     */
    public void sendPingMessage(PingDto pingDto) {
        messageTemplate.convertAndSend("/topic/ping", pingDto);
    }
}
