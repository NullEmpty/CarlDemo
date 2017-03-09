package com.example.carldemo.mdns;

import android.app.Activity;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.carldemo.R;

import java.net.InetAddress;
import java.net.ServerSocket;

public class MDNSActivity extends Activity {

    private static final String TAG = MDNSActivity.class.getSimpleName();
    private NsdManager mNsdManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdns);

        int port = 0;
        try {
            ServerSocket sock = new ServerSocket(0);
            port = sock.getLocalPort();
            sock.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "can not set port", Toast.LENGTH_SHORT);
        }
        mNsdManager = (NsdManager)getSystemService(Context.NSD_SERVICE);
        NsdServiceInfo info = new NsdServiceInfo();
        info.setPort(port);
        info.setServiceName("NSD_carl");
        info.setServiceType("_http._tcp.");
        mNsdManager.registerService(info, NsdManager.PROTOCOL_DNS_SD, mRegistrationListener);
    }

    private NsdManager.RegistrationListener mRegistrationListener = new NsdManager.RegistrationListener() {
        @Override
        public void onRegistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
            Log.e(TAG, "onRegistrationFailed");
        }

        @Override
        public void onUnregistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
            Log.e(TAG, "onUnregistrationFailed");
        }

        @Override
        public void onServiceRegistered(NsdServiceInfo nsdServiceInfo) {
            Log.e(TAG, "onServiceRegistered");
        }

        @Override
        public void onServiceUnregistered(NsdServiceInfo nsdServiceInfo) {
            Log.e(TAG, "onServiceUnregistered");
        }
    };

    @Override
    protected void onDestroy() {
        if (mNsdManager != null) {
            mNsdManager.unregisterService(mRegistrationListener);
        }
        super.onDestroy();
    }
}
