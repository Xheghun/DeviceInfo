package com.xheghun.deviceinfo.model.service

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import com.xheghun.deviceinfo.model.entities.DeviceInfo
import com.xheghun.deviceinfo.model.entities.Hardware
import java.util.*

class DeviceInfoService(val context: Context) {
    val telephonyManager: TelephonyManager = (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager)
    val wifiManager: WifiManager = (context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
    val devLocale =  Locale.getDefault()
    fun getDeviceInfo(): DeviceInfo {

        val carrier = telephonyManager.networkOperatorName
        val name = "${Build.MANUFACTURER}-${Build.BRAND}"
        val model = Build.MODEL
        val osVersion = Build.VERSION.RELEASE
        val locale = "${devLocale.language}-${devLocale.country}"
        val imei = telephonyManager.deviceId
        //val softwareVersion = telephonyManager.deviceSoftwareVersion
        val x = Date(Build.TIME)
        val n = Build.FINGERPRINT
        val board = Build.BOARD
        //val mac = wifiManager.connectionInfo.macAddress

        return DeviceInfo()
    }

    fun getConnectedHardware(): List<Hardware> {

    }
}