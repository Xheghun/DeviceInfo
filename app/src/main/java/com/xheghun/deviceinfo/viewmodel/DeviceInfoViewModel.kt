package com.xheghun.deviceinfo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xheghun.deviceinfo.model.repo.DeviceInfoRepo
import java.lang.IllegalArgumentException

class DeviceInfoViewModel(private val repository: DeviceInfoRepo): ViewModel() {
    private val deviceInfo = repository.getDeviceInfo()


    fun deviceDetails(): String =
         deviceInfo.fInfo()


    fun cameraDetails(): String{
        var x = ""
        deviceInfo.camera.forEach {
            x += "$it\n"
        }
        return x
    }

    fun bluetoothDetails(): String{
        return deviceInfo.bluetooth
    }

    fun wifiDetails(): String{
        return deviceInfo.wifi
    }
}

class DeviceInfoViewModelFactory(private val repository: DeviceInfoRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DeviceInfoViewModel::class.java)) {
            return DeviceInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}