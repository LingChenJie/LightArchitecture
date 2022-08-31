package com.android.architecture.data.remote.ssl;

import android.annotation.SuppressLint;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 信任所有Hostname
 */
public class TrustAllHostname implements HostnameVerifier {

    @SuppressLint("BadHostnameVerifier")
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }

}
