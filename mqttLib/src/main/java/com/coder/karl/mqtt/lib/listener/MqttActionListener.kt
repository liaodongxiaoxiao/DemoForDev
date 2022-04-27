package com.coder.karl.mqtt.lib.listener

interface MqttActionListener {
    fun onSuccess(topic: String)
    fun onFailure(exception: Throwable?)
}