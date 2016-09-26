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

    /**
     * 门禁设备
     */
    public static final String DEVICE_TYPE_DOOR = "Door";
    public static final String DEVICE_TYPE_PASSWORD_CONTROLLER = "DoorController";

    /**
     * 门设备的事件
     */
    public static final String DEVICE_EVENT_REPORT_DATA = "ReportData";

    public static final String DEVICE_EVENT_REPORT_PASSWORD = "ReportPassword";
    public static final String DEVICE_EVENT_REPORT_CARD = "ReportCardId";
    public static final String DEVICE_EVENT_REPORT_PASSWORD_OR_CARD = "ReportPassword|ReportCardId";
    public static final String DEVICE_FUNCTION_OPEN_DOOR = "On";

    public static final String API_CONDITION_URL = "ValidateUrl";
    public static final String DOOR_ATTRIBUUTE = "DoorCode";


    public static final String DEFAULT_GROUP_NAME = "DEFAULT_GROUP";
    public static final String CONDITION_TYPE_API = "CONTROLLED";
    public static final String OLD_DOOR_TYPE = "Door";
    /**
     * 会议室门禁设备
     */
    public static final String MEET_ROOM_DOOR_TYPE = "MeetingRoomDoor";

    public static final int LOGIC_TRUE = 1;

    /**
     * 事件超时 8秒 以上则不执行云端场景方法
     */
    public static final long EXECUTE_POLICY_INTERVAL = 1000 * 8;


    public static final String PM25_CONTROLLER = "PM2.5Controller";
    public static final String PM25_SENSOR = "PM2.5Sensor";
    public static final String PM10_SENSOR = "PM10Sensor";

    public static final String DEVICE_FUNCTION_REMOVE_CARD = "removeCard";


}
