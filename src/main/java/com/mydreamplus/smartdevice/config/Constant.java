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
     * PING周期, 1分钟
     */
    public static final long PING_INTERVAL = 1000 * 1 * 60;

    public static final String DEFAULT_CREATE_USER = "system";

    /**
     * n路灯开关面板
     */
    public static final String PARENT_DEVICE_TYPE_N_SWITCHLIGHTPANEL = "N_SwitchLightPanel";

    public static final String PARENT_DEVICE_TYPE_N_SWITCHPANEL = "PowerPanel";

    public static final boolean AUTO_JOIN_IN = true;


    /**
     * 事件触发超时时间
     */
    public static final long EVENT_TIME_OUT = 1000 * 3;

}
