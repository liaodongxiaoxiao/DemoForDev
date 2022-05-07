package com.coder.karl.mqtt.lib.listener

interface MqttActionListener {
    fun onSuccess()
    fun onFailure(exception: Throwable?)
}