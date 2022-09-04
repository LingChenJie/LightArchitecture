package com.architecture.light.helper

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.android.architecture.extension.getContext
import com.android.architecture.helper.Logger
import com.sunmi.idcardservice.IDCardServiceAidl

/**
 * Created by SuQi on 2022/9/4.
 * Describe:
 */
class AidlServiceFactory private constructor() {

    companion object {
        const val TAG = "AidlServiceFactory"
        val instance: AidlServiceFactory by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            AidlServiceFactory()
        }
    }

    private var idCardService: IDCardServiceAidl? = null

    fun init() {
        bindService()
    }

    fun release() {

    }

    fun getIDCardService(): IDCardServiceAidl? {
        if (idCardService == null) {
            bindIDCardService()
        }
        return idCardService
    }

    private fun bindService() {
        bindIDCardService()
    }

    private fun unBindService() {
        unBindIDCardService()
    }

    private fun bindIDCardService() {
        val intent = Intent()
        intent.setPackage("com.sunmi.idcardservice")
        intent.action = "com.sunmi.idcard"
        getContext().bindService(intent, idCardConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unBindIDCardService() {
        try {
            getContext().unbindService(idCardConnection)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val idCardConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Logger.e(TAG, "bindIDCardService onServiceConnected")
            idCardService = IDCardServiceAidl.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Logger.e(TAG, "bindIDCardService onServiceDisconnected")
            idCardService = null
        }
    }

}