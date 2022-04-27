package com.coder.karl.mqtt.lib.listener

interface MqttConnectListener {
    fun onConnected()

    fun onDisConnected(errorCode: Int, errorMessage: String?)
}