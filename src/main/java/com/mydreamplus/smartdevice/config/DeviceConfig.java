package com.mydreamplus.smartdevice.config;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午2:15
 * To change this template use File | Settings | File Templates.
 * 系统的一些配置,暂时写在这里,后面都要写在property中
 */
public class DeviceConfig {


    /**
     * The constant AUTO_JOIN_IN.
     */
    private static boolean AUTO_JOIN_IN = false;

    public static boolean isAutoJoinIn() {
        return AUTO_JOIN_IN;
    }

    public static void setAutoJoinIn(boolean autoJoinIn) {
        AUTO_JOIN_IN = autoJoinIn;
    }
}
