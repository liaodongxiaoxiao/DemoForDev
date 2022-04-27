package com.coder.karl.mqtt.lib.listener

interface OnArrivedMessageListener {
    fun messageArrived(message: String?)
}