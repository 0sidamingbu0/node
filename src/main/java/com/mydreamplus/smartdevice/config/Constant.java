package com.mydreamplus.smartdevice.config;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午2:15
 * To change this template use File | Settings | File Templates.
 * 系统的一些配置,暂时写在这里,后面都要写在property中
 */
public class Constant {


    /**
     * n路灯开关面板
     */
    public static final String PARENT_DEVICE_TYPE_N_SWITCHLIGHTPANEL = "N_SwitchLightPanel";

    /**
     * The constant PARENT_DEVICE_TYPE_N_SWITCHPANEL.
     */
    public static final String PARENT_DEVICE_TYPE_N_SWITCHPANEL = "PowerPanel";

    public static final String DEVICE_TYPE_DOOR = "Door";

    /**
     * 事件触发超时时间
     */
    public static final long EVENT_TIME_OUT = 1000 * 3;

    public static final String WEBSOCKET_SERVICE_API = "/api/websocket/sendMessageToClient";
    /**
     * 门设备的事件
     */
    public static final String DEVICE_EVENT_REPORT_PASSWORD = "ReportCardId";
    public static final String DEVICE_EVENT_REPORT_CARD = "ReportPassword";
    public static String WEBSOCKET_SERVICE_URI = "";

    public static final String DEFAULT_GROUP_NAME = "DEFAULT_GROUP";

}
