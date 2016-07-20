package com.mydreamplus.smartdevice.domain;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/19
 * Time: 上午10:37
 * To change this template use File | Settings | File Templates.
 */
public class PolicyConfigDto {

    public PolicyConfigDto(String masterDevice, String masterEvent, String slaveDevice, String slaveFunction) {
        this.master = new Master(masterDevice, masterEvent);
        this.slave = new Slave(slaveDevice, slaveFunction);
    }

    private Master master;
    private Slave slave;

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public Slave getSlave() {
        return slave;
    }

    public void setSlave(Slave slave) {
        this.slave = slave;
    }

    class Master{
        public Master(String deviceSymbol, String eventName) {
            this.deviceSymbol = deviceSymbol;
            this.eventName = eventName;
        }

        String deviceSymbol;
        String eventName;
    }

    class Slave{
        public Slave(String deviceSymbol, String functionName) {
            this.deviceSymbol = deviceSymbol;
            this.functionName = functionName;
        }

        String deviceSymbol;
        String functionName;
    }
}
