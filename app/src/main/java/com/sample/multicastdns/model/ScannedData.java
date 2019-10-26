package com.sample.multicastdns.model;

import java.net.InetAddress;

/**
 * Created by AKASH on 26/10/19.
 */
public class ScannedData {
    private String serviceType;
    private String serviceName;
    private int port;
    private InetAddress hostAddress;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(InetAddress hostAddress) {
        this.hostAddress = hostAddress;
    }
}
