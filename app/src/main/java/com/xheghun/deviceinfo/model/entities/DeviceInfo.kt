package com.xheghun.deviceinfo.model.entities

data class DeviceInfo(
    val name: String,
    val model: String,
    val carrier: String,
    val locale: String,
    val manufactureDate: String,
    val buildNumber: String,
    val storageCapacity: String,
    val macAddress: String,
    val processorName: String,
    val ipAddress: String,
    val IMEI: String,
    val connectedHardware: List<Hardware>
    )