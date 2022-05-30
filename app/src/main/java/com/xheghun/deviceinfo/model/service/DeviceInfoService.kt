package com.xheghun.deviceinfo.model.service

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import com.xheghun.deviceinfo.model.entities.DeviceInfo
import java.util.*

/**
 * Class to help with accessing Device Information
 *
 * @param context app's context
 * */
class DeviceInfoService(private val context: Context) {


    private val telephonyManager: TelephonyManager =
        (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager)
    private val wifiManager: WifiManager =
        (context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
    private val cameraManager = (context.getSystemService(Context.CAMERA_SERVICE) as CameraManager)
    private val bluetoothManager =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager)
    private val conManager = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

    /**
     * gets the necessary information about the device in a structures object
     *
     * @return DeviceInfo object
     * */
    @SuppressLint("MissingPermission")
    fun getDeviceInfo(): DeviceInfo {
        val devLocale: Locale = Locale.getDefault()
        val carrier = telephonyManager.networkOperatorName
        val name = " ${Build.BRAND.replaceFirstChar { it.uppercase() }}  ${bluetoothManager.adapter?.name ?: ""}"
        val model = Build.MODEL
        val osVersion = Build.VERSION.RELEASE
        val locale = "${devLocale.language}-${devLocale.country}"
        val board = Build.BOARD
        val date = Date(Build.TIME).toString()

        return DeviceInfo(
            name,
            model,
            carrier,
            locale,
            board,
            osVersion,
            date,
            getCameraInfo(),
            bluetoothInfo(),
            wifiInfo()
        )
    }

    private fun getCameraInfo(): List<String> {
        val cameraIdList = cameraManager.cameraIdList

        val info = mutableListOf<String>()

        for (cameraId in cameraIdList) {
            val c = cameraManager.getCameraCharacteristics(cameraId)

            when(c.get(CameraCharacteristics.LENS_FACING)) {
                0 -> info.add("Front")
                1 -> info.add("Back")
                2 -> info.add("External")
                else -> info.add("Unidentified")
            }
        }
        return info
    }

    @SuppressLint("MissingPermission")
   private fun bluetoothInfo(): String {
        var message = "No Bluetooth device found"

        if (bluetoothManager.adapter != null) {
            val name = bluetoothManager.adapter.name
            val isEnabled = bluetoothManager.adapter.isEnabled
            val address = bluetoothManager.adapter.address
            message = "Name: $name\n" +
                    "Enabled: $isEnabled\n" +
                    "Address: $address"
        }
        return message

    }

    @SuppressLint("MissingPermission")
    private fun wifiInfo(): String {

        val wifi = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)

         val enabled = wifiManager.isWifiEnabled
        val isConnected = wifi?.isConnected
        val macAddress = wifiManager.connectionInfo.macAddress
        val id = wifiManager.connectionInfo.networkId
        val ssid = wifiManager.connectionInfo.ssid



        return "Enabled: $enabled\n" +
                "Connected: $isConnected\n" +
                "MAC Address: $macAddress\n" +
                "ID: $id\n" +
                "SSID: $ssid"
    }

    /**
     * formats and saves DeviceInformation to internal storage file, accessible by the app only.
     * */
     fun saveInfoToFile() {

        val info = "[DEVICE]\n" +
                "${getDeviceInfo().fInfo()}\n\n\n" +
                "[CAMERA]\n" +
                "${getDeviceInfo().camera.toString()}\n\n\n" +
                "[BLUETOOTH]\n" +
                "${getDeviceInfo().bluetooth}\n\n\n" +
                "[WIFI]\n" +
                getDeviceInfo().wifi


        context.openFileOutput("deviceInfo", Context.MODE_PRIVATE).use {
            output ->
            output.write(info.toByteArray())
            Log.i("DeviceInfoService", "Saved $info")
        }
    }
}