package com.khoubyari;

import com.mydreamplus.smartdevice.Application;
import junit.framework.TestCase;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/19
 * Time: 下午8:00
 * To change this template use File | Settings | File Templates.
 */
public class TestDeviceRestService extends TestCase implements MqttCallback {
    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

/*    @Autowired
    private DeviceRestService deviceRestService;

    @Test
    public void removeDevice() {
        Assert.isTrue(deviceRestService != null);
//        deviceRestService.get
    }*/
}
