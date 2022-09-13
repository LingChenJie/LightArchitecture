package com.architecture.light.helper

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.android.architecture.extension.getContext
import com.android.architecture.helper.Logger
import com.sunmi.a4printerservice.IA4PrinterAidl
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
    private var a4PrinterService: IA4PrinterAidl? = null

    fun init() {
        bindService()
    }

    fun release() {
        unBindService()
    }

    fun getIDCardService(): IDCardServiceAidl? {
        if (idCardService == null) {
            bindIDCardService()
        }
        return idCardService
    }

    fun getA4PrinterService(): IA4PrinterAidl? {
        if (a4PrinterService == null) {
            bindA4PrinterService()
        }
        return a4PrinterService
    }

    private fun bindService() {
        bindIDCardService()
        bindA4PrinterService()
    }

    private fun unBindService() {
        unBindIDCardService()
        unBindA4PrinterService()
    }

    private fun bindIDCardService() {
        val intent = Intent()
        intent.setPackage("com.sunmi.idcardservice")
        intent.action = "com.sunmi.idcard"
        getContext().bindService(intent, idCardConnection, Context.BIND_AUTO_CREATE)
    }

    private fun bindA4PrinterService() {
        val intent = Intent()
        intent.setPackage("com.sunmi.a4printerservice")
        intent.action = "com.sunmi.a4printerservice.PrinterService"
        getContext().bindService(intent, a4PrinterConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unBindIDCardService() {
        try {
            getContext().unbindService(idCardConnection)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun unBindA4PrinterService() {
        try {
            getContext().unbindService(a4PrinterConnection)
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

    private val a4PrinterConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Logger.e(TAG, "bindA4PrinterService onServiceConnected")
            a4PrinterService = IA4PrinterAidl.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Logger.e(TAG, "bindA4PrinterService onServiceDisconnected")
            a4PrinterService = null
        }
    }

}