package com.sample.multicastdns.listener;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

/**
 * Created by AKASH on 26/10/19.
 */
public class MyRegistrationListener implements NsdManager.RegistrationListener {

    private MyNewtorkRegistration registration=null;
    public interface MyNewtorkRegistration{
        void onDeviceRegistration(String message);
    }

    public MyRegistrationListener(MyNewtorkRegistration registration) {
        this.registration = registration;
    }

    @Override
    public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
        registration.onDeviceRegistration("Registration failed");

    }

    @Override
    public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
        registration.onDeviceRegistration("Unregistration failed");

    }

    @Override
    public void onServiceRegistered(NsdServiceInfo serviceInfo) {
        registration.onDeviceRegistration("Service registered");
    }

    @Override
    public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
        registration.onDeviceRegistration("Service unregistered");

    }
}
