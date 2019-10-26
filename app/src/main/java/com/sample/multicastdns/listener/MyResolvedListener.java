package com.sample.multicastdns.listener;

import android.annotation.SuppressLint;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import com.sample.multicastdns.model.ScannedData;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by AKASH on 26/10/19.
 */
public class MyResolvedListener implements NsdManager.ResolveListener {
    private MyResolveListener myResolveListener = null;
    private Observable<ScannedData> observable = null;

    public interface MyResolveListener {
        void onDeviceFound(ScannedData data);
    }

    public MyResolvedListener(MyResolveListener myResolveListener) {
        this.myResolveListener = myResolveListener;
    }


    @Override
    public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void onServiceResolved(NsdServiceInfo serviceInfo) {
        ScannedData data = new ScannedData();
        data.setHostAddress(serviceInfo.getHost());
        data.setPort(serviceInfo.getPort());
        data.setServiceName(serviceInfo.getServiceName());
        data.setServiceType(serviceInfo.getServiceType());

        observable = Observable.just(data);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> myResolveListener.onDeviceFound(result));

    }
}
