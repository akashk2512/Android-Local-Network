package com.sample.multicastdns.listener;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

/**
 * Created by AKASH on 26/10/19.
 */
public class MyDisCoveryListener implements NsdManager.DiscoveryListener {
    private NetworkDiscovery discovery=null;

    public MyDisCoveryListener(NetworkDiscovery discovery) {
        this.discovery = discovery;
    }

    public interface NetworkDiscovery{
        void onSeriveFound(NsdServiceInfo serviceInfo);
        void discoveryStatus(String message);
    }

    @Override
    public void onStartDiscoveryFailed(String serviceType, int errorCode) {
        discovery.discoveryStatus("Start discovery failed");

    }

    @Override
    public void onStopDiscoveryFailed(String serviceType, int errorCode) {
        discovery.discoveryStatus("Stop discovery failed");
    }

    @Override
    public void onDiscoveryStarted(String serviceType) {
        discovery.discoveryStatus("Discovery started");


    }

    @Override
    public void onDiscoveryStopped(String serviceType) {
        discovery.discoveryStatus("Discovery stop");

    }

    @Override
    public void onServiceFound(NsdServiceInfo serviceInfo) {
        discovery.onSeriveFound(serviceInfo);
    }

    @Override
    public void onServiceLost(NsdServiceInfo serviceInfo) {
        discovery.discoveryStatus("Service lost");

    }
}
