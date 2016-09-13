package com.mydreamplus.smartdevice.aop;

import com.mydreamplus.smartdevice.dao.jpa.DeviceRepository;
import com.mydreamplus.smartdevice.dao.jpa.LinkQualityRepositoryImpl;
import com.mydreamplus.smartdevice.dao.jpa.PIRespository;
import com.mydreamplus.smartdevice.dao.jpa.PolicyRepository;
import com.mydreamplus.smartdevice.domain.DeviceStateEnum;
import com.mydreamplus.smartdevice.domain.EventLog;
import com.mydreamplus.smartdevice.domain.in.*;
import com.mydreamplus.smartdevice.domain.message.PolicyMessage;
import com.mydreamplus.smartdevice.entity.PI;
import com.mydreamplus.smartdevice.service.DeviceRestService;
import com.mydreamplus.smartdevice.service.WebSocketService;
import com.mydreamplus.smartdevice.util.PolicyParseUtil;
import com.mydreamplus.smartdevice.util.SymbolUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Rest controller aspect.
 */
@Aspect
@Component(value = "restControllerAspect")
public class RestControllerAspect {


    private final Logger log = LoggerFactory.getLogger(RestControllerAspect.class);

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceRestService deviceRestService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private PIRespository piRespository;

    /**
     * node每次请求云端,都会携带policyUpdateTime 场景的最后更新时间,云端根据这个时间来更新策略
     *
     * @param deviceRequest the device request
     * @throws Throwable the throwable
     */
    @Before("execution(public * com.mydreamplus.smartdevice.api.rest.*Controller.*(..)) &&" + "args(deviceRequest,..)")
    public void logBeforeRestCall(BaseDeviceRequest deviceRequest) throws Throwable {
        /*if (deviceRequest != null && deviceRequest.getPolicyUpdateTime() != null) {
            // 根据时间戳查询变化的策略集合
            List<PolicyMessage> list = new ArrayList<>();
            PI pi = piRespository.findByMacAddress(deviceRequest.getPiMacAddress());
            if (pi != null) {
                log.info("=========================== 查询更新场景 ==========================");
                this.policyRepository.findAllByPiAndUpdateTimeGreaterThan(pi, new Date(deviceRequest.getPolicyUpdateTime())).forEach(policy -> {
                    PolicyMessage policyMessage = new PolicyMessage();
                    policyMessage.setUpdateTime(policy.getUpdateTime().getTime());
                    policyMessage.setPolicyId(policy.getID());
                    policyMessage.setPolicyConfigDto(PolicyParseUtil.josnToPolicyConfigDto(policy.getPolicyConfig()));
                    policyMessage.setDeleted(policy.getDeleted());
                    policyMessage.setDisabled(policy.isDisabled());
                    list.add(policyMessage);
                    log.info("更新策略:{}", policy.getName());
                });
                // 有策略更新
                if (list.size() > 0) {
                    // 下发到node, node会更新场景, 如果下发失败, node上场景的update time不变,下次继续下发
                    log.info("更新策略数量:{}, {}", list.size(), new Date(deviceRequest.getPolicyUpdateTime()));
                    deviceRestService.sendPolicy(deviceRequest.getPiMacAddress(), list);
                }
            }
        }*/
        // ======================== 更新 Link Quality、UpdateTime、Status ========================
        int linkQuality = deviceRequest.getLinkQuality();
        log.info("设备Link Quality: " + linkQuality);
        Date date = new Date();
        // 1、注册
        if (deviceRequest instanceof DeviceRegisterRequest) {
            DeviceRegisterRequest deviceRegisterRequest = (DeviceRegisterRequest) deviceRequest;
            EventLog eventLog = new EventLog();
            eventLog.setDate(date);
            eventLog.setEventName("设备注册");
            eventLog.setLinkQuality(deviceRegisterRequest.getLinkQuality());
            eventLog.setPiMacAddress(deviceRegisterRequest.getPiMacAddress());
            eventLog.setMacAddress(deviceRegisterRequest.getMacAddress());
            webSocketService.sendMessage(eventLog);
//            webSocketService.sendMessage("时间:" + date + ",设备注册:" + deviceRegisterRequest.getMacAddress() + ",网关地址:" + deviceRegisterRequest.getPiMacAddress() + ",设备类型:" + deviceRegisterRequest.getParentDeviceType() + ", 信号强度:" + linkQuality);
            LinkQualityRepositoryImpl.setLinkQuality(((DeviceRegisterRequest) deviceRequest).getMacAddress(), linkQuality);
            this.updateDeviceStateByMacAddress(((DeviceRegisterRequest) deviceRequest).getMacAddress());
        }
        // 2、上报设备状态
        if (deviceRequest instanceof DeviceSituationRequest) {
            if (((DeviceSituationRequest) deviceRequest).getDeviceSituationDtos() != null && ((DeviceSituationRequest) deviceRequest).getDeviceSituationDtos().size() > 0) {
                ((DeviceSituationRequest) deviceRequest).getDeviceSituationDtos().forEach(deviceSituationDto -> {
                    EventLog eventLog = new EventLog();
                    eventLog.setSymbol(deviceSituationDto.getSymbol());
                    eventLog.setPiMacAddress(deviceRequest.getPiMacAddress());
                    eventLog.setEventName("设备上报状态");
                    eventLog.setDate(new Date());
                    eventLog.setLinkQuality(linkQuality);
                    eventLog.setState(deviceSituationDto.getValue());
                    webSocketService.sendMessage(eventLog);
//                    webSocketService.sendMessage("时间:" + date + ",设备上报状态:" + deviceSituationDto.getSymbol() + ",状态:" + deviceSituationDto.getValue() + ",信号强度:" + linkQuality);
                    LinkQualityRepositoryImpl.setLinkQuality(SymbolUtil.parseMacAddress(deviceSituationDto.getSymbol()), linkQuality);
                    this.updateDeviceStateBySymbol(deviceSituationDto.getSymbol());
                });
            }
        }
        // 3、上报事件
        if (deviceRequest instanceof DeviceEventRequest) {
            DeviceEventRequest deviceEventRequest = (DeviceEventRequest) deviceRequest;
            EventLog eventLog = new EventLog();
            eventLog.setLinkQuality(linkQuality);
            eventLog.setDate(new Date());
            eventLog.setEventName("事件上报:" + deviceEventRequest.getEventName());
            eventLog.setPiMacAddress(deviceEventRequest.getPiMacAddress());
            eventLog.setCostTime(System.currentTimeMillis() - deviceEventRequest.getEventTime());
            eventLog.setSymbol(deviceEventRequest.getSymbol());
            webSocketService.sendMessage(eventLog);
//            webSocketService.sendMessage("时间:" + date + ",设备事件:" + deviceEventRequest.getSymbol() + ",事件名:" + deviceEventRequest.getEventName() + ",耗时:" + String.valueOf(System.currentTimeMillis() - deviceEventRequest.getEventTime()) + ",信号强度:" + linkQuality);
            LinkQualityRepositoryImpl.setLinkQuality(SymbolUtil.parseMacAddress(((DeviceEventRequest) deviceRequest).getSymbol()), linkQuality);
            this.updateDeviceStateBySymbol(deviceEventRequest.getSymbol());
        }
        // 4、Ping
        if (deviceRequest instanceof DevicePingRequest) {
            DevicePingRequest devicePingRequest = (DevicePingRequest) deviceRequest;

            EventLog eventLog = new EventLog();
            eventLog.setPiMacAddress(devicePingRequest.getPiMacAddress());
            eventLog.setEventName("PING");
            eventLog.setDate(new Date());
            eventLog.setLinkQuality(linkQuality);
            eventLog.setPing(devicePingRequest.getPing());
            webSocketService.sendMessage(eventLog);

//            webSocketService.sendMessage("时间:" + date + ",PING MAC地址:" + devicePingRequest.getMacAddress() + ",PING值:" + devicePingRequest.getPing() + ",信号强度:" + linkQuality);
            LinkQualityRepositoryImpl.setLinkQuality(((DevicePingRequest) deviceRequest).getMacAddress(), linkQuality);
            this.updateDeviceStateByMacAddress(((DevicePingRequest) deviceRequest).getMacAddress());
        }
        // 5、上报数据
        if (deviceRequest instanceof SensorValueRequest) {
            SensorValueRequest sensorValueRequest = (SensorValueRequest) deviceRequest;

            EventLog eventLog = new EventLog();
            eventLog.setDate(new Date());
            eventLog.setSymbol(sensorValueRequest.getSymbol());
            eventLog.setSensorValue(sensorValueRequest.getValue().toString());
            eventLog.setEventName("上报数据");
            eventLog.setLinkQuality(linkQuality);
            webSocketService.sendMessage(eventLog);
//            webSocketService.sendMessage("时间:" + date + ",设备上报数据:" + sensorValueRequest.getSymbol() + ",数据:" + sensorValueRequest.getValue() + ",信号强度:" + linkQuality);
            LinkQualityRepositoryImpl.setLinkQuality(SymbolUtil.parseMacAddress(((SensorValueRequest) deviceRequest).getSymbol()), linkQuality);
            this.updateDeviceStateBySymbol(((SensorValueRequest) deviceRequest).getSymbol());
        }
    }

    /**
     * 更新设备的更新时间和设备在线状态
     *
     * @param symbol
     */
    private void updateDeviceStateBySymbol(String symbol) {
        this.updateDeviceStateByMacAddress(SymbolUtil.parseMacAddress(symbol));
    }

    /**
     * 更新设备的更新时间和设备在线状态
     *
     * @param macAddress
     */
    private void updateDeviceStateByMacAddress(String macAddress) {
        log.info("更新设备的在线状态{}", macAddress);
        this.deviceRepository.updateUpdateTimeByMacAddress(new Date(), DeviceStateEnum.ONLINE, macAddress);
    }
}
