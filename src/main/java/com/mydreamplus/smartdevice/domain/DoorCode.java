package com.mydreamplus.smartdevice.domain;

/**
 * The type Door code.
 */
public class DoorCode {
    /**
     * The Door code.
     */
    String doorCode;
    /**
     * The Mac address.
     */
    String macAddress;

    /**
     * Gets door code.
     *
     * @return the door code
     */
    public String getDoorCode() {
        return doorCode;
    }

    /**
     * Sets door code.
     *
     * @param doorCode the door code
     */
    public void setDoorCode(String doorCode) {
        this.doorCode = doorCode;
    }

    /**
     * Gets mac address.
     *
     * @return the mac address
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Sets mac address.
     *
     * @param macAddress the mac address
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}