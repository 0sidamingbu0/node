package com.mydreamplus.smartdevice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 * 传感器上报数据存储
 */
@Entity
@Table
public class SensorData extends BaseEntity {

    /**
     * 设备标识
     */
    @Column
    private String symbol;


    @Column
    private float data;

    /**
     * 传感器类型
     */
    @Column
    private String sensorType;

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
