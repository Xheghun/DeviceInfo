package com.xheghun.deviceinfo.application

import android.app.Application
import android.hardware.camera2.CameraManager
import com.xheghun.deviceinfo.model.repo.DeviceInfoRepo
import com.xheghun.deviceinfo.model.service.DeviceInfoService

class DeviceInfoApplication: Application() {

    private val deviceService by lazy { DeviceInfoService(this) }

    val repository by lazy { DeviceInfoRepo(deviceService) }
}