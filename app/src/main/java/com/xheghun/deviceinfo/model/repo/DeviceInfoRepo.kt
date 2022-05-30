package com.xheghun.deviceinfo.model.repo

import com.xheghun.deviceinfo.model.entities.DeviceInfo
import com.xheghun.deviceinfo.model.service.DeviceInfoService

class DeviceInfoRepo(private val deviceService: DeviceInfoService) {
    fun getDeviceInfo(): DeviceInfo {
        val deviceInfo = deviceService.getDeviceInfo()
        deviceService.saveInfoToFile()
        return deviceInfo
    }
}