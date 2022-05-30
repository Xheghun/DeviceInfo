package com.xheghun.deviceinfo.model.entities

data class DeviceInfo(
    val name: String,
    val model: String,
    val carrier: String,
    val locale: String,
    val processorName: String,
    val osVersion: String,
    val date: String,
    val camera: List<String>,
    val bluetooth: String,
    val wifi: String
    ) {


     fun fInfo(): String {
        return "Name: ${name}\n" +
                "Model: ${model}\n" +
                "Board: ${processorName}\n" +
                "OS: ${osVersion}\n" +
                "Carrier: ${carrier}\n" +
                "Locale: ${locale}\n" +
                "Build Date: ${date}"
    }
}