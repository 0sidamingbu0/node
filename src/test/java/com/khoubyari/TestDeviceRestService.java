package com.khoubyari;

import com.mydreamplus.smartdevice.Application;
import com.mydreamplus.smartdevice.service.DeviceRestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projectodd.stilts.stomp.StompException;
import org.projectodd.stilts.stomp.client.ClientSubscription;
import org.projectodd.stilts.stomp.client.StompClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/19
 * Time: 下午8:00
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class TestDeviceRestService {

/*    @Autowired
    private DeviceRestService deviceRestService;

    @Test
    public void removeDevice() {
        Assert.isTrue(deviceRestService != null);
//        deviceRestService.get
    }*/

    @Test
    public void testConnection() throws URISyntaxException, StompException, InterruptedException {
        StompClient client = new StompClient("http://dev.websocket.aws.mxj.mx/socket");
//        client.
/*
        ClientSubscription subscription1 =
                client.subscribe( "/queues/foo" )
                        .withMessageHandler( new Acumulator( "one" ) )
                        .withAckMode( AckMode.CLIENT_INDIVIDUAL )
                        .start();*/
    }
}
