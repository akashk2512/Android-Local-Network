package com.sample.multicastdns.listener;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

import com.sample.multicastdns.model.ScannedData;


/**
 * Created by AKASH on 26/10/19.
 */
public class MyResolvedListener implements NsdManager.ResolveListener {
   private MyResolveListener myResolveListener=null;

    public interface MyResolveListener{
        void onDeviceFound(ScannedData data);
    }

    public MyResolvedListener(MyResolveListener myResolveListener) {
        this.myResolveListener = myResolveListener;
    }


    @Override
    public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {

    }

    @Override
    public void onServiceResolved(NsdServiceInfo serviceInfo) {
        ScannedData data = new ScannedData();
        data.setHostAddress(serviceInfo.getHost());
        data.setPort(serviceInfo.getPort());
        data.setServiceName(serviceInfo.getServiceName());
        data.setServiceType(serviceInfo.getServiceType());
        myResolveListener.onDeviceFound(data);
    }
}
