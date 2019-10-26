package com.sample.multicastdns.view;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sample.multicastdns.AppConstant;
import com.sample.multicastdns.AppUtils;
import com.sample.multicastdns.listener.MyDisCoveryListener;
import com.sample.multicastdns.listener.MyRegistrationListener;
import com.sample.multicastdns.listener.MyResolvedListener;
import com.sample.multicastdns.R;
import com.sample.multicastdns.adapters.ScanDataAdapter;
import com.sample.multicastdns.model.ScannedData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyRegistrationListener.MyNewtorkRegistration, MyDisCoveryListener.NetworkDiscovery, MyResolvedListener.MyResolveListener {

    Button btn_scan;
    Button btn_publish;
    RecyclerView recyclerView;

    private NsdManager mNsdManager;
    private boolean isServicePublished;
    private boolean isDisCoveryRunning;

    boolean isPublishedClicked;
    boolean isScanClicked;
    Context mContext;

    private ScanDataAdapter scanDataAdapter = null;

    MyDisCoveryListener disCoveryListener = new MyDisCoveryListener(this);
    MyRegistrationListener mRegistrationListener = new MyRegistrationListener(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;

        mNsdManager = AppUtils.initializeNSDManger(mContext);

        btn_scan = findViewById(R.id.btn_scan);
        btn_publish = findViewById(R.id.btn_publish);
        recyclerView = findViewById(R.id.recycler_view);

        btn_publish.setOnClickListener(this);
        btn_scan.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        scanDataAdapter = new ScanDataAdapter(mContext);
        recyclerView.setAdapter(scanDataAdapter);
    }

    @Override
    protected void onPause() {
        if (mNsdManager != null) {
            if (isPublishedClicked) {
                unRegisterService();
            }
            if (isScanClicked) {
                stopDisCoverService();
            }
        }
        if (countDownTimer != null) {
            stopTimer();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNsdManager != null) {
            if (isPublishedClicked) {
                registerService(AppConstant.PORT);
            }
            if (isScanClicked) {
                disCoverService();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Discover service
     */
    public void disCoverService() {
        if (!isDisCoveryRunning) {
            isDisCoveryRunning = true;
            startTime();
            scanDataAdapter.refreshAdapter();
            mNsdManager.discoverServices(AppConstant.SERVICE_TYPE,
                    NsdManager.PROTOCOL_DNS_SD, disCoveryListener);
        }
    }

    /**
     * Stop discoverService
     */
    public void stopDisCoverService() {
        if (isDisCoveryRunning) {
            isDisCoveryRunning = false;
            mNsdManager.stopServiceDiscovery(disCoveryListener);
        }
    }


    /**
     * Register service
     *
     * @param port
     */
    public void registerService(int port) {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName(AppConstant.SERVICE_NAME);
        serviceInfo.setServiceType(AppConstant.SERVICE_TYPE);
        serviceInfo.setPort(port);
        if (!isServicePublished) {
            isServicePublished = true;
            mNsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD,
                    mRegistrationListener);
        }
    }

    /**
     * Unregister service
     */
    public void unRegisterService() {
        if (isServicePublished) {
            isServicePublished = false;
            mNsdManager.unregisterService(mRegistrationListener);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_publish:
                isPublishedClicked = true;
                isScanClicked = false;
                registerService(AppConstant.PORT);
                break;
            case R.id.btn_scan:
                if (btn_scan.getText().toString().equalsIgnoreCase("SCAN")) {
                    isPublishedClicked = false;
                    isScanClicked = true;
                    disCoverService();
                }
                break;
        }
    }

    @Override
    public void onDeviceRegistration(String message) {
        AppUtils.showToast(mContext, message);
    }


    @Override
    public void onSeriveFound(NsdServiceInfo serviceInfo) {
        mNsdManager.resolveService(serviceInfo, new MyResolvedListener(this));
    }

    @Override
    public void discoveryStatus(String message) {
        AppUtils.showToast(mContext, message);

    }

    @Override
    public void onDeviceFound(final ScannedData data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scanDataAdapter.updateList(data);

            }
        });
    }

    CountDownTimer countDownTimer = null;

    void startTime() {
        countDownTimer = new CountDownTimer(10000, 1000) { // count down for 10seconds

            public void onTick(long millisUntilFinished) {
                btn_scan.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                stopDisCoverService();
                btn_scan.setText("SCAN");
            }
        }.start();

    }

    void stopTimer() {
        btn_scan.setText("SCAN");
        countDownTimer.cancel();
    }
}
