package com.mydreamplus.smartdevice.domain;

/**
 * The type Door info.
 */
public class DoorInfo {
    /**
     * The Door code.
     */
    String doorCode;
    /**
     * The Mac address.
     */
    String macAddress;
    /**
     * The Card no.
     */
    String cardNo;

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

    /**
     * Gets card no.
     *
     * @return the card no
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * Sets card no.
     *
     * @param cardNo the card no
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
